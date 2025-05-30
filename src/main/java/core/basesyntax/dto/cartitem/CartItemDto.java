package core.basesyntax.dto.cartitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long id;
    private Long bookId;
    private String title;
    private int quantity;
}
