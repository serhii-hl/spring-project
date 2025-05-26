package core.basesyntax.controller;

import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.dto.user.UserDto;
import core.basesyntax.exception.RegistrationException;
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

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register user", description = "Register a new user")
    public UserDto register(@RequestBody @Valid CreateUserRequestDto request)
            throws RegistrationException {
        return userService.registerUser(request);
    }
}
