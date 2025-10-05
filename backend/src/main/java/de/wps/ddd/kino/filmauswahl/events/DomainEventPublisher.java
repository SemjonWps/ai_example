package de.wps.ddd.kino.filmauswahl.events;

public interface DomainEventPublisher {
    void publish(Object event);
}
