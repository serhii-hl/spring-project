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
    public static final String AUTHOR_KEY = "author";
    public static final String ISBN_KEY = "isbn";
    public static final String TITLE_KEY = "title";
    public static final String MINPRICE_KEY = "min_price";
    public static final String MAXPRICE_KEY = "max_price";
    private final SpecificationProviderManager specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(specificationProviderManager.getProvider(AUTHOR_KEY)
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.isbn() != null && searchParameters.isbn().length() > 0) {
            String isbn = searchParameters.isbn();
            spec = spec.and(specificationProviderManager.getProvider(ISBN_KEY)
                    .getSpecification(new String[]{isbn}));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(specificationProviderManager.getProvider(TITLE_KEY)
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.minPrice() != null && searchParameters.minPrice() > 0) {
            String[] params = {String.valueOf(searchParameters.minPrice())};
            spec = spec.and(specificationProviderManager.getProvider(MINPRICE_KEY)
                    .getSpecification(params));
        }
        if (searchParameters.minPrice() != null && searchParameters.maxPrice() > 0) {
            String[] params = {String.valueOf(searchParameters.maxPrice())};
            spec = spec.and(specificationProviderManager.getProvider(MAXPRICE_KEY)
                    .getSpecification(params));
        }
        return spec;
    }
}
