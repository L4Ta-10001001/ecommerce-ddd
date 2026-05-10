package com.expo.ddd.infrastructure.persistence.mapper;

import com.expo.ddd.domain.model.order.Order;
import com.expo.ddd.domain.model.order.OrderItem;
import com.expo.ddd.domain.model.product.Product;
import com.expo.ddd.domain.valueobject.CustomerId;
import com.expo.ddd.domain.valueobject.OrderId;
import com.expo.ddd.domain.valueobject.Quantity;
import com.expo.ddd.infrastructure.persistence.entity.OrderEntity;
import com.expo.ddd.infrastructure.persistence.entity.OrderItemEntity;
import com.expo.ddd.infrastructure.persistence.entity.ProductEntity;

import java.util.UUID;

/**
 * ✅ Mapper bidireccional entre el agregado Order y su representación JPA.
 *    La conversión es explícita y rastreable — no hay magia de frameworks.
 */
public class OrderMapper {

    public static Order toDomain(OrderEntity entity) {
        Order order = Order.reconstitute(
                OrderId.of(UUID.randomUUID()),
                CustomerId.of(entity.getCustomerId()),
                entity.getStatus()
        );
        reconstructItemsFromEntity(order, entity);
        return order;
    }

    public static OrderEntity toEntity(Order order) {
        OrderEntity entity = buildOrderEntity(order);
        addItemEntitiesToOrder(entity, order);
        return entity;
    }

    private static void reconstructItemsFromEntity(Order order, OrderEntity entity) {
        for (OrderItemEntity itemEntity : entity.getItems()) {
            Product product = ProductMapper.toDomain(itemEntity.getProduct());
            order.reconstitueItem(product, Quantity.of(itemEntity.getQuantity()));
        }
    }

    private static OrderEntity buildOrderEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setCustomerId(order.getCustomerId().getValue());
        entity.setStatus(order.getStatus());
        entity.setTotal(order.getTotal().getAmount());
        return entity;
    }

    private static void addItemEntitiesToOrder(OrderEntity entity, Order order) {
        for (OrderItem item : order.getItems()) {
            OrderItemEntity itemEntity = buildOrderItemEntity(item, entity);
            entity.getItems().add(itemEntity);
        }
    }

    private static OrderItemEntity buildOrderItemEntity(OrderItem item, OrderEntity orderEntity) {
        OrderItemEntity itemEntity = new OrderItemEntity();
        itemEntity.setOrder(orderEntity);
        itemEntity.setProduct(buildProductEntityReference(item));
        itemEntity.setQuantity(item.getQuantity().getValue());
        itemEntity.setSubtotal(item.getSubtotal().getAmount());
        return itemEntity;
    }

    private static ProductEntity buildProductEntityReference(OrderItem item) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(item.getProduct().getId().getValue());
        productEntity.setName(item.getProduct().getName());
        productEntity.setUnitPrice(item.getProduct().getUnitPrice().getAmount());
        productEntity.setStock(item.getProduct().getStock().getValue());
        return productEntity;
    }

    private OrderMapper() {
        // Utility class — no instantiation
    }
}
