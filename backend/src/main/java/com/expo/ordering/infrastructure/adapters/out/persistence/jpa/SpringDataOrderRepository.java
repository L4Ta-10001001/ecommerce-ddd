package com.expo.ddd.infrastructure.persistence.jpa;

import com.expo.ddd.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 🔌 Repositorio Spring Data JPA para OrderEntity.
 */
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
