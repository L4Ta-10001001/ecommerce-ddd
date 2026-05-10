package com.expo.ddd.api.dto;

/**
 * DTO para un ítem de orden en la solicitud HTTP.
 */
public record OrderItemRequest(Long productId, int quantity) {
}
