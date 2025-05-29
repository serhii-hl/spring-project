package core.basesyntax.repository.shoppingcart;

import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.model.ShoppingCart;
import core.basesyntax.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Page<ShoppingCartDto> findById(Long categoryId, Pageable pageable);

    Optional<ShoppingCart> findByUser(User user);
}
