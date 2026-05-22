package com.expo.ordering.domain.model.order;

import com.expo.shared.domain.Money;

public record ProductSnapshot(
        String productId,
        String productName,
        Money unitPrice
) {

    public ProductSnapshot {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product id is required.");
        }

        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name is required.");
        }

        if (unitPrice == null) {
            throw new IllegalArgumentException("Unit price is required.");
        }
    }
}