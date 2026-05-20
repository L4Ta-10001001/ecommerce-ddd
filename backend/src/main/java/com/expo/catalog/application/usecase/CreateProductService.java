package com.expo.catalog.application.usecase;

import com.expo.catalog.application.command.CreateProductCommand;
import com.expo.catalog.application.dto.ProductDto;
import com.expo.catalog.application.port.in.CreateProductUseCase;
import com.expo.catalog.application.port.out.DomainEventPublisher;
import com.expo.catalog.domain.factory.ProductFactory;
import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductName;
import com.expo.catalog.domain.repository.ProductRepository;
import com.expo.catalog.domain.specification.ProductNameUniqueSpecification;

public class CreateProductService implements CreateProductUseCase {

    private final ProductFactory factory;
    private final ProductRepository repository;
    private final ProductNameUniqueSpecification specification;
    private final DomainEventPublisher eventPublisher;
    
    public CreateProductService(ProductFactory factory,
                                ProductRepository repository,
                                ProductNameUniqueSpecification specification,
                                DomainEventPublisher eventPublisher) {
        this.factory = factory;
        this.repository = repository;
        this.specification = specification;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ProductDto execute(CreateProductCommand command) {

        ProductName name = new ProductName(command.name());
        if (!specification.isSatisfiedBy(name)) {
            throw new IllegalArgumentException("A product with that name already exists.");
        }

        Product product = factory.create(
                command.name(),
                command.description(),
                command.price(),
                command.currency()
        );

        repository.save(product);

        product.pullDomainEvents().forEach(eventPublisher::publish);

        return ProductDto.from(product);
    }
}