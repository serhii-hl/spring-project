package core.basesyntax.validation.general.impl;

import core.basesyntax.validation.general.Name;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name, String> {
    private static final String NAME_PATTERN = "^[A-Za-z]{3,}$";

    @Override
    public boolean isValid(String author, ConstraintValidatorContext constraintValidatorContext) {
        return author != null && Pattern.compile(NAME_PATTERN).matcher(author).matches();
    }
}
