package core.basesyntax.service.impl;

import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.dto.shoppingcart.UpdateCartItemQuantityDto;
import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.ShoppingCartMapper;
import core.basesyntax.mapper.UserMapper;
import core.basesyntax.model.CartItem;
import core.basesyntax.model.ShoppingCart;
import core.basesyntax.model.User;
import core.basesyntax.repository.cartitem.CartItemRepository;
import core.basesyntax.repository.shoppingcart.ShoppingCartRepository;
import core.basesyntax.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
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
    private final UserMapper userMapper;
    private final CartItemRepository cartItemRepository;

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

        Optional<CartItem> cartItem = cartItemRepository.findByShoppingCartAndBook_Id(cart, bookId);

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

        Optional<CartItem> cartItem = cartItemRepository
                .findByShoppingCartAndBook_Id(cart, cartItemId);
        if (cartItem.isPresent()) {
            cartItem.get().setQuantity(quantity);
        } else {
            throw new EntityNotFoundException("CartItem with bookId "
                    + cartItemId + " not found in cart");
        }
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
    @Transactional
    public void createCartForUser(CreateUserRequestDto request) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(userMapper.toUser(request));
        cart.setCartItems(new HashSet<>());
        repository.save(cart);
    }

    @Override
    public void deleteCartItem(User user, Long cartItemId) {
        ShoppingCart cart = repository.findByUser(user)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart not found for user: "
                                + user.getUsername()));

        cartItemRepository.deleteByIdAndShoppingCartId(cartItemId, cart.getId());
    }
}
