package com.expo.catalog.application.usecase;

import com.expo.catalog.application.command.ChangeProductPriceCommand;
import com.expo.catalog.application.port.in.ChangeProductPriceUseCase;
import com.expo.catalog.application.port.out.DomainEventPublisher;
import com.expo.catalog.domain.exception.ProductNotFoundException;
import com.expo.catalog.domain.model.product.Product;
import com.expo.catalog.domain.model.product.ProductId;
import com.expo.catalog.domain.repository.ProductRepository;
import com.expo.shared.domain.Currency;
import com.expo.shared.domain.Money;

public class ChangeProductPriceService implements ChangeProductPriceUseCase {

    private final ProductRepository productRepository;
    private final DomainEventPublisher eventPublisher;

    public ChangeProductPriceService(ProductRepository productRepository, DomainEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void changePrice(ChangeProductPriceCommand command) {
        Product product = productRepository.findById(ProductId.from(command.productId()))
                .orElseThrow(() -> new ProductNotFoundException(command.productId()));

        Money newPrice = new Money(command.amount(), new Currency(command.currency()));

        product.changePrice(newPrice);
        productRepository.save(product);

        product.pullDomainEvents().forEach(eventPublisher::publish);
    }
}
