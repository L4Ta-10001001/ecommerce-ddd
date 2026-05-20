package com.expo.catalog.application.port.out;

public interface DomainEventPublisher {
    void publish(Object event);
}