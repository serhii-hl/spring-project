package core.basesyntax.mapper;

import core.basesyntax.config.MapperConfig;
import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.BookDtoWithoutCategoryIds;
import core.basesyntax.dto.book.CreateBookRequestDto;
import core.basesyntax.model.Book;
import core.basesyntax.model.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CategoryMapperHelper.class})
public interface BookMapper {

    public BookDto toDto(Book book);

    public Book toBook(CreateBookRequestDto requestDto, @Context CategoryMapperHelper helper);

    public void updateBook(@MappingTarget Book book, CreateBookRequestDto bookRequestDto);

    public BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto dto, Book book) {
        if (book.getCategories() != null) {
            dto.setCategoryIds(
                    book.getCategories().stream()
                            .map(Category::getId)
                            .collect(Collectors.toSet())
            );
        }
    }

    @AfterMapping
    default void mapCategoryIdsToCategories(@MappingTarget Book book,
                                            CreateBookRequestDto dto,
            @Context CategoryMapperHelper helper) {
        if (dto.getCategoryIds() != null) {
            book.setCategories(helper.mapCategoryIdsToCategories(dto.getCategoryIds()));
        }
    }
}
