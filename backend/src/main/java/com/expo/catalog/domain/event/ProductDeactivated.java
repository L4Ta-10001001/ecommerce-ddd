package com.expo.catalog.domain.event;

import java.time.LocalDateTime;

import com.expo.shared.domain.event.DomainEvent;

public final class ProductDeactivated implements DomainEvent {

    private final String productId;
    private final LocalDateTime occurredOn;

    public ProductDeactivated(String productId) {
        this.productId = productId;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public String getProductId() {
        return productId;
    }
}