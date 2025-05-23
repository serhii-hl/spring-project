package core.basesyntax.validation.book;

import core.basesyntax.validation.book.impl.TitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TitleValidator.class)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Title {
    String message() default "Invalid title. Please use at least three letters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
