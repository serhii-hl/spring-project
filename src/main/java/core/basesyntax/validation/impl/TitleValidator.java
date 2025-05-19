package core.basesyntax.validation.impl;

import core.basesyntax.validation.Title;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TitleValidator implements ConstraintValidator<Title, String> {
    private static final String PATTERN = "^[A-Za-z0-9]{3,}$";

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        return title != null && Pattern.compile(PATTERN).matcher(title).matches();
    }
}
