package core.basesyntax.booktests;

import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.CreateBookRequestDto;
import core.basesyntax.model.Book;
import java.math.BigDecimal;
import java.util.Set;

public class TestBookFactory {
    public static CreateBookRequestDto createBook(String title, String author,
                                                  String description, String isbn,
                                                  String coverImage) {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setPrice(BigDecimal.valueOf(50));
        dto.setIsbn(isbn);
        dto.setAuthor(author);
        dto.setDescription(description);
        dto.setTitle(title);
        dto.setCoverImage(coverImage);
        dto.setCategoryIds(Set.of(1L));
        return dto;
    }

    public static BookDto expectedBook(Long id, String title,
                                       String author, String description,
                                       String isbn, String coverImage) {
        BookDto dto = new BookDto();
        dto.setId(id);
        dto.setPrice(BigDecimal.valueOf(50));
        dto.setIsbn(isbn);
        dto.setAuthor(author);
        dto.setDescription(description);
        dto.setTitle(title);
        dto.setCoverImage(coverImage);
        dto.setCategoryIds(Set.of(1L));
        return dto;
    }

    public static Book createInitialBook() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("init_author");
        book.setTitle("init_title");
        book.setDescription("init_description");
        book.setIsbn("9780123456780");
        book.setPrice(BigDecimal.valueOf(200));
        return book;
    }
}
