package core.basesyntax.service;

import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.dto.cartitem.CartItemResponseDto;
import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.model.User;

public interface ShoppingCartService {

    ShoppingCartDto addCartItem(User user, CartItemDto cartItemDto);

    ShoppingCartDto updateCartItemQuantity(User user, Long cartItemId, int quantity);

    ShoppingCartDto getCartByUser(User user);

    public ShoppingCartDto getCart(Long cartId);

    void deleteCartItem(User user, Long cartItemId);

    void createCartForUser(CreateUserRequestDto request);

    void clearCart(User user);

    CartItemResponseDto createCartItem(CartItemDto dto);

}
