package core.basesyntax.service.impl;

import core.basesyntax.dto.BookDto;
import core.basesyntax.dto.CreateBookRequestDto;
import core.basesyntax.mapper.BookMapper;
import core.basesyntax.model.Book;
import core.basesyntax.repository.BookRepository;
import core.basesyntax.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toBook(bookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not find Book with id " + id));
        return bookMapper.toDto(book);
    }
}
