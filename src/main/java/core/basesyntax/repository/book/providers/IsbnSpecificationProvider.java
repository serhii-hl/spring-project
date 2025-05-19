package core.basesyntax.repository.book.providers;

import static core.basesyntax.repository.book.BookSpecificationBuilder.ISBN_KEY;

import core.basesyntax.model.Book;
import core.basesyntax.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return ISBN_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                (criteriaBuilder.like(root.get(ISBN_KEY), "%" + params[0] + "%"));
    }
}
