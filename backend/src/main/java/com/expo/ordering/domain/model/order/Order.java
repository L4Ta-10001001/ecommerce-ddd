package com.expo.ordering.domain.model.order;

import com.expo.ordering.domain.event.OrderCancelled;
import com.expo.ordering.domain.event.OrderConfirmed;
import com.expo.ordering.domain.event.OrderPlaced;
import com.expo.ordering.domain.exception.DomainException;
import com.expo.ordering.domain.exception.InvalidOrderStateException;
import com.expo.ordering.domain.valueobject.CustomerId;
import com.expo.ordering.domain.valueobject.CustomerType;
import com.expo.shared.domain.Currency;
import com.expo.shared.domain.Money;
import com.expo.shared.domain.Quantity;
import com.expo.shared.domain.event.DomainEvent;

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
    private final List<OrderItem> items = new ArrayList<>();
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Order(OrderId id, CustomerId customerId, CustomerType customerType) {
        if (id == null) throw new IllegalArgumentException("Order id is required.");
        if (customerId == null) throw new IllegalArgumentException("Customer id is required.");
        if (customerType == null) throw new IllegalArgumentException("Customer type is required.");

        this.id = id;
        this.customerId = customerId;
        this.customerType = customerType;
        this.status = OrderStatus.PENDING;
        this.total = Money.zero(new Currency("USD"));
    }

    public static Order reconstitute(
            OrderId id,
            CustomerId customerId,
            CustomerType customerType,
            OrderStatus status,
            Money total,
            List<OrderItem> items
    ) {
        Order order = new Order(id, customerId, customerType);

        order.status = status;
        order.total = total;
        order.items.addAll(items);

        return order;
    }

    // 🔧 ESCALABILIDAD: nueva regla agregada SOLO aquí.
    // En el Proyecto A (N-Capas / modelo anémico) este cambio habría
    // requerido modificar OrderService mezclando lógica de negocio
    // con código de persistencia. Aquí solo cambia el agregado.
    public void addItem(ProductSnapshot product, Quantity quantity) {
        validateNotClosed();
        items.add(new OrderItem(product, quantity));
        recalculateTotal();
    }

    public void confirm() {
        if (status != OrderStatus.PLACED) {
            throw new InvalidOrderStateException("Only placed orders can be confirmed.");
        }

        this.status = OrderStatus.CONFIRMED;
        domainEvents.add(new OrderConfirmed(id.value().toString()));
    }

    // ✅ La cancelación restaura el stock — responsabilidad del agregado, no de un Service
    public void cancel() {
        if (status == OrderStatus.CANCELLED) throw new InvalidOrderStateException("Order already cancelled.");

        this.status = OrderStatus.CANCELLED;
        
        domainEvents.add(
            new OrderCancelled(this.id, this.toSnapshots())
        );
    }

    public void place() {
        validateCanBePlaced();

        if (items.isEmpty()) {
            throw new DomainException("Order must have at least one item");
        }

        this.status = OrderStatus.PLACED;

        domainEvents.add(new OrderPlaced(id, customerId, total));
    }

    private void recalculateTotal() {
        Money runningTotal = Money.zero(items.get(0).getSubtotal().currency());

        for (OrderItem item : items) {
            runningTotal = runningTotal.add(item.getSubtotal());
        }

        this.total = runningTotal;
    }

    private void validateCanBePlaced() {
        if (status != OrderStatus.PENDING) {
            throw new InvalidOrderStateException("Only draft orders can be placed.");
        }
    }

    private void validateNotClosed() {
        if (status == OrderStatus.CONFIRMED || status == OrderStatus.CANCELLED) {
            throw new InvalidOrderStateException("Order is already closed.");
        }
    }

    private List<OrderItemSnapshot> toSnapshots() {
        return items.stream()
                .map(item -> new OrderItemSnapshot(
                        item.getProduct().productId(),     
                        item.getProduct().productName(), 
                        item.getQuantity().getValue(),    
                        item.getProduct().unitPrice()   
                ))
                .toList();
    }

    /**
     * Extrae y limpia los eventos acumulados.
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
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
}
