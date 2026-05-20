package com.expo.ordering.infrastructure.mapper;

import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.infrastructure.adapters.out.persistence.entity.ProductJpaEntity;
import com.expo.catalog.infrastructure.mapper.ProductMapper;
import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.model.order.OrderId;
import com.expo.ordering.domain.model.order.OrderItem;
import com.expo.ordering.domain.model.shared.Quantity;
import com.expo.ordering.domain.valueobject.CustomerId;
import com.expo.ordering.domain.valueobject.CustomerType;
import com.expo.ordering.infrastructure.adapters.out.persistence.entity.OrderJpaEntity;
import com.expo.ordering.infrastructure.adapters.out.persistence.entity.OrderItemJpaEntity;

import java.util.UUID;

/**
 * ✅ Mapper bidireccional entre el agregado Order y su representación JPA.
 *    La conversión es explícita y rastreable — no hay magia de frameworks.
 */
public class OrderMapper {

    public static Order toDomain(OrderJpaEntity entity) {
        Order order = Order.reconstitute(
                OrderId.of(UUID.randomUUID()),
                CustomerId.of(entity.getCustomerId()),
                entity.getCustomerType(),
                entity.getStatus()
        );
        reconstructItemsFromEntity(order, entity);
        return order;
    }

    public static OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = buildOrderEntity(order);
        addItemEntitiesToOrder(entity, order);
        return entity;
    }

    private static void reconstructItemsFromEntity(Order order, OrderJpaEntity entity) {
        for (OrderItemJpaEntity itemEntity : entity.getItems()) {
            Product product = ProductMapper.toDomain(itemEntity.getProduct());
            order.reconstitueItem(product, Quantity.of(itemEntity.getQuantity()));
        }
    }

    private static OrderJpaEntity buildOrderEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setCustomerId(order.getCustomerId().getValue());
        entity.setCustomerType(order.getCustomerType());
        entity.setStatus(order.getStatus());
        entity.setTotal(order.getTotal().getAmount());
        return entity;
    }

    private static void addItemEntitiesToOrder(OrderJpaEntity entity, Order order) {
        for (OrderItem item : order.getItems()) {
            OrderItemJpaEntity itemEntity = buildOrderItemEntity(item, entity);
            entity.getItems().add(itemEntity);
        }
    }

    private static OrderItemJpaEntity buildOrderItemEntity(OrderItem item, OrderJpaEntity orderEntity) {
        OrderItemJpaEntity itemEntity = new OrderItemJpaEntity();
        itemEntity.setOrder(orderEntity);
        itemEntity.setProduct(buildProductEntityReference(item));
        itemEntity.setQuantity(item.getQuantity().getValue());
        itemEntity.setSubtotal(item.getSubtotal().getAmount());
        return itemEntity;
    }

    private static ProductJpaEntity buildProductEntityReference(OrderItem item) {
        ProductJpaEntity productEntity = new ProductJpaEntity();
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
