package org.juz.common.service.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.juz.common.api.Command;
import org.juz.common.api.ValidationError;
import org.juz.common.api.ValidationResult;
import org.juz.common.command.*;
import org.juz.common.persistence.model.audit.AuditRevisionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
class CommandServiceBean implements CommandService {

	private static final Logger log = LoggerFactory.getLogger(CommandServiceBean.class);

	private final static CommandToStringStyle COMMAND_TO_STRING_STYLE = new CommandToStringStyle();

	@Autowired
	private CommandHandlerFactory handlerFactory;

	@Autowired
	private CommandValidatorFactory validatorFactory;

	@Produce
	private ProducerTemplate producer;

	@Value("${command.queue:}")
	private String commandQueue;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private TransactionBinder transactionBinder;

	@Override
	@Transactional
	public <C extends Command> void enqueue(final C command) {
		if (StringUtils.isEmpty(commandQueue)) {
			log.warn("No command queue defined");
			return;
		}
		log.info("Enqueuing command {}", command.getClass().getSimpleName());
		transactionBinder.bind(() -> {
			producer.sendBody(commandQueue, command);
		});
	}

	@Transactional
	@Override
	public <C extends Command> void enqueueWithHighPriority(final C command) {
		log.info("Enqueuing command with high priority {}", command.getClass().getSimpleName());
		transactionBinder.bind(() -> {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("JMSPriority", JMSPriority.HIGH.getJmsPriority());
			producer.sendBodyAndHeaders(commandQueue, command, headers);
		});
	}

	@Override
	@Transactional
	public <C extends Command> void enqueueSingleOnTransaction(final C command, final String operationId) {
		log.info("Enqueuing single command command {} operationId: {}", command.getClass().getSimpleName(), operationId);
		transactionBinder.bind(() -> {
			Map<String, Object> headers = Maps.newHashMap();
			headers.put("correlationId", operationId);
			producer.sendBodyAndHeaders(commandQueue, command, headers);
		});
	}

	@Override
	@Transactional
	public <C extends Command, T> T execute(final C command) {
		T result = null;
		LocalDateTime start = LocalDateTime.now();
		addAuditDetail(command);
		boolean shouldLog = isCommandLoggable(command);
		if (shouldLog) {
			log.info("--> {}", toString(command));
		}
		result = doExecute(command);
		entityManager.flush();
		if (shouldLog) {
			logResult(result, start);
		}
		return result;
	}

	private <C extends Command, T> T doExecute(final C command) {
		Preconditions.checkNotNull(command);
		ValidationResult validationResult = validate(command);
		if (validationResult.hasErrors()) {
			throw new ValidationException(command, validationResult);
		}
		CommandHandler<C, T> handler = handlerFactory.getCommandHandler(command);
		T result = handler.execute(command);
		Preconditions.checkNotNull(result, "Command result is null, command: %s", command);
		return result;
	}

	private <T> void logResult(T result, LocalDateTime start) {
		Duration period = Duration.between(start, LocalDateTime.now());
		log.info("<-- {} {}", toString(result), period);
	}

	private <C> void addAuditDetail(final C command) {
		AuditRevisionListener.addDetail(command.getClass().getSimpleName().replace("Command", ""));
	}

	private <C> boolean isCommandLoggable(final C command) {
		if (log.isDebugEnabled()) {
			return true;
		}
		return command.getClass().getAnnotation(DontLogCommandExecution.class) == null;
	}

	private static class CommandToStringStyle extends ToStringStyle {
		private CommandToStringStyle() {
			super();
			this.setUseShortClassName(true);
			this.setUseIdentityHashCode(false);
		}

		@Override
		protected void appendDetail(StringBuffer buffer, String fieldName, byte[] array) {
			buffer.append("NOT LOGGED");
		}
	}

	private String toString(Object object) {
		return ToStringBuilder.reflectionToString(object, COMMAND_TO_STRING_STYLE);
	}

	@Override
	@Transactional(readOnly = true)
	public <C extends Command> ValidationResult validate(final C command) {
		Preconditions.checkNotNull(command);
		ValidationResult validationResult = new ValidationResult();
		Collection<CommandValidator<C>> validators = validatorFactory.getValidators(command);
		for (CommandValidator<C> validator : validators) {
			Set<ValidationError> errors = validator.validate(command);
			validationResult.addErrors(errors);
		}
		return validationResult;
	}
}
