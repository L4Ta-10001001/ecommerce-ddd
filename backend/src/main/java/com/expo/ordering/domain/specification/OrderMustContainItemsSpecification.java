package com.expo.ordering.domain.specification;

import java.util.List;

import com.expo.ordering.domain.model.order.OrderItem;

public class OrderMustContainItemsSpecification {

    public boolean isSatisfiedBy(List<OrderItem> items) {
        return items != null && !items.isEmpty();
    }
}
