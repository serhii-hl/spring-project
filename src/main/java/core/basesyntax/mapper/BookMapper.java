package core.basesyntax.mapper;

import core.basesyntax.config.MapperConfig;
import core.basesyntax.dto.BookDto;
import core.basesyntax.dto.CreateBookRequestDto;
import core.basesyntax.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    public BookDto toDto(Book book);

    public Book toBook(CreateBookRequestDto requestDto);

    public void updateBook(@MappingTarget Book book, CreateBookRequestDto bookRequestDto);
}
