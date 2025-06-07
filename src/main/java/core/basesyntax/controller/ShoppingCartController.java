package core.basesyntax.controller;

import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.dto.shoppingcart.UpdateCartItemQuantityDto;
import core.basesyntax.model.User;
import core.basesyntax.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Сart controller",
        description = "Endpoints for cart management ( CRUD operations )")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Get cart", description = "Get cart")
    public ShoppingCartDto getCart(@AuthenticationPrincipal User user) {
        return shoppingCartService.getCartByUser(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add cart item to the cart", description = "Add cart item to the cart")
    public ShoppingCartDto createCart(@RequestBody @Valid
                                          CartItemDto cartItemDto,
                                      @AuthenticationPrincipal User user) {
        return shoppingCartService.addCartItem(user, cartItemDto);
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update cart item in the cart",
            description = "Update cart item in the cart")
    public ShoppingCartDto updateCart(@RequestBody @Valid UpdateCartItemQuantityDto dto,
                                      @PathVariable Long cartItemId,
                                      @AuthenticationPrincipal User user) {
        return shoppingCartService.updateCartItemQuantity(user, cartItemId, dto.getQuantity());
    }

    @DeleteMapping("/items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete cart item", description = "Remove an item from the shopping cart")
    public void deleteCartItem(@PathVariable Long cartItemId,
                           @AuthenticationPrincipal User user) {
        shoppingCartService.deleteCartItem(user, cartItemId);
    }

    @DeleteMapping("/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete cart item", description = "Remove an item from the shopping cart")
    public void deleteAllCartItems(@AuthenticationPrincipal User user) {
        shoppingCartService.clearCart(user);
    }
}
