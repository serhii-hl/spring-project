package core.basesyntax.service;

import core.basesyntax.dto.BookDto;
import core.basesyntax.dto.CreateBookRequestDto;
import core.basesyntax.model.BookSearchParameters;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    BookDto update(CreateBookRequestDto bookRequestDto, Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters bookSearchParameters);
}
