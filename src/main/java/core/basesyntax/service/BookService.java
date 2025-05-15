package core.basesyntax.service;

import core.basesyntax.dto.BookDto;
import core.basesyntax.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);
}
