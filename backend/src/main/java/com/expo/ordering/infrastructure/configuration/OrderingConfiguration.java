package com.expo.ordering.infrastructure.configuration;

import com.expo.contracts.catalog.CatalogContract;
import com.expo.ordering.application.port.in.CancelOrderUseCase;
import com.expo.ordering.application.port.in.GetOrderByIdUseCase;
import com.expo.ordering.application.port.in.PlaceOrderUseCase;
import com.expo.ordering.application.port.out.DomainEventPublisher;
import com.expo.ordering.application.usecase.CancelOrderService;
import com.expo.ordering.application.usecase.GetOrderByIdService;
import com.expo.ordering.application.usecase.PlaceOrderService;
import com.expo.ordering.domain.factory.OrderFactory;
import com.expo.ordering.domain.repository.OrderRepository;
import com.expo.ordering.infrastructure.adapters.out.event.SpringOrderDomainEventPublisher;
import com.expo.ordering.infrastructure.adapters.out.persistence.adapter.OrderRepositoryAdapter;
import com.expo.ordering.infrastructure.adapters.out.persistence.jpa.SpringDataOrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderingConfiguration {

    @Bean
    public OrderFactory orderFactory() {
        return new OrderFactory();
    }

    @Bean
    public OrderRepository orderRepository(SpringDataOrderRepository springDataOrderRepository) {
        return new OrderRepositoryAdapter(springDataOrderRepository);
    }

    @Bean
    public DomainEventPublisher orderingDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new SpringOrderDomainEventPublisher(applicationEventPublisher);
    }

    @Bean
    public PlaceOrderUseCase placeOrderUseCase(
            CatalogContract catalogContract,
            OrderRepository orderRepository,
            DomainEventPublisher domainEventPublisher,
            OrderFactory orderFactory
    ) {
        return new PlaceOrderService(catalogContract, orderRepository, domainEventPublisher, orderFactory);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(
            OrderRepository orderRepository,
            DomainEventPublisher domainEventPublisher
    ) {
        return new CancelOrderService(orderRepository, domainEventPublisher);
    }

    @Bean
    public GetOrderByIdUseCase getOrderByIdUseCase(OrderRepository orderRepository) {
        return new GetOrderByIdService(orderRepository);
    }
}
