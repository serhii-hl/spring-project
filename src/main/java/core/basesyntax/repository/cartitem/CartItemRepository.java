package core.basesyntax.repository.cartitem;

import core.basesyntax.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long itemId, Long bookId);

    void deleteByIdAndShoppingCartId(Long cartItemId, Long cartId);
}
