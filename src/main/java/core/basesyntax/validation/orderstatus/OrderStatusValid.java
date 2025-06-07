package core.basesyntax.validation.orderstatus;

import core.basesyntax.validation.general.impl.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderStatusValid {
    String message() default
            "Invalid status. Please use one from the list: NEW, PROCESSING, SHIPPED"
                    + "DELIVERED, CANCELED";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
