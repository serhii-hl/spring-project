package core.basesyntax.service.impl;

import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.dto.user.UserDto;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.mapper.UserMapper;
import core.basesyntax.model.User;
import core.basesyntax.repository.user.UserRepository;
import core.basesyntax.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto registerUser(CreateUserRequestDto createUserRequestDto)
            throws RegistrationException {
        User user = userMapper.toUser(createUserRequestDto);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return userMapper.toUserDto(userRepository.save(user));
        }
        throw new RegistrationException("User with such email already exist ");
    }
}
