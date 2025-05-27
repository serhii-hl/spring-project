package core.basesyntax.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
