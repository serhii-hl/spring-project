package core.basesyntax.service;

import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.dto.shoppingcart.UpdateCartItemQuantityDto;
import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {

    void delete(Long cartId);

    ShoppingCartDto updateCartItemQuantity(User user, UpdateCartItemQuantityDto dto);

    ShoppingCartDto updateCartItemQuantity(User user, Long cartItemId, int quantity);

    Page<ShoppingCartDto> getCart(Long cartId, Pageable pageable);

    ShoppingCartDto getCartByUser(User user);

    public void deleteCartItem(User user, Long cartItemId);

    public void createCartForUser(CreateUserRequestDto request);
}
