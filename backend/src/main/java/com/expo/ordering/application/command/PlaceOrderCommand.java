package com.expo.ordering.application.command;

import java.util.List;

import com.expo.ordering.domain.valueobject.CustomerId;
import com.expo.ordering.domain.valueobject.CustomerType;

/**
 * ✅ Command object que transporta la intención del usuario al caso de uso.
 *    El caso de uso no conoce HTTP ni JSON — solo este objeto de comando.
 */
public record PlaceOrderCommand(
    CustomerId customerId,
    CustomerType customerType,
    List<OrderItemCommand> items
) {}
