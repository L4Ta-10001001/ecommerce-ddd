package com.expo.catalog.infrastructure.adapters.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;

/**
 * 🔌 Repositorio Spring Data JPA para ProductEntity.
 *    Solo la capa de infraestructura lo conoce.
 */
public interface SpringDataProductRepository extends JpaRepository<ProductJpaEntity, UUID> {
    boolean existsByName(String name);

}
