package core.basesyntax.repository.book.providers;

import core.basesyntax.model.Book;
import core.basesyntax.repository.SpecificationProvider;
import core.basesyntax.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> specificationProvider;

    @Override
    public SpecificationProvider<Book> getProvider(String key) {
        return specificationProvider.stream().filter(spec ->
                spec.getKey()
                        .equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No specification found for " + key));
    }
}
