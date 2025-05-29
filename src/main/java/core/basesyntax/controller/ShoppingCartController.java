package core.basesyntax.controller;

import core.basesyntax.dto.shoppingcart.ShoppingCartDto;
import core.basesyntax.dto.shoppingcart.UpdateCartItemQuantityDto;
import core.basesyntax.model.User;
import core.basesyntax.service.ShoppingCartService;
import core.basesyntax.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Сart controller",
        description = "Endpoints for cart management ( CRUD operations )")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get cart", description = "Get cart")
    public ShoppingCartDto getCart(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return shoppingCartService.getCartByUser(user);
    }

    @PostMapping
    @Operation(summary = "Create a cart", description = "Add book to the shopping cart")
    public ShoppingCartDto createCart(@RequestBody @Valid
                                          UpdateCartItemQuantityDto updateCartItemQuantityDto,
                                      Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return shoppingCartService.updateCartItemQuantity(user, updateCartItemQuantityDto);
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update a cart", description = "Update cart item quantity")
    public ShoppingCartDto updateCart(@RequestParam int quantity, @PathVariable Long cartItemId,
                                      Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return shoppingCartService.updateCartItemQuantity(user, cartItemId, quantity);
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Delete cart item", description = "Remove an item from the shopping cart")
    public ShoppingCartDto deleteCart(@PathVariable Long cartItemId,
                                      Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return shoppingCartService.deleteCartItem(user, cartItemId);
    }
}
