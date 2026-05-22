package com.expo.ordering.infrastructure.adapters.in;

import com.expo.ordering.application.command.OrderItemCommand;
import com.expo.ordering.application.command.PlaceOrderCommand;
import com.expo.ordering.application.port.in.CancelOrderUseCase;
import com.expo.ordering.application.port.in.GetOrderByIdUseCase;
import com.expo.ordering.application.port.in.PlaceOrderUseCase;
import com.expo.ordering.domain.model.order.Order;
import com.expo.ordering.domain.valueobject.CustomerId;
import com.expo.ordering.domain.valueobject.CustomerType;
import com.expo.ordering.infrastructure.adapters.in.api.request.OrderItemRequest;
import com.expo.ordering.infrastructure.adapters.in.api.request.PlaceOrderRequest;
import com.expo.ordering.infrastructure.adapters.in.api.response.OrderResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;

    public OrderController(
            PlaceOrderUseCase placeOrderUseCase,
            CancelOrderUseCase cancelOrderUseCase,
            GetOrderByIdUseCase getOrderByIdUseCase
    ) {
        this.placeOrderUseCase = placeOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
    }

    @PostMapping
    public OrderResponse placeOrder(@RequestBody PlaceOrderRequest request) {
        PlaceOrderCommand command = toCommand(request);
        Order order = placeOrderUseCase.execute(command);
        return OrderResponse.from(order);
    }

    @DeleteMapping("/{orderId}")
    public OrderResponse cancelOrder(@PathVariable UUID orderId) {
        Order order = cancelOrderUseCase.execute(orderId);
        return OrderResponse.from(order);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable UUID orderId) {
        return OrderResponse.from(getOrderByIdUseCase.execute(orderId));
    }

    private PlaceOrderCommand toCommand(PlaceOrderRequest request) {
        List<OrderItemCommand> items = request.items().stream()
                .map(this::toItemCommand)
                .toList();

        return new PlaceOrderCommand(
                new CustomerId(request.customerId()),
                resolveCustomerType(request.customerType()),
                items
        );
    }

    private OrderItemCommand toItemCommand(OrderItemRequest item) {
        return new OrderItemCommand(item.productId(), item.quantity());
    }

    private CustomerType resolveCustomerType(String customerType) {
        if (customerType == null || customerType.isBlank()) {
            return CustomerType.REGULAR;
        }

        return CustomerType.valueOf(customerType.trim().toUpperCase(Locale.ROOT));
    }
}
