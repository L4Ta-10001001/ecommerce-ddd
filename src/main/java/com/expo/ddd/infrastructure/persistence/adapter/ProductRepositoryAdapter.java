package com.expo.ddd.infrastructure.persistence.adapter;

import com.expo.ddd.domain.model.product.Product;
import com.expo.ddd.domain.repository.ProductRepository;
import com.expo.ddd.infrastructure.persistence.entity.ProductEntity;
import com.expo.ddd.infrastructure.persistence.jpa.ProductJpaRepository;
import com.expo.ddd.infrastructure.persistence.mapper.ProductMapper;

import java.util.Optional;

/**
 * 🔌 Adaptador de salida: implementa el puerto del dominio usando Spring Data JPA.
 *    El dominio solo ve ProductRepository (la interfaz); nunca ve esta clase.
 * ✅ La dirección de la dependencia fluye hacia el dominio, no al revés.
 */
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryAdapter(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        ProductEntity savedEntity = jpaRepository.save(entity);
        return ProductMapper.toDomain(savedEntity);
    }
}
