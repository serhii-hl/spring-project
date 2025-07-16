package core.basesyntax.mapper;

import core.basesyntax.config.MapperConfig;
import core.basesyntax.dto.order.OrderDto;
import core.basesyntax.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "items")
    OrderDto toDto(Order order);
}
