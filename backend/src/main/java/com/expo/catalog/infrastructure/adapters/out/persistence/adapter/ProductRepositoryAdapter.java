package com.expo.catalog.infrastructure.adapters.out.persistence.adapter;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.repository.ProductRepository;
import com.expo.catalog.infrastructure.adapters.out.persistence.jpa.SpringDataProductRepository;
import com.expo.catalog.infrastructure.mapper.ProductMapper;
import com.expo.catalog.domain.model.product.ProductId;

import java.util.Optional;

/**
 * 🔌 Adaptador de salida: implementa el puerto del dominio usando Spring Data JPA.
 *    El dominio solo ve ProductRepository (la interfaz); nunca ve esta clase.
 * ✅ La dirección de la dependencia fluye hacia el dominio, no al revés.
 */
public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository jpaRepository;
    private final ProductMapper mapper;

    public ProductRepositoryAdapter(
        SpringDataProductRepository jpaRepository,
        ProductMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Product product) {
        jpaRepository.save(mapper.toEntity(product));
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByName(String productName) {
        return jpaRepository.existsByName(productName);
    }

}
