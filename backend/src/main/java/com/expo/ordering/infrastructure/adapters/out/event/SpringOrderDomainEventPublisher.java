package com.expo.ordering.infrastructure.adapters.out.event;

import org.springframework.context.ApplicationEventPublisher;

import com.expo.ordering.application.port.out.DomainEventPublisher;

public class SpringOrderDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public SpringOrderDomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}