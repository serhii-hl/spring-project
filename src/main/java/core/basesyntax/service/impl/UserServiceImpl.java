package core.basesyntax.service.impl;

import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.dto.user.UserDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.mapper.UserMapper;
import core.basesyntax.model.Role;
import core.basesyntax.model.User;
import core.basesyntax.repository.role.RoleRepository;
import core.basesyntax.repository.user.UserRepository;
import core.basesyntax.service.ShoppingCartService;
import core.basesyntax.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserDto registerUser(CreateUserRequestDto createUserRequestDto) {
        if (userRepository.existsByEmail(createUserRequestDto.getEmail())) {
            throw new RegistrationException("User with such email already exist "
                    + createUserRequestDto.getEmail());
        }
        User user = userMapper.toUser(createUserRequestDto);
        Role userRole = roleRepository.findByRole(Role.RoleName.USER)
                .orElseThrow(() -> new RegistrationException("Role USER not found"));
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        shoppingCartService.createCartForUser(createUserRequestDto);
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with such email does not exist"));
    }
}
