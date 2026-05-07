package com.expo.ddd.infrastructure.persistence.adapter;

import com.expo.ddd.domain.model.order.Order;
import com.expo.ddd.domain.repository.OrderRepository;
import com.expo.ddd.infrastructure.persistence.entity.OrderEntity;
import com.expo.ddd.infrastructure.persistence.jpa.OrderJpaRepository;
import com.expo.ddd.infrastructure.persistence.mapper.OrderMapper;

import java.util.Optional;

/**
 * 🔌 Adaptador de salida: implementa el puerto del dominio usando Spring Data JPA.
 *    El dominio define la interfaz (OrderRepository); este adaptador la implementa.
 */
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    public OrderRepositoryAdapter(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        OrderEntity savedEntity = jpaRepository.save(entity);
        return OrderMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id)
                .map(OrderMapper::toDomain);
    }
}
