package core.basesyntax.dto;

import core.basesyntax.validation.Author;
import core.basesyntax.validation.Isbn;
import core.basesyntax.validation.Title;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotNull
    @Title
    private String title;
    @NotNull
    @Author
    private String author;
    @NotNull
    @Isbn
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @NotNull
    private String description;
    @NotNull
    private String coverImage;
}
