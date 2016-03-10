package org.juz.common.command;


import org.juz.common.api.Command;
import org.juz.common.api.ValidationResult;

public interface CommandService {

	<C extends Command> void enqueue(C command);

	<C extends Command> void enqueueSingleOnTransaction(final C command, String operationId);

	/**
	 * using this do not have effect with activemq, if not configured
	 *
	 * @param command
	 * @param <C>
	 */
	<C extends Command> void enqueueWithHighPriority(C command);

	<C extends Command, T> T execute(C command);

	<C extends Command> ValidationResult validate(C command);
}
