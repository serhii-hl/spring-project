package core.basesyntax.validation.orderstatus.impl;

import core.basesyntax.model.OrderStatus;
import core.basesyntax.validation.orderstatus.OrderStatusValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrderStatusValidator implements ConstraintValidator<OrderStatusValid, String> {

    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintValidatorContext) {
        if (status == null) {
            return false;
        }
        try {
            OrderStatus.valueOf(status);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
