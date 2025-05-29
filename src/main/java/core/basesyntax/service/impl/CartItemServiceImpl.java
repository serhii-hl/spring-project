package core.basesyntax.service.impl;

import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.CartItemMapper;
import core.basesyntax.model.CartItem;
import core.basesyntax.repository.cartitem.CartItemRepository;
import core.basesyntax.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository repository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto create(CartItemDto dto) {
        repository.save(cartItemMapper.toEntity(dto));
        return dto;
    }

    @Override
    public void delete(Long cartItemId) {
        repository.deleteById(cartItemId);
    }

    @Override
    public CartItemDto updateQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = repository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find entity by id: " + cartItemId));
        cartItem.setQuantity(quantity);
        repository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }
}
