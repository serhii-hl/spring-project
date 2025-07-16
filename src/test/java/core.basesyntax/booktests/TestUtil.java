package core.basesyntax.booktests;

import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.CreateBookRequestDto;
import core.basesyntax.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class TestUtil {
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

    public static BookDto createJavaBookDto() {
        BookDto dto = new BookDto();
        dto.setId(1L);
        dto.setTitle("Java");
        dto.setAuthor("Java author");
        dto.setIsbn("9780123456789");
        dto.setPrice(BigDecimal.valueOf(49.99));
        dto.setCategoryIds(Set.of(1L));
        return dto;
    }

    public static BookDto createHistoryBookDto() {
        BookDto dto = new BookDto();
        dto.setId(2L);
        dto.setTitle("History of UA");
        dto.setAuthor("History author");
        dto.setIsbn("9780123456780");
        dto.setPrice(BigDecimal.valueOf(59.99));
        dto.setCategoryIds(Set.of(2L));
        return dto;
    }

    public static List<BookDto> createAllBooks() {
        return List.of(createJavaBookDto(), createHistoryBookDto());
    }
}
