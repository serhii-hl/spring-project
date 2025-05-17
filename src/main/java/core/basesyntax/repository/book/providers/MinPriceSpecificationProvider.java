package core.basesyntax.repository.book.providers;

import core.basesyntax.model.Book;
import core.basesyntax.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MinPriceSpecificationProvider implements SpecificationProvider<Book> {
    private static final String KEY = "min_price";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        Integer minPrice = params.length > 0 && !params[0].isEmpty()
                ? Integer.parseInt(params[0]) : null;
        return (root, query, criteriaBuilder) ->
                minPrice != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice)
                        : criteriaBuilder.conjunction();
    }
}
