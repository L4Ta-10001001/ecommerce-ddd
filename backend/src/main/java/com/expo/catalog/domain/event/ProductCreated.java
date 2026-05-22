package com.expo.catalog.domain.event;

import java.time.LocalDateTime;

import com.expo.shared.domain.event.DomainEvent;

public final class ProductCreated implements DomainEvent {

    private final String productId;
    private final String name;
    private final LocalDateTime occurredOn;

    public ProductCreated(String productId, String name) {
        this.productId = productId;
        this.name = name;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }
}