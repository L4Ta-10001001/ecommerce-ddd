package com.expo.catalog.domain.repository;

import java.util.Optional;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductId;

/**
 * 🔌 Puerto de salida para la recuperación de productos.
 */
public interface ProductRepository {
    void save(Product product);
    Optional<Product> findById(ProductId id);
    boolean existsByName(String productName);
}