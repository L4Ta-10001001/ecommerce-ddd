package com.expo.ordering.domain.event;
import java.time.LocalDateTime;

import com.expo.ordering.domain.model.order.OrderId;
import com.expo.ordering.domain.valueobject.CustomerId;
import com.expo.shared.domain.Money;
import com.expo.shared.domain.event.DomainEvent;

public final class OrderPlaced implements DomainEvent {

    private final OrderId orderId;
    private final CustomerId customerId;
    private final Money total;
    private final LocalDateTime occurredOn;

    public OrderPlaced(OrderId orderId, CustomerId customerId, Money total)   {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getTotal() {
        return total;
    }
}
