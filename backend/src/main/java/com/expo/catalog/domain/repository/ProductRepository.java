package com.expo.catalog.domain.repository;

import java.util.Optional;

import com.expo.catalog.domain.model.product.Product;

/**
 * 🔌 Puerto de salida para la recuperación de productos.
 */
public interface ProductRepository {

    Optional<Product> findById(Long id);

    Product save(Product product);
}
