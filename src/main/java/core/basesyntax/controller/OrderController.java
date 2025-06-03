package core.basesyntax.controller;

import core.basesyntax.dto.order.OrderDto;
import core.basesyntax.dto.order.UpdateOrderStatusRequest;
import core.basesyntax.model.User;
import core.basesyntax.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order controller",
        description = "Endpoints for cart management ( CRUD operations )")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get order history", description = "Get order history")
    public Page<OrderDto> getOrders(@AuthenticationPrincipal User user, Pageable pageable) {
        return orderService.getOrdersByUser(user, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place an order", description = "Creates an order from shopping cart")
    public OrderDto createOrder(@AuthenticationPrincipal User user) {
        return orderService.createOrderFromCart(user);
    }

    @GetMapping("/{orderId}/items/{id}")
    @Operation(summary = "Get order history", description = "Get order history")
    public OrderDto getOrder(@PathVariable Long orderId, @PathVariable Long id,
                             @AuthenticationPrincipal User user) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Get order history", description = "Get order history")
    public void deleteOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        orderService.cancelOrder(orderId, user);
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get order history", description = "Get order history")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateOrderStatus(@AuthenticationPrincipal User user,
                                  @PathVariable Long orderId,
                                  @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, user, request.getStatus());
    }

}
