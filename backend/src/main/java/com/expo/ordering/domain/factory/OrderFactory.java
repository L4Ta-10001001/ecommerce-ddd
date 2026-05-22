package com.expo.ordering.domain.factory;

import java.util.List;

import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.model.order.OrderId;
import com.expo.ordering.domain.model.order.OrderItem;
import com.expo.ordering.domain.model.order.OrderStatus;
import com.expo.ordering.domain.valueobject.CustomerId;
import com.expo.ordering.domain.valueobject.CustomerType;
import com.expo.shared.domain.Money;

public class OrderFactory {

    public Order createNew(CustomerId customerId, CustomerType customerType) {
        return new Order(
                OrderId.newId(),
                customerId,
                customerType
        );
    }

     /**
     * ✅ Factory method para reconstituir un agregado desde persistencia.
     */
    public Order reconstitute(
            OrderId id,
            CustomerId customerId,
            CustomerType customerType,
            OrderStatus status,
            Money total,
            List<OrderItem> items
    ) {
        return Order.reconstitute(
                id,
                customerId,
                customerType,
                status,
                total,
                items
        );
    }
}