package core.basesyntax.service;

import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.CreateBookRequestDto;
import core.basesyntax.model.BookSearchParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    BookDto update(CreateBookRequestDto bookRequestDto, Long id);

    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    Page<BookDto> search(BookSearchParameters bookSearchParameters, Pageable pageable);
}
