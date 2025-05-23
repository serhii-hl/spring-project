package core.basesyntax.dto.user;

import core.basesyntax.validation.general.Name;
import core.basesyntax.validation.user.FieldMatch;
import core.basesyntax.validation.user.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Password
    private String password;
    @NotBlank
    @FieldMatch
    private String repeatPassword;
    @NotBlank
    @Name
    private String firstName;
    @NotBlank
    @Name
    private String lastName;
    private String shippingAddress;
}
