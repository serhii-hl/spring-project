package core.basesyntax.service;

import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.model.User;

public interface ShoppingCartService {

    ShoppingCartDto addCartItem(User user, CartItemDto cartItemDto);

    ShoppingCartDto updateCartItemQuantity(User user, Long cartItemId, int quantity);

    ShoppingCartDto getCartByUser(User user);

    void deleteCartItem(User user, Long cartItemId);

    void createCartForUser(User user);

    void clearCart(User user);

}
