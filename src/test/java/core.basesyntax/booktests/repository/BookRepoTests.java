package core.basesyntax.booktests.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import core.basesyntax.model.Book;
import core.basesyntax.repository.book.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepoTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find All Books By Category Id")
    @Sql(scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryIdEquals() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> books = bookRepository.findAllByCategoryId(1L,pageable);
        assertThat(books.getTotalElements()).isEqualTo(1);
        assertThat(books.getContent().get(0).getTitle()).isEqualTo("Java");
    }
}
