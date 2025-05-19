package core.basesyntax.validation.impl;

import core.basesyntax.validation.Author;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AuthorValidator implements ConstraintValidator<Author, String> {
    private static final String PATTERN = "^[A-Za-z]{3,}$";

    @Override
    public boolean isValid(String author, ConstraintValidatorContext constraintValidatorContext) {
        return author != null && Pattern.compile(PATTERN).matcher(author).matches();
    }
}
