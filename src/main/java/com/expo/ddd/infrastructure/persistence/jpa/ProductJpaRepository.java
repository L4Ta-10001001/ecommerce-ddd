package com.expo.ddd.infrastructure.persistence.jpa;

import com.expo.ddd.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 🔌 Repositorio Spring Data JPA para ProductEntity.
 *    Solo la capa de infraestructura lo conoce.
 */
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
