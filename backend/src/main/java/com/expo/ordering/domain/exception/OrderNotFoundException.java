package com.expo.ordering.domain.exception;

/**
 * ✅ Excepción de dominio para cuando un pedido no existe.
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Object orderId) {
        super("Order not found with id: " + orderId);
    }
}
