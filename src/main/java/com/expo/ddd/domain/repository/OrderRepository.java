package com.expo.ddd.domain.repository;

import com.expo.ddd.domain.model.order.Order;

import java.util.Optional;

/**
 * 🔌 Puerto de salida del dominio. El dominio define la interfaz;
 *    la infraestructura provee la implementación (Adapter).
 * ✅ El dominio no sabe nada de JPA ni de bases de datos.
 */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);
}
