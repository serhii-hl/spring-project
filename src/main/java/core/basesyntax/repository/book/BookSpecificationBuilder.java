package core.basesyntax.repository.book;

import core.basesyntax.model.Book;
import core.basesyntax.model.BookSearchParameters;
import core.basesyntax.repository.SpecificationBuilder;
import core.basesyntax.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(specificationProviderManager.getProvider("author")
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.isbn() != null && searchParameters.isbn().length() > 0) {
            String isbn = searchParameters.isbn();
            spec = spec.and(specificationProviderManager.getProvider("isbn")
                    .getSpecification(new String[]{isbn}));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(specificationProviderManager.getProvider("title")
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.minPrice() != null && searchParameters.minPrice() > 0) {
            String[] params = {String.valueOf(searchParameters.minPrice())};
            spec = spec.and(specificationProviderManager.getProvider("min_price")
                    .getSpecification(params));
        }
        if (searchParameters.minPrice() != null && searchParameters.maxPrice() > 0) {
            String[] params = {String.valueOf(searchParameters.maxPrice())};
            spec = spec.and(specificationProviderManager.getProvider("max_price")
                    .getSpecification(params));
        }
        return spec;
    }
}
