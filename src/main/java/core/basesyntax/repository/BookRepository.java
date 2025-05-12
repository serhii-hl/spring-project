package core.basesyntax.repository;

import core.basesyntax.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List findAll();
}
