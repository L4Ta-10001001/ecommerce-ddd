package com.expo.ddd.application.command;

import java.util.List;
import java.util.UUID;

/**
 * ✅ Command object que transporta la intención del usuario al caso de uso.
 *    El caso de uso no conoce HTTP ni JSON — solo este objeto de comando.
 */
public record PlaceOrderCommand(UUID customerId, List<OrderItemCommand> items) {
}
