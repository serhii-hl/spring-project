package core.basesyntax.dto.order;

import core.basesyntax.model.OrderStatus;
import core.basesyntax.validation.orderstatus.OrderStatusValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {
    @NotBlank
    @OrderStatusValid
    private OrderStatus status;
}
