package com.expo.ddd.domain;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.model.shared.Money;
import com.expo.ddd.domain.valueobject.*;
import com.expo.ordering.domain.exception.InsufficientStockException;
import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.model.order.OrderId;
import com.expo.ordering.domain.model.order.OrderStatus;
import com.expo.ordering.domain.model.shared.Quantity;
import com.expo.ordering.domain.valueobject.CustomerId;
import com.expo.ordering.domain.valueobject.CustomerType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ✅ Tests puramente de dominio. CERO Spring, CERO H2, CERO mocks de JPA.
 * ⚠️ En Proyecto A es imposible testear el dominio sin levantar el contexto de Spring
 *    porque la lógica vive en @Service con @Autowired — fuertemente acoplado al framework.
 *
 * Aplica F.I.R.S.T:
 *   Fast        — puro Java, sin I/O
 *   Independent — cada test crea su propio estado
 *   Repeatable  — no depende de BD ni red
 *   Self-Validating — assertTrue/assertEquals definen éxito
 *   Timely      — escritos para guiar el diseño
 */
class OrderDomainTest {

    private Product laptop;
    private Product mouse;

    @BeforeEach
    void setUp() {
        laptop = buildProduct(1L, "Laptop", "1200.00", 10);
        mouse = buildProduct(2L, "Mouse", "25.00", 50);
    }

    // ✅ El agregado Order encapsula la regla de confirmación
    @Test
    @DisplayName("shouldConfirmOrderWhenStockIsSufficient")
    void shouldConfirmOrderWhenStockIsSufficient() {
        Order order = buildRegularOrder();

        order.addItem(laptop, Quantity.of(2));
        order.confirm();

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertEquals(8, laptop.getStock().getValue(), "Stock should be decremented by 2");
        assertEquals(new BigDecimal("2400.00"), order.getTotal().getAmount());
    }

    // ✅ La regla de stock vive en el dominio, no en un Service
    @Test
    @DisplayName("shouldThrowExceptionWhenStockIsInsufficient")
    void shouldThrowExceptionWhenStockIsInsufficient() {
        Order order = buildRegularOrder();

        InsufficientStockException exception = assertThrows(
                InsufficientStockException.class,
                () -> order.addItem(laptop, Quantity.of(15))
        );

        assertTrue(exception.getMessage().contains("Laptop"));
    }

    // ✅ La cancelación y restauración de stock es responsabilidad del agregado
    @Test
    @DisplayName("shouldRestoreStockWhenOrderIsCancelled")
    void shouldRestoreStockWhenOrderIsCancelled() {
        Order order = buildRegularOrder();
        order.addItem(mouse, Quantity.of(3));
        order.confirm();
        assertEquals(47, mouse.getStock().getValue());

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals(50, mouse.getStock().getValue(), "Stock should be fully restored to 50");
    }

    // ✅ El agregado protege su propio estado
    @Test
    @DisplayName("shouldThrowExceptionWhenCancellingAlreadyCancelledOrder")
    void shouldThrowExceptionWhenCancellingAlreadyCancelledOrder() {
        Order order = buildRegularOrder();
        order.addItem(mouse, Quantity.of(1));
        order.cancel();

        assertThrows(
                IllegalStateException.class,
                order::cancel,
                "Cancelling an already cancelled order must throw IllegalStateException"
        );
    }

    // ✅ Los Value Objects se auto-validan en construcción
    @Test
    @DisplayName("shouldRejectNegativeMoney")
    void shouldRejectNegativeMoney() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Money(BigDecimal.valueOf(-100)),
                "Money with negative amount must throw IllegalArgumentException"
        );
    }

    // ✅ Los Value Objects garantizan que valores inválidos nunca existen
    @Test
    @DisplayName("shouldRejectZeroQuantity")
    void shouldRejectZeroQuantity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Quantity(0),
                "Quantity of zero must throw IllegalArgumentException"
        );
    }

    // ✅ El dominio maneja la regla VIP sin tocar infraestructura
    @Test
    @DisplayName("shouldAllowVipOrderEvenWithInsufficientStock")
    void shouldAllowVipOrderEvenWithInsufficientStock() {
        Order vipOrder = buildVipOrder();

        vipOrder.addItem(laptop, Quantity.of(99));
        vipOrder.confirm();

        assertEquals(OrderStatus.CONFIRMED, vipOrder.getStatus());
        assertEquals(new BigDecimal("118800.00"), vipOrder.getTotal().getAmount());
    }

    // ✅ La regla original permanece intacta para clientes regulares
    @Test
    @DisplayName("shouldRejectRegularOrderWithInsufficientStock")
    void shouldRejectRegularOrderWithInsufficientStock() {
        Order regularOrder = buildRegularOrder();

        assertThrows(
                InsufficientStockException.class,
                () -> regularOrder.addItem(laptop, Quantity.of(99)),
                "Regular customer order must be rejected when stock is insufficient"
        );
    }

    private Order buildRegularOrder() {
        return new Order(
                OrderId.of(UUID.randomUUID()),
                CustomerId.of(UUID.randomUUID()),
                CustomerType.REGULAR
        );
    }

    private Order buildVipOrder() {
        return new Order(
                OrderId.of(UUID.randomUUID()),
                CustomerId.of(UUID.randomUUID()),
                CustomerType.VIP
        );
    }

    private Product buildProduct(Long id, String name, String price, int stock) {
        return new Product(
                ProductId.of(id),
                name,
                new Money(new BigDecimal(price)),
                new Quantity(stock)
        );
    }
}
