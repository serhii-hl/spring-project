package core.basesyntax.controller;

import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.dto.user.UserDto;
import core.basesyntax.dto.user.UserLoginRequestDto;
import core.basesyntax.dto.user.UserLoginResponseDto;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.security.AuthenticationService;
import core.basesyntax.service.ShoppingCartService;
import core.basesyntax.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User controller", description = "Endpoints for user management ( CRUD operations )")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final ShoppingCartService shoppingCartService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register user", description = "Register a new user")
    public UserDto register(@RequestBody @Valid CreateUserRequestDto request)
            throws RegistrationException {
        shoppingCartService.createCartForUser(request);
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Login user")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
