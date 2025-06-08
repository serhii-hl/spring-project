package core.basesyntax.dto.order;

import core.basesyntax.dto.orderitem.OrderItemDto;
import core.basesyntax.model.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private Set<OrderItemDto> items;
}
