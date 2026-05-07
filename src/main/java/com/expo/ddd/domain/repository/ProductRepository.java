package com.expo.ddd.domain.repository;

import com.expo.ddd.domain.model.product.Product;

import java.util.Optional;

/**
 * 🔌 Puerto de salida para la recuperación de productos.
 */
public interface ProductRepository {

    Optional<Product> findById(Long id);

    Product save(Product product);
}
