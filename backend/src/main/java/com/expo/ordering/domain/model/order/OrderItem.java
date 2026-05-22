package com.expo.ordering.domain.model.order;

import com.expo.shared.domain.Money;
import com.expo.shared.domain.Quantity;

/**
 * ✅ Entidad hija del agregado Order. Solo existe dentro del contexto de una Order.
 * No tiene repositorio propio — se accede siempre a través del Aggregate Root.
 */

public class OrderItem {

    private final ProductSnapshot product;
    private final Quantity quantity;

    public OrderItem(ProductSnapshot product, Quantity quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product snapshot is required.");
        }

        if (quantity == null) {
            throw new IllegalArgumentException("Quantity is required.");
        }

        this.product = product;
        this.quantity = quantity;
    }

    public Money getSubtotal() {
        return product.unitPrice().multiply(quantity.getValue());
    }

    public ProductSnapshot getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
