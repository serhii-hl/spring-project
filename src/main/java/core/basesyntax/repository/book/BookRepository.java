package core.basesyntax.repository.book;

import core.basesyntax.model.Book;
import jakarta.annotation.Nullable;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @EntityGraph(attributePaths = {"categories"})
    @Query("""
    SELECT b FROM Book b
    JOIN b.categories c
    WHERE c.id = :categoryId
      AND b.isDeleted = false
      AND c.isDeleted = false
                """)
    Page<Book> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Optional<Book> findById(Long id);

    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(@Nullable Specification<Book> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"categories"})
    Page<Book> findAll(Pageable pageable);
}
