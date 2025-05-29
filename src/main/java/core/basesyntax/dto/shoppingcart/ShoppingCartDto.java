package core.basesyntax.dto.shoppingcart;

import core.basesyntax.model.CartItem;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
