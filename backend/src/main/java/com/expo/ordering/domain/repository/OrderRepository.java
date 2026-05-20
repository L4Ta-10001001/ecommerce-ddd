package com.expo.ordering.domain.repository;

import java.util.Optional;

import com.expo.ordering.domain.model.order.Order;

/**
 * 🔌 Puerto de salida del dominio. El dominio define la interfaz;
 *    la infraestructura provee la implementación (Adapter).
 * ✅ El dominio no sabe nada de JPA ni de bases de datos.
 */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);
}
