package core.basesyntax.dto.book;

import core.basesyntax.validation.book.Isbn;
import core.basesyntax.validation.book.Title;
import core.basesyntax.validation.general.Name;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotBlank
    @Title
    private String title;
    @NotBlank
    @Name
    private String author;
    @NotBlank
    @Isbn
    private String isbn;
    @NotNull
    @Positive
    private BigDecimal price;
    private String description;
    private String coverImage;
    @NotEmpty
    private Set<Long> categoryIds;
}
