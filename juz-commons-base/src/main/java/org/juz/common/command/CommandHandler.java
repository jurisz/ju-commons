package org.juz.common.command;


import org.juz.common.api.Command;

public interface CommandHandler<C extends Command, T> {

	T execute(C command);

}
