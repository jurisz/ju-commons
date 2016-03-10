package org.juz.common.events;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public final class DomainEvents {

	private static final Logger log = LoggerFactory.getLogger(DomainEvents.class);

	private static final Multimap<Class<? extends DomainEvent>, DomainEventHandler<? extends DomainEvent>> handlers = createHandlersMap();
	private static transient DomainEvent lastEvent;

	private DomainEvents() {
		super();
	}

	private static Multimap<Class<? extends DomainEvent>, DomainEventHandler<? extends DomainEvent>> createHandlersMap() {
		Multimap<Class<? extends DomainEvent>, DomainEventHandler<? extends DomainEvent>> map = ArrayListMultimap.create();
		return Multimaps.synchronizedMultimap(map);
	}

	@SuppressWarnings({"unchecked"})
	public static <T extends DomainEvent> void publish(final T event) {
		log(event);
		lastEvent = event;
		Collection<DomainEventHandler<? extends DomainEvent>> domainEventHandlers = handlers.get(event.getClass());
		domainEventHandlers.forEach(x -> ((DomainEventHandler<T>) x).handle(event));
	}

	private static void log(DomainEvent event) {
		String eventString = ToStringBuilder.reflectionToString(event, ToStringStyle.SHORT_PREFIX_STYLE);
		log.info("Event {}", eventString);
	}

	public synchronized static <T extends DomainEvent> void subscribe(Class<T> eventType, DomainEventHandler<T> handler) {
		handlers.put(eventType, handler);
	}

	public synchronized static <T extends DomainEvent> void unsubscribe(Class<T> eventType, DomainEventHandler<T> handler) {
		handlers.remove(eventType, handler);
	}

	@VisibleForTesting
	public static DomainEvent getLastEvent() {
		return lastEvent;
	}

	@VisibleForTesting
	public static void clearLastEvent() {
		lastEvent = null;
	}

}
