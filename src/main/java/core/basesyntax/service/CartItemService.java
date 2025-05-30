package core.basesyntax.service;

import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.dto.cartitem.CartItemResponceDto;

public interface CartItemService {
    CartItemResponceDto create(CartItemDto dto);

    void delete(Long cartItemId);

    CartItemDto updateQuantity(Long cartItemId, int quantity);

}
