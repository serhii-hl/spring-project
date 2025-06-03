package core.basesyntax.mapper;

import core.basesyntax.config.MapperConfig;
import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.model.ShoppingCart;
import core.basesyntax.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CategoryMapperHelper.class})
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    public ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Named("userFromId")
    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
