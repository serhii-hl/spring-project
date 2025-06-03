package core.basesyntax.mapper;

import core.basesyntax.config.MapperConfig;
import core.basesyntax.dto.cartitem.CartItemDto;
import core.basesyntax.dto.cartitem.CartItemResponseDto;
import core.basesyntax.model.Book;
import core.basesyntax.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "title")
    public CartItemDto toDto(CartItem cartItem);

    @Mapping(source = "bookId", target = "book", qualifiedByName = "bookFromId")
    CartItem toEntity(CartItemDto dto);

    public CartItemResponseDto toResponceDto(CartItemDto dto);

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
