package com.expo.ordering.application.usecase;

import com.expo.ordering.application.port.in.GetOrderByIdUseCase;
import com.expo.ordering.domain.exception.OrderNotFoundException;
import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.repository.OrderRepository;

import java.util.UUID;

public class GetOrderByIdService implements GetOrderByIdUseCase {

    private final OrderRepository orderRepository;

    public GetOrderByIdService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));
    }
}
