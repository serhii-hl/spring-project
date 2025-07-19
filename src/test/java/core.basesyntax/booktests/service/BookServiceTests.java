package core.basesyntax.booktests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.booktests.TestUtil;
import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.CreateBookRequestDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.BookMapper;
import core.basesyntax.mapper.CategoryMapperHelper;
import core.basesyntax.model.Book;
import core.basesyntax.model.BookSearchParameters;
import core.basesyntax.repository.book.BookRepository;
import core.basesyntax.repository.book.BookSpecificationBuilder;
import core.basesyntax.service.impl.BookServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private CategoryMapperHelper categoryMapperHelper;

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
        BookDto expectedDto = createBookDto();

        when(bookMapper.toBook(bookRequestDto, categoryMapperHelper)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(expectedDto);

        BookDto result = bookService.save(bookRequestDto);

        assertEquals(expectedDto, result);
    }

    @Test
    @DisplayName("Update book and check if the data is correct")
    void updateBookShouldReturnCorrectDto() {
        CreateBookRequestDto bookRequestDto = createRequestDto();
        Book initialBook = createInitialBook();
        Book updatedBook = createUpdatedBook();
        BookDto expectedDto = createBookDto();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(initialBook));
        doNothing().when(bookMapper).updateBook(any(Book.class), any(CreateBookRequestDto.class));
        when(bookRepository.save(initialBook)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedDto);

        BookDto result = bookService.update(bookRequestDto, 1L);

        assertEquals(expectedDto, result);
    }

    @Test
    @DisplayName("Find book by id should throw exception if book not found")
    void updateByIdShouldThrowIfNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.findById(99L));
    }

    @Test
    @DisplayName("Find all books and map to DTOs")
    void findAllShouldReturnPagedDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = createUpdatedBook();
        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);
        BookDto dto = createBookDto();
        Page<BookDto> expectedPage = new PageImpl<>(List.of(dto), pageable, 1);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(dto);

        Page<BookDto> result = bookService.findAll(pageable);

        assertEquals(expectedPage, result);
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
        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookSpecificationBuilder.build(params)).thenReturn(spec);
        when(bookRepository.findAll(spec, pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(dto);

        Page<BookDto> expectedPage = new PageImpl<>(List.of(dto), pageable, 1);

        Page<BookDto> result = bookService.search(params, pageable);

        assertEquals(expectedPage, result);
    }

    @Test
    @DisplayName("Search book using category id and check if the data is correct")
    void findAllByCategoryShouldReturnCorrectDto() {
        Book book = createUpdatedBook();
        BookDto expectedDto = createBookDto();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> page = new PageImpl<>(List.of(book), pageable, 1);
        Page<BookDto> expectedPage = new PageImpl<>(List.of(expectedDto), pageable, 1);

        when(bookRepository.findAllByCategoryId(1L, pageable)).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        Page<BookDto> result = bookService.findAllByCategoryId(1L, pageable);

        assertEquals(expectedPage, result);
    }

    @Test
    @DisplayName("Find book by id should return correct DTO")
    void findByIdShouldReturnCorrectDto() {
        Book book = createUpdatedBook();
        BookDto expectedDto = createBookDto();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto result = bookService.findById(1L);

        assertEquals(expectedDto, result);
    }

    @Test
    @DisplayName("Delete book by id should call repository")
    void deleteByIdShouldCallRepository() {
        Long id = 1L;

        bookService.deleteById(id);

        verify(bookRepository).deleteById(id);
    }

    private CreateBookRequestDto createRequestDto() {
        return TestUtil.createBook(
                "Java", "author", "description",
                "9780123456789", "image");
    }

    private Book createInitialBook() {
        return TestUtil.createInitialBook();
    }

    private Book createUpdatedBook() {
        return TestUtil.createInitialBook();
    }

    private BookDto createBookDto() {
        return TestUtil.expectedBook(
                1L, "Javar", "Authorrr", "Javar book", "978-0123456789", "imager");
    }
}
