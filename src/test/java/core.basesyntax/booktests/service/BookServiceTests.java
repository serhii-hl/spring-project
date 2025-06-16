package core.basesyntax.booktests.service;

import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.CreateBookRequestDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.BookMapper;
import core.basesyntax.model.Book;
import core.basesyntax.model.BookSearchParameters;
import core.basesyntax.repository.book.BookRepository;
import core.basesyntax.repository.book.BookSpecificationBuilder;
import core.basesyntax.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Save book and check if the data is correct")
    void saveBookShouldReturnCorrectDto() {
        CreateBookRequestDto bookRequestDto = createRequestDto();
        Book book = createUpdatedBook();
        Book savedBook = createUpdatedBook();
        BookDto bookDto = createBookDto();

        Mockito.when(bookMapper.toBook(bookRequestDto)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(savedBook);
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(bookDto);

        BookDto result = bookService.save(bookRequestDto);

        assertBookDtoEquals(bookDto, result);
    }

    @Test
    @DisplayName("Update book and check if the data is correct")
    void updateBookShouldReturnCorrectDto() {
        CreateBookRequestDto bookRequestDto = createRequestDto();
        Book initialBook = createInitialBook();
        Book updatedBook = createUpdatedBook();
        BookDto bookDto = createBookDto();

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(initialBook));
        Mockito.doNothing().when(bookMapper).updateBook(Mockito.any(Book.class),
                Mockito.any(CreateBookRequestDto.class));
        Mockito.when(bookRepository.save(initialBook)).thenReturn(updatedBook);
        Mockito.when(bookMapper.toDto(updatedBook)).thenReturn(bookDto);

        BookDto result = bookService.update(bookRequestDto, 1L);

        assertBookDtoEquals(bookDto, result);
    }

    @Test
    @DisplayName("Find book by id should throw exception if book not found")
    void updateByIdShouldThrowIfNotFound() {
        Mockito.when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.findById(99L));
    }

    @Test
    @DisplayName("Find all books and map to DTOs")
    void findAllShouldReturnPagedDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = createUpdatedBook();
        Page<Book> bookPage = new PageImpl<>(List.of(book));
        BookDto dto = createBookDto();

        Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(dto);

        Page<BookDto> result = bookService.findAll(pageable);

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(1L, result.getContent().get(0).getId());
    }

    @Test
    @DisplayName("Search books using specification and map to DTOs")
    void searchShouldReturnPagedDtos() {
        BookSearchParameters params = new BookSearchParameters(
                new String[]{"title"}, new String[]{"author"}, "9780123456789", 10, 50);
        Specification<Book> spec = (root, query, cb) -> null;
        Book book = createUpdatedBook();
        BookDto dto = createBookDto();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        Mockito.when(bookSpecificationBuilder.build(params)).thenReturn(spec);
        Mockito.when(bookRepository.findAll(spec, pageable)).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(dto);

        Page<BookDto> result = bookService.search(params, pageable);

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(1L, result.getContent().get(0).getId());
    }

    @Test
    @DisplayName("Search book using category id and check if the data is correct")
    void findAllByCategoryShouldReturnCorrectDto() {
        Book book = createUpdatedBook();
        BookDto bookDto = createBookDto();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> page = new PageImpl<>(List.of(book));

        Mockito.when(bookRepository.findAllByCategoryId(1L, pageable)).thenReturn(page);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        Page<BookDto> result = bookService.findAllByCategoryId(1L, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        assertBookDtoEquals(bookDto, result.getContent().get(0));
    }

    @Test
    @DisplayName("Find book by id should return correct DTO")
    void findByIdShouldReturnCorrectDto() {
        Book book = createUpdatedBook();
        BookDto bookDto = createBookDto();

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.findById(1L);

        Assertions.assertNotNull(result);
        assertBookDtoEquals(bookDto, result);
    }

    @Test
    @DisplayName("Delete book by id should call repository")
    void deleteByIdShouldCallRepository() {
        Long id = 1L;
        bookService.deleteById(id);
        Mockito.verify(bookRepository).deleteById(id);
    }

    private CreateBookRequestDto createRequestDto() {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setAuthor("author");
        dto.setTitle("title");
        dto.setDescription("description");
        dto.setIsbn("9780123456789");
        dto.setPrice(BigDecimal.valueOf(100));
        dto.setCategoryIds(Set.of(1L, 2L, 3L));
        return dto;
    }

    private Book createInitialBook() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("init_author");
        book.setTitle("init_title");
        book.setDescription("init_description");
        book.setIsbn("9780123456780");
        book.setPrice(BigDecimal.valueOf(200));
        return book;
    }

    private Book createUpdatedBook() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("author");
        book.setTitle("title");
        book.setDescription("description");
        book.setIsbn("9780123456789");
        book.setPrice(BigDecimal.valueOf(100));
        return book;
    }

    private BookDto createBookDto() {
        BookDto dto = new BookDto();
        dto.setId(1L);
        dto.setAuthor("author");
        dto.setTitle("title");
        dto.setDescription("description");
        dto.setIsbn("9780123456789");
        dto.setPrice(BigDecimal.valueOf(100));
        dto.setCategoryIds(Set.of(1L, 2L, 3L));
        return dto;
    }

    private void assertBookDtoEquals(BookDto expected, BookDto actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getAuthor(), actual.getAuthor());
        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getIsbn(), actual.getIsbn());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expected.getCategoryIds(), actual.getCategoryIds());
    }
}
