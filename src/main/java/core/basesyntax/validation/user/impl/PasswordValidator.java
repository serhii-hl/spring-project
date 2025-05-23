package core.basesyntax.validation.user.impl;

import core.basesyntax.validation.user.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final String PATTERN = "^(?=.*[^a-zA-Z0-9]).{8,}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && Pattern.compile(PATTERN).matcher(password).matches();
    }
}
