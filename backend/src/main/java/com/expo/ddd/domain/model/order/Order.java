package com.expo.ddd.domain.model.order;

import com.expo.ddd.domain.exception.InsufficientStockException;
import com.expo.ddd.domain.model.product.Product;
import com.expo.ddd.domain.valueobject.CustomerId;
import com.expo.ddd.domain.valueobject.Money;
import com.expo.ddd.domain.valueobject.OrderId;
import com.expo.ddd.domain.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ✅ Aggregate Root principal. Contiene TODA la lógica de negocio de una orden.
 * ⚠️ En Proyecto A, Order es solo un @Entity anémico con getters/setters.
 *    Toda la lógica vive en OrderService, lo que dificulta razonar sobre el dominio.
 */
public class Order {

    private final OrderId id;
    private final CustomerId customerId;
    private OrderStatus status;
    private Money total;
    private final List<OrderItem> items;

    public Order(OrderId id, CustomerId customerId) {
        this.id = id;
        this.customerId = customerId;
        this.status = OrderStatus.PENDING;
        this.total = new Money(BigDecimal.ZERO);
        this.items = new ArrayList<>();
    }

    /**
     * ✅ Factory method para reconstituir un agregado desde persistencia.
     *    Separado del constructor público para dejar claro que no es creación nueva.
     */
    public static Order reconstitute(OrderId id, CustomerId customerId, OrderStatus status) {
        Order order = new Order(id, customerId);
        order.status = status;
        return order;
    }

    // ✅ Regla de negocio encapsulada: validación de stock + decremento + cálculo de total
    public void addItem(Product product, Quantity quantity) {
        if (!product.hasStock(quantity)) {
            throw new InsufficientStockException(product.getName());
        }
        product.decrementStock(quantity);
        items.add(new OrderItem(product, quantity));
        recalculateTotal();
    }

    /**
     * ✅ Usado exclusivamente para reconstituir una Order desde persistencia.
     *    No valida ni modifica el stock (ya fue modificado cuando se creó la orden original).
     */
    public void reconstitueItem(Product product, Quantity quantity) {
        items.add(new OrderItem(product, quantity));
        recalculateTotal();
    }

    public void confirm() {
        this.status = OrderStatus.CONFIRMED;
    }

    // ✅ La cancelación restaura el stock — responsabilidad del agregado, no de un Service
    public void cancel() {
        validateNotAlreadyCancelled();
        restoreAllItemsStock();
        this.status = OrderStatus.CANCELLED;
    }

    public OrderId getId() {
        return id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Money getTotal() {
        return total;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private void recalculateTotal() {
        Money runningTotal = new Money(BigDecimal.ZERO);
        for (OrderItem item : items) {
            runningTotal = runningTotal.add(item.getSubtotal());
        }
        this.total = runningTotal;
    }

    private void restoreAllItemsStock() {
        for (OrderItem item : items) {
            item.getProduct().restoreStock(item.getQuantity());
        }
    }

    private void validateNotAlreadyCancelled() {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }
    }
}
