package core.basesyntax.mapper;

import core.basesyntax.model.Category;
import core.basesyntax.repository.category.CategoryRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryMapperHelper {
    private final CategoryRepository categoryRepository;

    public Set<Category> mapCategoryIdsToCategories(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptySet();
        }
        return new HashSet<>(categoryRepository.findAllById(ids));
    }
}
