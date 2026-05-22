package com.expo.catalog.infrastructure.adapters.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expo.catalog.application.command.ChangeProductPriceCommand;
import com.expo.catalog.application.command.CreateProductCommand;
import com.expo.catalog.application.dto.ProductDto;
import com.expo.catalog.application.port.in.ActivateProductUseCase;
import com.expo.catalog.application.port.in.ChangeProductPriceUseCase;
import com.expo.catalog.application.port.in.CreateProductUseCase;
import com.expo.catalog.application.port.in.DeactivateProductUseCase;
import com.expo.catalog.application.port.in.GetProductByIdUseCase;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ActivateProductUseCase activateProductUseCase;
    private final DeactivateProductUseCase deactivateProductUseCase;
    private final ChangeProductPriceUseCase changeProductPriceUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;

    public ProductController(
            CreateProductUseCase createProductUseCase,
            ActivateProductUseCase activateProductUseCase,
            DeactivateProductUseCase deactivateProductUseCase,
            ChangeProductPriceUseCase changeProductPriceUseCase,
            GetProductByIdUseCase getProductByIdUseCase) {

        this.createProductUseCase = createProductUseCase;
        this.activateProductUseCase = activateProductUseCase;
        this.deactivateProductUseCase = deactivateProductUseCase;
        this.changeProductPriceUseCase = changeProductPriceUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
    }

    @PostMapping
    public ProductDto create(@RequestBody CreateProductCommand command) {
        return createProductUseCase.execute(command);
    }
    

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable String id) {
        return getProductByIdUseCase.getById(id);
    }

    @PostMapping("/{id}/activate")
    public void activate(@PathVariable String id) {
        activateProductUseCase.activate(id);
    }

    @PostMapping("/{id}/deactivate")
    public void deactivate(@PathVariable String id) {
        deactivateProductUseCase.deactivate(id);
    }

    @PutMapping("/{id}/price")
    public void changePrice(
            @PathVariable String id,
            @RequestBody ChangeProductPriceCommand command
    ) {
        ChangeProductPriceCommand finalCommand =
                new ChangeProductPriceCommand(
                        id,
                        command.amount(),
                        command.currency()
                );

        changeProductPriceUseCase.changePrice(finalCommand);
    }
}
