package core.basesyntax.dto;

import core.basesyntax.validation.Author;
import core.basesyntax.validation.Isbn;
import core.basesyntax.validation.Title;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotBlank
    @Title
    private String title;
    @NotBlank
    @Author
    private String author;
    @NotBlank
    @Isbn
    private String isbn;
    @NotNull
    @Positive
    private BigDecimal price;
    private String description;
    private String coverImage;
}
