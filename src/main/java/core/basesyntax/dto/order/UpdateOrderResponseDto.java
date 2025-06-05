package core.basesyntax.dto.order;

import core.basesyntax.model.OrderStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderResponseDto {
    private Long orderId;
    private OrderStatus previousStatus;
    private OrderStatus updatedStatus;
    private LocalDateTime updatedAt;
}
