package core.basesyntax.validation.impl;

import core.basesyntax.validation.Isbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    private static final String PATTERN_OF_ISBN_13 = "^97[89]-[0-9]{9}[0-9]$";

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        return isbn != null && Pattern.compile(PATTERN_OF_ISBN_13).matcher(isbn).matches();
    }
}
