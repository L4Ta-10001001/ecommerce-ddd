package com.expo.ordering.application.port.in;

import com.expo.ordering.application.command.PlaceOrderCommand;
import com.expo.ordering.domain.model.order.Order;

/**
 * ✅ Caso de uso: orquesta la operación sin contener lógica de negocio.
 *    La lógica de negocio vive en Order y Product (los agregados).
 * ⚠️ En Proyecto A, toda esta lógica (y más) estaría en un método de OrderService,
 *    mezclando orquestación con reglas de negocio.
 */

public interface PlaceOrderUseCase {
    Order execute(PlaceOrderCommand command);
}
