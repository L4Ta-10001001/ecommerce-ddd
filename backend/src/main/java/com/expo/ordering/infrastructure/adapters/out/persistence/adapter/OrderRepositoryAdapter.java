package com.expo.ordering.infrastructure.adapters.out.persistence.adapter;

import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.repository.OrderRepository;
import com.expo.ordering.infrastructure.adapters.out.persistence.entity.OrderJpaEntity;
import com.expo.ordering.infrastructure.adapters.out.persistence.jpa.SpringDataOrderRepository;
import com.expo.ordering.infrastructure.mapper.OrderMapper;

import java.util.Optional;

/**
 * 🔌 Adaptador de salida: implementa el puerto del dominio usando Spring Data JPA.
 *    El dominio define la interfaz (OrderRepository); este adaptador la implementa.
 */
public class OrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository jpaRepository;

    public OrderRepositoryAdapter(SpringDataOrderRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = OrderMapper.toEntity(order);
        OrderJpaEntity savedEntity = jpaRepository.save(entity);
        return OrderMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id)
                .map(OrderMapper::toDomain);
    }
}
