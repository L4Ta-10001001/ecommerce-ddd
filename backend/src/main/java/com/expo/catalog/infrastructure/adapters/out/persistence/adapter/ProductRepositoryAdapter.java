package com.expo.catalog.infrastructure.adapters.out.persistence.adapter;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.repository.ProductRepository;
import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;
import com.expo.catalog.infrastructure.mapper.ProductMapper;

import java.util.Optional;

/**
 * 🔌 Adaptador de salida: implementa el puerto del dominio usando Spring Data JPA.
 *    El dominio solo ve ProductRepository (la interfaz); nunca ve esta clase.
 * ✅ La dirección de la dependencia fluye hacia el dominio, no al revés.
 */
public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository jpaRepository;

    public ProductRepositoryAdapter(SpringDataProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = ProductMapper.toEntity(product);
        ProductJpaEntity savedEntity = jpaRepository.save(entity);
        return ProductMapper.toDomain(savedEntity);
    }
}
