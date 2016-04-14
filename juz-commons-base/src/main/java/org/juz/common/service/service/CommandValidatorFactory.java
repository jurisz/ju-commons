package org.juz.common.service.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.juz.common.api.Command;
import org.juz.common.command.CommandValidator;
import org.juz.common.util.GenericTypeUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class CommandValidatorFactory implements InitializingBean {

	private Multimap<Class<?>, CommandValidator<?>> commandValidators = ArrayListMultimap.create();

	private CommandConstraintsValidator commandConstraintsValidator = new CommandConstraintsValidator();

	@Autowired(required = false)
	private List<CommandValidator> foundValidators = Lists.newArrayList();

	@Override
	public void afterPropertiesSet() throws Exception {
		for (CommandValidator<?> validator : foundValidators) {
			Class<?> commandClazz = extractCommandClazz(validator);
			if (!commandValidators.containsKey(commandClazz)) {
				commandValidators.put(commandClazz, commandConstraintsValidator);
			}
			commandValidators.put(commandClazz, validator);
		}
	}

	private <C extends Command> Class<C> extractCommandClazz(CommandValidator<C> validator) {
		return GenericTypeUtil.extractInterfaceFirstTypeArgument(validator.getClass());
	}

	@SuppressWarnings("unchecked")
	public <C extends Command> List<CommandValidator<C>> getValidators(C command) {
		Collection validators = commandValidators.get(command.getClass());
		if (validators.isEmpty()) {
			validators.add(commandConstraintsValidator);
		}
		return (List<CommandValidator<C>>) validators;
	}
}
