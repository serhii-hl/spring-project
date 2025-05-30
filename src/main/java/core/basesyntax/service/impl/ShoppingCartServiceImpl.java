package core.basesyntax.service.impl;

import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.dto.cartitem.CartItemResponseDto;
import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.CartItemMapper;
import core.basesyntax.mapper.ShoppingCartMapper;
import core.basesyntax.mapper.UserMapper;
import core.basesyntax.model.CartItem;
import core.basesyntax.model.ShoppingCart;
import core.basesyntax.model.User;
import core.basesyntax.repository.cartitem.CartItemRepository;
import core.basesyntax.repository.shoppingcart.ShoppingCartRepository;
import core.basesyntax.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository repository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserMapper userMapper;
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
        ShoppingCart cart = repository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ShoppingCart not found for user: " + user.getUsername()));

        Optional<CartItem> cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, cart.getId());

        if (cartItem.isPresent()) {
            cartItem.get().setQuantity(quantity);
        } else {
            throw new EntityNotFoundException("CartItem with id "
                    + cartItemId + " not found in cart");
        }

        ShoppingCart updatedCart = repository.save(cart);
        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto getCart(Long cartId) {
        return shoppingCartMapper.toDto(repository.findById(cartId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find entity by id: " + cartId)));
    }

    @Override
    public ShoppingCartDto getCartByUser(User user) {
        return shoppingCartMapper.toDto(
                repository.findById(user.getId()).orElseThrow(
                        () -> new EntityNotFoundException("Can`t find user: "
                                + user.getUsername()))
        );
    }

    @Override
    public void createCartForUser(CreateUserRequestDto request) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(userMapper.toUser(request));
        repository.save(cart);
    }

    @Override
    public void deleteCartItem(User user, Long cartItemId) {
        ShoppingCart cart = repository.findById(user.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart not found for user: "
                                + user.getUsername()));

        cartItemRepository.deleteByIdAndShoppingCartId(cartItemId, cart.getId());
    }

    @Override
    public CartItemResponseDto createCartItem(CartItemDto dto) {
        cartItemRepository.save(cartItemMapper.toEntity(dto));
        return cartItemMapper.toResponceDto(dto);
    }
}
