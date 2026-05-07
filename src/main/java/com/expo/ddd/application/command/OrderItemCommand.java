package com.expo.ddd.application.command;

/**
 * ✅ Command object para un ítem dentro de una orden.
 *    Evita pasar múltiples parámetros sueltos al caso de uso.
 */
public record OrderItemCommand(Long productId, int quantity) {
}
