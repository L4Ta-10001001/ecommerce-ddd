package com.expo.ordering.application.port.out;

public interface DomainEventPublisher {
    void publish(Object event);
}
