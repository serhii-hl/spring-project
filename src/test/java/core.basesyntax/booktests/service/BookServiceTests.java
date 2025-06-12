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
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setAuthor("author");
        bookRequestDto.setTitle("title");
        bookRequestDto.setDescription("description");
        bookRequestDto.setIsbn("9780123456789");
        bookRequestDto.setPrice(BigDecimal.valueOf(100));
        bookRequestDto.setCategoryIds(Set.of(1L, 2L, 3L));

        Book book = new Book();
        book.setId(1L);
        book.setAuthor("author");
        book.setTitle("title");
        book.setDescription("description");
        book.setIsbn("9780123456789");
        book.setPrice(BigDecimal.valueOf(100));

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setAuthor("author");
        savedBook.setTitle("title");
        savedBook.setDescription("description");
        savedBook.setIsbn("9780123456789");
        savedBook.setPrice(BigDecimal.valueOf(100));

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("author");
        bookDto.setTitle("title");
        bookDto.setDescription("description");
        bookDto.setIsbn("9780123456789");
        bookDto.setPrice(BigDecimal.valueOf(100));
        bookDto.setCategoryIds(Set.of(1L, 2L, 3L));

        Mockito.when(bookMapper.toBook(bookRequestDto)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(savedBook);
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(bookDto);

        BookDto result = bookService.save(bookRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("author", result.getAuthor());
        Assertions.assertEquals("title", result.getTitle());
        Assertions.assertEquals("description", result.getDescription());
        Assertions.assertEquals("9780123456789", result.getIsbn());
        Assertions.assertEquals(BigDecimal.valueOf(100), result.getPrice());
        Assertions.assertEquals(Set.of(1L, 2L, 3L), result.getCategoryIds());
    }

    @Test
    @DisplayName("Update book and check if the data is correct")
    void updateBookShouldReturnCorrectDto() {
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setAuthor("author");
        bookRequestDto.setTitle("title");
        bookRequestDto.setDescription("description");
        bookRequestDto.setIsbn("9780123456789");
        bookRequestDto.setPrice(BigDecimal.valueOf(100));
        bookRequestDto.setCategoryIds(Set.of(1L, 2L, 3L));

        Book initialBook = new Book();
        initialBook.setId(1L);
        initialBook.setAuthor("init_author");
        initialBook.setTitle("init_title");
        initialBook.setDescription("init_description");
        initialBook.setIsbn("9780123456780");
        initialBook.setPrice(BigDecimal.valueOf(200));

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setAuthor("author");
        updatedBook.setTitle("title");
        updatedBook.setDescription("description");
        updatedBook.setIsbn("9780123456789");
        updatedBook.setPrice(BigDecimal.valueOf(100));

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setAuthor("author");
        savedBook.setTitle("title");
        savedBook.setDescription("description");
        savedBook.setIsbn("9780123456789");
        savedBook.setPrice(BigDecimal.valueOf(100));

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("author");
        bookDto.setTitle("title");
        bookDto.setDescription("description");
        bookDto.setIsbn("9780123456789");
        bookDto.setPrice(BigDecimal.valueOf(100));
        bookDto.setCategoryIds(Set.of(1L, 2L, 3L));

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(initialBook));
        Mockito.doNothing().when(bookMapper)
                .updateBook(Mockito.any(Book.class), Mockito.any(CreateBookRequestDto.class));
        Mockito.when(bookRepository.save(initialBook)).thenReturn(savedBook);
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(bookDto);

        BookDto result = bookService.update(bookRequestDto, 1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("author", result.getAuthor());
        Assertions.assertEquals("title", result.getTitle());
        Assertions.assertEquals("description", result.getDescription());
        Assertions.assertEquals("9780123456789", result.getIsbn());
        Assertions.assertEquals(BigDecimal.valueOf(100), result.getPrice());
        Assertions.assertEquals(Set.of(1L, 2L, 3L), result.getCategoryIds());
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
        Book book = new Book();
        book.setId(1L);
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        BookDto dto = new BookDto();
        dto.setId(1L);

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
                new String[]{"title"}, new String[]{"author"},
                        "9780123456789", 10, 50);

        Specification<Book> spec =
                (root, query, criteriaBuilder) -> null;

        Book book = new Book();
        book.setId(1L);
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        BookDto dto = new BookDto();
        dto.setId(1L);

        Pageable pageable = PageRequest.of(0, 5);

        Mockito.when(bookSpecificationBuilder.build(params)).thenReturn(spec);
        Mockito.when(bookRepository.findAll(spec, pageable)).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(dto);

        // then
        Page<BookDto> result = bookService.search(params, pageable);

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(1L, result.getContent().get(0).getId());
    }

    @Test
    @DisplayName("Search book using category id and check if the data is correct")
    void findAllByCategoryShouldReturnCorrectDto() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("author");
        book.setTitle("title");
        book.setDescription("description");
        book.setIsbn("9780123456789");
        book.setPrice(BigDecimal.valueOf(100));

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("author");
        bookDto.setTitle("title");
        bookDto.setDescription("description");
        bookDto.setIsbn("9780123456789");
        bookDto.setPrice(BigDecimal.valueOf(100));

        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> page = new PageImpl<>(List.of(book));

        Mockito.when(bookRepository.findAllByCategoryId(1L, pageable)).thenReturn(page);
        Mockito.when(bookMapper.toDto(page.getContent().get(0))).thenReturn(bookDto);

        Page<BookDto> result = bookService.findAllByCategoryId(1L, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        BookDto resultDto = result.getContent().get(0);
        Assertions.assertEquals("author", resultDto.getAuthor());
        Assertions.assertEquals("title", resultDto.getTitle());
        Assertions.assertEquals("description", resultDto.getDescription());
        Assertions.assertEquals("9780123456789", resultDto.getIsbn());
        Assertions.assertEquals(BigDecimal.valueOf(100), resultDto.getPrice());
    }

    @Test
    @DisplayName("Find book by id should return correct DTO")
    void findByIdShouldReturnCorrectDto() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("author");
        book.setTitle("title");
        book.setDescription("description");
        book.setIsbn("9780123456789");
        book.setPrice(BigDecimal.valueOf(100));

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("author");
        bookDto.setTitle("title");
        bookDto.setDescription("description");
        bookDto.setIsbn("9780123456789");
        bookDto.setPrice(BigDecimal.valueOf(100));

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("author", result.getAuthor());
        Assertions.assertEquals("title", result.getTitle());
        Assertions.assertEquals("description", result.getDescription());
        Assertions.assertEquals("9780123456789", result.getIsbn());
        Assertions.assertEquals(BigDecimal.valueOf(100), result.getPrice());
    }

    @Test
    @DisplayName("Delete book by id should call repository")
    void deleteByIdShouldCallRepository() {
        Long id = 1L;

        bookService.deleteById(id);

        Mockito.verify(bookRepository).deleteById(id);
    }
}
