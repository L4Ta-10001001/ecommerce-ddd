package com.expo.catalog.infrastructure.configuration;

import com.expo.catalog.application.port.in.ActivateProductUseCase;
import com.expo.catalog.application.port.in.ChangeProductPriceUseCase;
import com.expo.catalog.application.port.in.CreateProductUseCase;
import com.expo.catalog.application.port.in.DeactivateProductUseCase;
import com.expo.catalog.application.port.in.GetProductByIdUseCase;
import com.expo.catalog.application.port.in.ReleaseStockUseCase;
import com.expo.catalog.application.port.in.ReplenishStockUseCase;
import com.expo.catalog.application.port.in.ReserveStockUseCase;
import com.expo.catalog.application.port.out.DomainEventPublisher;
import com.expo.catalog.application.usecase.ActivateProductService;
import com.expo.catalog.application.usecase.ChangeProductPriceService;
import com.expo.catalog.application.usecase.CreateProductService;
import com.expo.catalog.application.usecase.DeactivateProductService;
import com.expo.catalog.application.usecase.GetProductByIdService;
import com.expo.catalog.application.usecase.ReleaseStockService;
import com.expo.catalog.application.usecase.ReplenishStockService;
import com.expo.catalog.application.usecase.ReserveStockService;
import com.expo.catalog.domain.factory.ProductFactory;
import com.expo.catalog.domain.repository.ProductRepository;
import com.expo.catalog.domain.specification.ProductNameUniqueSpecification;
import com.expo.catalog.infrastructure.adapters.out.event.SpringDomainEventPublisher;
import com.expo.catalog.infrastructure.adapters.out.persistence.adapter.ProductRepositoryAdapter;
import com.expo.catalog.infrastructure.adapters.out.persistence.jpa.SpringDataProductRepository;
import com.expo.catalog.infrastructure.mapper.ProductMapper;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfiguration {

    @Bean
    public CreateProductUseCase createProductUseCase(
            ProductFactory factory,
            ProductRepository productRepository,
            ProductNameUniqueSpecification specification,
            DomainEventPublisher eventPublisher
        ) {
        return new CreateProductService(factory, productRepository, specification, eventPublisher);
    }

    @Bean
    public ActivateProductUseCase activateProductUseCase(
            ProductRepository productRepository,
            DomainEventPublisher eventPublisher) {
        return new ActivateProductService(productRepository, eventPublisher);
    }

    @Bean
    public DeactivateProductUseCase deactivateProductUseCase(
            ProductRepository productRepository,
            DomainEventPublisher eventPublisher) {
        return new DeactivateProductService(productRepository, eventPublisher);
    }

    @Bean
    public ChangeProductPriceUseCase changeProductPriceUseCase(
            ProductRepository productRepository,
            DomainEventPublisher eventPublisher) {
        return new ChangeProductPriceService(productRepository, eventPublisher);
    }

    @Bean
    public GetProductByIdUseCase getProductByIdUseCase(
            ProductRepository productRepository) {
        return new GetProductByIdService(productRepository);
    }

    @Bean
    public ProductNameUniqueSpecification productNameUniqueSpecification(
            ProductRepository productRepository
    ) {
        return new ProductNameUniqueSpecification(productRepository);
    }

    @Bean
    public DomainEventPublisher domainEventPublisher(
            ApplicationEventPublisher applicationEventPublisher
    ) {
        return new SpringDomainEventPublisher(applicationEventPublisher);
    }

    @Bean
    public ProductFactory productFactory() {
        return new ProductFactory();
    }

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }

    @Bean
    public ProductRepository productRepository(
            SpringDataProductRepository springDataProductRepository,
            ProductMapper productMapper
    ) {
        return new ProductRepositoryAdapter(springDataProductRepository, productMapper);
    }

    @Bean
    public ReserveStockUseCase reserveStockUseCase(
            ProductRepository productRepository,
            DomainEventPublisher eventPublisher
    ) {
        return new ReserveStockService(productRepository, eventPublisher);
    }

    @Bean
    public ReplenishStockUseCase replenishStockUseCase(
            ProductRepository productRepository,
            DomainEventPublisher eventPublisher
    ) {
        return new ReplenishStockService(productRepository, eventPublisher);
    }

    @Bean
    public ReleaseStockUseCase releaseStockUseCase(
            ProductRepository productRepository,
            DomainEventPublisher eventPublisher
    ) {
        return new ReleaseStockService(productRepository, eventPublisher);
    }
}
