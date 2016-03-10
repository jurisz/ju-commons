package org.juz.common.events;

public interface DomainEventHandler<T extends DomainEvent> {

	void handle(T event);
}
