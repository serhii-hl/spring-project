package core.basesyntax.repository.book.providers;

import core.basesyntax.model.Book;
import core.basesyntax.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MaxPriceSpecificationProvider implements SpecificationProvider<Book> {
    private static final String KEY = "max_price";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        Integer maxPrice = params.length > 0 && !params[0].isEmpty()
                ? Integer.parseInt(params[0]) : null;
        return (root, query, criteriaBuilder) ->
                maxPrice != null ? criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice)
                        : criteriaBuilder.conjunction();
    }
}
