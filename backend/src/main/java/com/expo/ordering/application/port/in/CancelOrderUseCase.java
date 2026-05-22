package com.expo.ordering.application.port.in;

import com.expo.ordering.domain.model.order.Order;

import java.util.UUID;

/**
 * ✅ Caso de uso de cancelación: delega la lógica de restauración de stock al agregado Order.
 *    Este servicio de aplicación es deliberadamente delgado.
 */
public interface CancelOrderUseCase {
    Order execute(UUID orderId);
}
