package core.basesyntax.dto.category;

import jakarta.validation.constraints.NotBlank;

public class CreateCategoryRequestDto {
    @NotBlank
    private String name;
    private String description;
}
