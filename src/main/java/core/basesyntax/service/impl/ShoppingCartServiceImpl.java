package core.basesyntax.service.impl;

import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.CartItemMapper;
import core.basesyntax.mapper.ShoppingCartMapper;
import core.basesyntax.model.CartItem;
import core.basesyntax.model.ShoppingCart;
import core.basesyntax.model.User;
import core.basesyntax.repository.cartitem.CartItemRepository;
import core.basesyntax.repository.shoppingcart.ShoppingCartRepository;
import core.basesyntax.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository repository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto addCartItem(User user, CartItemDto cartItemDto) {
        ShoppingCart cart = repository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ShoppingCart not found for user: " + user.getUsername()));
        CartItem cartItem = cartItemMapper.toEntity(cartItemDto);
        cartItem.setShoppingCart(cart);
        cart.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(repository.save(cart));
    }

    @Override
    public ShoppingCartDto updateCartItemQuantity(User user, Long cartItemId, int quantity) {
        ShoppingCart cart = repository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ShoppingCart not found for user: " + user.getUsername()));

        cartItemRepository.findByIdAndShoppingCartId(cartItemId, cart.getId())
                            .map(item -> {
                                item.setQuantity(quantity);
                                return item;
                            })
                            .orElseThrow(() -> new EntityNotFoundException(
                                "CartItem with id " + cartItemId + " not found in cart"));
        ShoppingCart updatedCart = repository.save(cart);
        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto getCartByUser(User user) {
        return shoppingCartMapper.toDto(
                repository.findByUser(user).orElseThrow(
                        () -> new EntityNotFoundException("Can`t find user: "
                                + user.getUsername()))
        );
    }

    @Override
    public void createCartForUser(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        repository.save(cart);
    }

    @Override
    public void clearCart(User user) {
        ShoppingCart cart = repository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart not found for user: " + user.getUsername()));
        cart.clearCart();
        repository.save(cart);
    }

    @Override
    public void deleteCartItem(User user, Long cartItemId) {
        ShoppingCart cart = repository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart not found for user: " + user.getUsername()));

        cartItemRepository.deleteByIdAndShoppingCartId(cartItemId, cart.getId());
    }

}
