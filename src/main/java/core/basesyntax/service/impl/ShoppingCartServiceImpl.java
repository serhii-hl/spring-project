package core.basesyntax.service.impl;

import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.dto.shoppingcart.UpdateCartItemQuantityDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.ShoppingCartMapper;
import core.basesyntax.model.CartItem;
import core.basesyntax.model.ShoppingCart;
import core.basesyntax.model.User;
import core.basesyntax.repository.shoppingcart.ShoppingCartRepository;
import core.basesyntax.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository repository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public void delete(Long cartId) {
        repository.deleteById(cartId);
    }

    @Override
    public ShoppingCartDto updateCartItemQuantity(User user, UpdateCartItemQuantityDto request) {
        Long bookId = request.getBookId();
        int quantity = request.getQuantity();

        ShoppingCart cart = repository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ShoppingCart not found for user: " + user.getUsername()));

        Optional<CartItem> cartItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst();

        if (cartItem.isPresent()) {
            cartItem.get().setQuantity(quantity);
        } else {
            throw new EntityNotFoundException("CartItem with bookId "
                    + bookId + " not found in cart");
        }

        ShoppingCart updatedCart = repository.save(cart);
        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto updateCartItemQuantity(User user, Long cartItemId, int quantity) {
        ShoppingCart cart = repository.findByUser(user)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart not found for user: "
                                + user.getUsername()));

        CartItem item = cart.getCartItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart item not found: " + cartItemId));

        item.setQuantity(quantity);
        ShoppingCart updated = repository.save(cart);
        return shoppingCartMapper.toDto(updated);
    }

    @Override
    public Page<ShoppingCartDto> getCart(Long cartId, Pageable pageable) {
        return repository.findById(cartId, pageable);
    }

    @Override
    public ShoppingCartDto getCartByUser(User user) {
        return shoppingCartMapper.toDto(
                repository.findByUser(user).orElseThrow(
                        () -> new EntityNotFoundException("Can`t find user"))
        );
    }

    @Override
    public ShoppingCartDto deleteCartItem(User user, Long cartItemId) {
        ShoppingCart cart = repository.findByUser(user)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart not found for user: "
                                + user.getUsername()));

        boolean removed = cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));

        if (!removed) {
            throw new EntityNotFoundException("CartItem not found with id: " + cartItemId);
        }

        ShoppingCart updated = repository.save(cart);
        return shoppingCartMapper.toDto(updated);
    }
}
