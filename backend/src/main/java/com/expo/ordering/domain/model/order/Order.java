package com.expo.ordering.domain.model.order;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.shared.Money;
import com.expo.ordering.domain.exception.InsufficientStockException;
import com.expo.ordering.domain.model.shared.Quantity;
import com.expo.ordering.domain.valueObject.CustomerId;
import com.expo.ordering.domain.valueObject.CustomerType;

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
    private final CustomerType customerType;
    private OrderStatus status;
    private Money total;
    private final List<OrderItem> items;

    public Order(OrderId id, CustomerId customerId, CustomerType customerType) {
        this.id = id;
        this.customerId = customerId;
        this.customerType = customerType;
        this.status = OrderStatus.PENDING;
        this.total = new Money(BigDecimal.ZERO);
        this.items = new ArrayList<>();
    }

    /**
     * ✅ Factory method para reconstituir un agregado desde persistencia.
     *    Separado del constructor público para dejar claro que no es creación nueva.
     */
    public static Order reconstitute(OrderId id, CustomerId customerId,
                                     CustomerType customerType, OrderStatus status) {
        Order order = new Order(id, customerId, customerType);
        order.status = status;
        return order;
    }

    // 🔧 ESCALABILIDAD: nueva regla agregada SOLO aquí.
    // En el Proyecto A (N-Capas / modelo anémico) este cambio habría
    // requerido modificar OrderService mezclando lógica de negocio
    // con código de persistencia. Aquí solo cambia el agregado.
    public void addItem(Product product, Quantity quantity) {
        if (isRegularCustomer()) {
            validateStock(product, quantity);
        }
        applyStockReduction(product, quantity);
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

    public CustomerType getCustomerType() {
        return customerType;
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

    private boolean isRegularCustomer() {
        return this.customerType == CustomerType.REGULAR;
    }

    private void applyStockReduction(Product product, Quantity quantity) {
        if (isRegularCustomer()) {
            product.decrementStock(quantity);
        } else {
            product.reserveStockForVip(quantity);
        }
    }

    private void validateStock(Product product, Quantity quantity) {
        if (!product.hasStock(quantity)) {
            throw new InsufficientStockException(product.getName());
        }
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
