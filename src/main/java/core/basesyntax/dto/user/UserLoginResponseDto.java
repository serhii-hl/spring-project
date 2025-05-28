package core.basesyntax.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDto {
    private String jwtToken;
}
