package org.juz.common.service.service;

import org.juz.common.api.Command;
import org.juz.common.command.CommandHandler;
import org.juz.common.util.GenericTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.*;

@Component
class CommandHandlerFactory {

	private static final Logger log = LoggerFactory.getLogger(CommandHandlerFactory.class);

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<?>, String> commandHandlerBeanNames = new ConcurrentHashMap<>();

	public <C extends Command, T> CommandHandler<C, T> getCommandHandler(C command) {
		return checkNotNull(createHandler(command), "Command handler not found for command " + command.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	private <C extends Command, T> CommandHandler<C, T> createHandler(C command) {
		String beanName = commandHandlerBeanNames.get(command.getClass());
		checkState(beanName != null, "Command handler not found for command " + command);
		return applicationContext.getBean(beanName, CommandHandler.class);
	}

	@PostConstruct
	public void init() {
		Map<String, CommandHandler> beans = applicationContext.getBeansOfType(CommandHandler.class);
		for (Map.Entry<String, CommandHandler> entry : beans.entrySet()) {
			CommandHandler handler = entry.getValue();
			checkHandlerIsPrototypeScoped(handler);
			String beanName = entry.getKey();
			Class<? extends CommandHandler> handlerClazz = handler.getClass();
			Class<?> commandClazz = GenericTypeUtil.extractInterfaceFirstTypeArgument(handlerClazz);
			if (handlerNotRegisteredYet(commandClazz)) {
				log.debug("registered new handler {} for command class {}", beanName, commandClazz.getSimpleName());
				commandHandlerBeanNames.put(commandClazz, beanName);
			} else if (AnnotationUtils.findAnnotation(handlerClazz, Primary.class) != null) {
				log.debug("Put primary handler {} for command class {}", beanName, commandClazz.getSimpleName());
				commandHandlerBeanNames.put(commandClazz, beanName);
			}
		}
	}

	private boolean handlerNotRegisteredYet(Class<?> commandClazz) {
		return !commandHandlerBeanNames.containsKey(commandClazz);
	}

	private void checkHandlerIsPrototypeScoped(CommandHandler bean) {
		Scope scope = AnnotationUtils.findAnnotation(bean.getClass(), Scope.class);
		checkNotNull(scope, "Command handler %s is not annotated with @Scope(prototype)", bean);
		checkArgument("prototype".equals(scope.value()), "Command handler %s is not annotated with @Scope(prototype)", bean);
	}
}
