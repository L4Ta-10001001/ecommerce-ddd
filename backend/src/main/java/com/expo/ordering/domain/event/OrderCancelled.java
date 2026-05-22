package com.expo.ordering.domain.event;

import java.time.LocalDateTime;
import java.util.List;

import com.expo.ordering.domain.model.order.OrderId;
import com.expo.ordering.domain.model.order.OrderItemSnapshot;
import com.expo.shared.domain.event.DomainEvent;

public class OrderCancelled implements DomainEvent {

    private final OrderId orderId;
    private final List<OrderItemSnapshot> items;
    private final LocalDateTime occurredOn;

    public OrderCancelled(OrderId orderId, List<OrderItemSnapshot> items) {
        this.orderId = orderId;
        this.items = items;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public OrderId orderId() {
        return orderId;
    }

    public List<OrderItemSnapshot> items() {
        return items;
    }
}
