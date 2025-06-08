package core.basesyntax.service.impl;

import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.dto.order.OrderDto;
import core.basesyntax.dto.order.UpdateOrderResponseDto;
import core.basesyntax.dto.orderitem.OrderItemDto;
import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.exception.OrderProcessingException;
import core.basesyntax.mapper.OrderItemMapper;
import core.basesyntax.mapper.OrderMapper;
import core.basesyntax.model.Book;
import core.basesyntax.model.Order;
import core.basesyntax.model.OrderItem;
import core.basesyntax.model.OrderStatus;
import core.basesyntax.model.User;
import core.basesyntax.repository.book.BookRepository;
import core.basesyntax.repository.order.OrderRepository;
import core.basesyntax.service.OrderService;
import core.basesyntax.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartService shoppingCartService;
    private final BookRepository bookRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDto createOrderFromCart(User user) {
        ShoppingCartDto shoppingCart = shoppingCartService.getCartByUser(user);
        Set<CartItemDto> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new OrderProcessingException("Shopping cart is empty");
        }
        Order order = initializeOrder(user);
        Set<OrderItem> orderItems = buildOrderItems(order, shoppingCart);
        BigDecimal totalPrice = calculateTotal(orderItems);
        order.setOrderItems(orderItems);
        order.setTotal(totalPrice);
        Order saved = orderRepository.save(order);
        shoppingCartService.clearCart(user);
        return orderMapper.toDto(saved);
    }

    @Override
    public Page<OrderDto> getOrdersByUser(User user, Pageable pageable) {
        return orderRepository.findAllOrdersByUser(user, pageable).map(orderMapper::toDto);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found with id " + orderId));
    }

    @Override
    public Page<OrderItemDto> getOrderItemsByOrderId(Long orderId, Pageable pageable, User user) {
        return orderRepository.findAllItemsByUserAndOrderId(user, orderId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long orderItemId, User user) {
        return orderItemMapper
                .toDto(orderRepository.findSecureItem(orderItemId, orderId, user));
    }

    @Override
    public void cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found with id " + orderId));
        if (order.getStatus() != OrderStatus.NEW) {
            throw new IllegalStateException("You can`t cancel order, it is status is: "
                    + order.getStatus());
        }
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Override
    public UpdateOrderResponseDto updateOrderStatus(Long orderId, User user, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found with id " + orderId));
        order.setStatus(status);
        orderRepository.save(order);
        UpdateOrderResponseDto responseDto = initializeUpdateOrderResponseDto(order);
        responseDto.setUpdatedStatus(status);
        return responseDto;
    }

    private UpdateOrderResponseDto initializeUpdateOrderResponseDto(Order order) {
        UpdateOrderResponseDto responseDto = new UpdateOrderResponseDto();
        responseDto.setOrderId(order.getId());
        responseDto.setPreviousStatus(order.getStatus());
        responseDto.setUpdatedAt(LocalDateTime.now());
        return responseDto;
    }

    private Order initializeOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(user.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        return order;
    }

    private Set<OrderItem> buildOrderItems(Order order, ShoppingCartDto cart) {
        Set<OrderItem> items = new HashSet<>();
        for (CartItemDto cartItem : cart.getCartItems()) {
            Book book = bookRepository.findById(cartItem.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Book not found with id " + cartItem.getBookId()));
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setBook(book);
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            items.add(item);
        }
        return items;
    }

    private BigDecimal calculateTotal(Set<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
