package core.basesyntax.service;

import core.basesyntax.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
