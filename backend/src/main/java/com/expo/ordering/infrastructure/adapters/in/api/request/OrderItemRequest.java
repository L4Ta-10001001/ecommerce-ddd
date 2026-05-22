package com.expo.ordering.infrastructure.adapters.in.api.request;

import java.util.UUID;

/**
 * DTO para un ítem de orden en la solicitud HTTP.
 */
public record OrderItemRequest(UUID productId, int quantity) {
}
