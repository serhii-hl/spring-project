package core.basesyntax.dto.shoppingcart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemQuantityDto {
    @NotBlank
    private Long bookId;
    @Positive
    private int quantity;
}
