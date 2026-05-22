package com.expo.ordering.domain.event;

import java.time.LocalDateTime;

import com.expo.shared.domain.event.DomainEvent;

public final class OrderConfirmed implements DomainEvent {

    private final String orderId;
    private final LocalDateTime occurredOn;

    public OrderConfirmed(String orderId) {
        this.orderId = orderId;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public String getOrderId() {
        return orderId;
    }
}
