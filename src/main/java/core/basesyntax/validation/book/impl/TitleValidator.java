package core.basesyntax.validation.book.impl;

import core.basesyntax.validation.book.Title;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TitleValidator implements ConstraintValidator<Title, String> {
    private static final String TITLE_PATTERN = "^[A-Za-z0-9]{3,}$";

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        return title != null && Pattern.compile(TITLE_PATTERN).matcher(title).matches();
    }
}
