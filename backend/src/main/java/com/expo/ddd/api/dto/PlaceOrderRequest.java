package com.expo.ddd.api.dto;

import java.util.List;
import java.util.UUID;

/**
 * DTO de entrada para colocar una nueva orden.
 */
public record PlaceOrderRequest(UUID customerId, List<OrderItemRequest> items) {
}
