package core.basesyntax.repository.shoppingcart;

import core.basesyntax.model.ShoppingCart;
import core.basesyntax.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findById(Long categoryId);

    Optional<ShoppingCart> findByUser(User user);

}
