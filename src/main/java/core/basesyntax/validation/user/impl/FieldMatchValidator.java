package core.basesyntax.validation.user.impl;

import core.basesyntax.dto.user.CreateUserRequestDto;
import core.basesyntax.validation.user.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, CreateUserRequestDto> {
    @Override
    public boolean isValid(CreateUserRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getRepeatPassword() == null) {
            return false;
        }
        return dto.getPassword().equals(dto.getRepeatPassword());
    }
}
