package com.expo.ddd.api;

import com.expo.ddd.api.dto.OrderResponse;
import com.expo.ddd.api.dto.PlaceOrderRequest;
import com.expo.ddd.application.command.OrderItemCommand;
import com.expo.ddd.application.command.PlaceOrderCommand;
import com.expo.ddd.application.usecase.CancelOrderUseCase;
import com.expo.ddd.application.usecase.PlaceOrderUseCase;
import com.expo.ddd.domain.exception.OrderNotFoundException;
import com.expo.ddd.domain.model.order.Order;
import com.expo.ddd.domain.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ✅ Adaptador de entrada HTTP. Traduce peticiones HTTP a comandos del dominio.
 *    El controlador NO contiene lógica de negocio — solo orquestación de la interfaz HTTP.
 * ⚠️ En Proyecto A, el controlador llama directamente a servicios que mezclan
 *    orquestación con reglas de negocio.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final OrderRepository orderRepository;

    public OrderController(PlaceOrderUseCase placeOrderUseCase,
                           CancelOrderUseCase cancelOrderUseCase,
                           OrderRepository orderRepository) {
        this.placeOrderUseCase = placeOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody PlaceOrderRequest request) {
        PlaceOrderCommand command = toCommand(request);
        Order order = placeOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        Order order = cancelOrderUseCase.execute(id);
        return ResponseEntity.ok(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return ResponseEntity.ok(OrderResponse.from(order));
    }

    private PlaceOrderCommand toCommand(PlaceOrderRequest request) {
        List<OrderItemCommand> itemCommands = request.items().stream()
                .map(item -> new OrderItemCommand(item.productId(), item.quantity()))
                .toList();
        return new PlaceOrderCommand(request.customerId(), itemCommands);
    }
}
