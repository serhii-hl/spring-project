package core.basesyntax.service;

import core.basesyntax.dto.cartitem.CartItemDto;

public interface CartItemService {
    CartItemDto create(CartItemDto dto);

    void delete(Long cartItemId);

    CartItemDto updateQuantity(Long cartItemId, int quantity);

}
