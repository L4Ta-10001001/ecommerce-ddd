package com.expo.ordering.domain.model.order;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.shared.Money;
import com.expo.ordering.domain.model.shared.Quantity;

/**
 * ✅ Entidad hija del agregado Order. Solo existe dentro del contexto de una Order.
 * No tiene repositorio propio — se accede siempre a través del Aggregate Root.
 */
public class OrderItem {

    private final Product product;
    private final Quantity quantity;
    private final Money subtotal;

    public OrderItem(Product product, Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = quantity.multiply(product.getUnitPrice());
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getSubtotal() {
        return subtotal;
    }
}
