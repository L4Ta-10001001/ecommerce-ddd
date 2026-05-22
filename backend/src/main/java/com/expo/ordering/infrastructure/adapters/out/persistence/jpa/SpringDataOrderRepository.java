package com.expo.ordering.infrastructure.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expo.ordering.infrastructure.adapters.out.persistence.entity.OrderJpaEntity;

import java.util.UUID;

/**
 * 🔌 Repositorio Spring Data JPA para OrderEntity.
 */
public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
}
