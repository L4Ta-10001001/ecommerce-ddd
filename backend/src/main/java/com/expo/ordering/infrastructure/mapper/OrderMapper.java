package com.expo.ordering.infrastructure.mapper;

import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.model.order.OrderId;
import com.expo.ordering.domain.model.order.OrderItem;
import com.expo.ordering.domain.model.order.ProductSnapshot;
import com.expo.ordering.domain.valueobject.CustomerId;
import com.expo.ordering.infrastructure.adapters.out.persistence.entity.OrderJpaEntity;
import com.expo.shared.domain.Currency;
import com.expo.shared.domain.Money;
import com.expo.shared.domain.Quantity;
import com.expo.ordering.infrastructure.adapters.out.persistence.entity.OrderItemJpaEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ✅ Mapper bidireccional entre el agregado Order y su representación JPA.
 *    La conversión es explícita y rastreable — no hay magia de frameworks.
 */
public class OrderMapper {

    public static Order toDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
            .map(itemEntity -> {

                ProductSnapshot snapshot = new ProductSnapshot(
                    itemEntity.getProductId().toString(),
                    itemEntity.getProductName().toString(),
                    new Money(
                        itemEntity.getUnitPrice(),
                        new Currency("USD"))
                );

                return new OrderItem(
                    snapshot,
                    new Quantity(itemEntity.getQuantity())
                );

            })
            .toList();

        return Order.reconstitute(
                new OrderId(entity.getId()),
                new CustomerId(entity.getCustomerId()),
                entity.getCustomerType(),
                entity.getStatus(),
                new Money(entity.getTotal(), new Currency("USD")),
                items
        );
    }

    public static OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();

        entity.setId(order.getId().value());

        entity.setCustomerId(order.getCustomerId().value());
        entity.setCustomerType(order.getCustomerType());
        entity.setStatus(order.getStatus());
        entity.setTotal(order.getTotal().amount());

        entity.setItems(new ArrayList<>());

        addItemEntitiesToOrder(entity, order);

        return entity;
    }

    private static void addItemEntitiesToOrder(OrderJpaEntity entity, Order order) {
        for (OrderItem item : order.getItems()) {
            entity.getItems().add(buildOrderItemEntity(item, entity));
        }
    }

    private static OrderItemJpaEntity buildOrderItemEntity(OrderItem item, OrderJpaEntity orderEntity) {
        OrderItemJpaEntity itemEntity = new OrderItemJpaEntity();

        itemEntity.setOrder(orderEntity);
        itemEntity.setId(UUID.randomUUID());
        itemEntity.setProductId(UUID.fromString(item.getProduct().productId()));
        itemEntity.setProductName(item.getProduct().productName());
        itemEntity.setUnitPrice(item.getProduct().unitPrice().amount());
        itemEntity.setQuantity(item.getQuantity().getValue());
        itemEntity.setSubtotal(item.getSubtotal().amount());

        return itemEntity;
    }

    private OrderMapper() {}
}
