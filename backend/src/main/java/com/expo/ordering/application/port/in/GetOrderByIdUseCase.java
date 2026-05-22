package com.expo.ordering.application.port.in;

import com.expo.ordering.domain.model.order.Order;

import java.util.UUID;

public interface GetOrderByIdUseCase {
    Order execute(UUID orderId);
}
