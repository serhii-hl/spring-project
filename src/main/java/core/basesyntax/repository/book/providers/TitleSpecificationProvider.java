package core.basesyntax.repository.book.providers;

import static core.basesyntax.repository.book.BookSpecificationBuilder.TITLE_KEY;

import core.basesyntax.model.Book;
import core.basesyntax.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return TITLE_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) ->
                root.get(TITLE_KEY)
                        .in(Arrays.stream(params)
                                .toArray()));
    }
}
