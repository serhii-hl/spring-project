package core.basesyntax.service;

import core.basesyntax.dto.order.OrderDto;
import core.basesyntax.model.OrderStatus;
import core.basesyntax.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrderFromCart(User user);

    Page<OrderDto> getOrdersByUser(User user, Pageable pageable);

    OrderDto getOrderById(Long orderId);

    void cancelOrder(Long orderId, User user);

    void updateOrderStatus(Long orderId, User user, OrderStatus status);
}
