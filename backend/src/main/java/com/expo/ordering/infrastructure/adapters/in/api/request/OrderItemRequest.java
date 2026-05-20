package com.expo.ordering.infrastructure.adapters.in.api.request;

/**
 * DTO para un ítem de orden en la solicitud HTTP.
 */
public record OrderItemRequest(Long productId, int quantity) {
}
