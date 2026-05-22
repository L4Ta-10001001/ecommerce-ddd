package com.expo.ordering.application.command;

import java.util.UUID;

/**
 * ✅ Command object para un ítem dentro de una orden.
 *    Evita pasar múltiples parámetros sueltos al caso de uso.
 */
public record OrderItemCommand(
    UUID productId,
    int quantity
) {}
