package core.basesyntax.repository.cartitem;

import core.basesyntax.model.CartItem;
import core.basesyntax.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByShoppingCartAndBook_Id(ShoppingCart shoppingCart, Long bookId);

    void deleteByIdAndShoppingCartId(Long cartItemId, Long cartId);
}
