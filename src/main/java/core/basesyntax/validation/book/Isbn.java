package core.basesyntax.validation.book;

import core.basesyntax.validation.book.impl.IsbnValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsbnValidator.class)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Isbn {
    String message() default "Invalid ISBN. Please use format ISBN_13";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
