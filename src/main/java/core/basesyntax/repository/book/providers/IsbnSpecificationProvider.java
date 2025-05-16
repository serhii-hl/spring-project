package core.basesyntax.repository.book.providers;

import core.basesyntax.model.Book;
import core.basesyntax.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "isbn";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                (criteriaBuilder.like(root.get("isbn"), "%" + params[0] + "%"));
    }
}
