package com.expo.catalog.infrastructure.adapters.out.event;

import org.springframework.context.ApplicationEventPublisher;
import com.expo.catalog.application.port.out.DomainEventPublisher;

public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}