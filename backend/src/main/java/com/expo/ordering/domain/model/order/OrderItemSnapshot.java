package com.expo.ordering.domain.model.order;

import com.expo.shared.domain.Money;

public record OrderItemSnapshot(
        String productId,
        String productName,
        int quantity,
        Money unitPrice
) {}