package com.expo.ordering.infrastructure.adapters.in.api.request;

import java.util.List;
import java.util.UUID;

/**
 * DTO de entrada para colocar una nueva orden.
 * El campo customerType acepta "REGULAR" o "VIP". Si se omite, se asume REGULAR.
 */
public record PlaceOrderRequest(UUID customerId, String customerType, List<OrderItemRequest> items) {
}
