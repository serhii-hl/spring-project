package core.basesyntax.service;

import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.dto.user.UserDto;

public interface UserService {
    UserDto registerUser(CreateUserRequestDto createUserRequestDto);
}
