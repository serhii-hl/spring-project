package core.basesyntax.validation;

import core.basesyntax.validation.impl.AuthorValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AuthorValidator.class)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Author {
    String message() default
            "Invalid name. Please use at least three letters and don`t use numbers/symbols";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
