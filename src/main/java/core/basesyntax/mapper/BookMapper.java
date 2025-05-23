package core.basesyntax.mapper;

import core.basesyntax.config.MapperConfig;
import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.CreateBookRequestDto;
import core.basesyntax.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    public BookDto toDto(Book book);

    public Book toBook(CreateBookRequestDto requestDto);

    public void updateBook(@MappingTarget Book book, CreateBookRequestDto bookRequestDto);
}
