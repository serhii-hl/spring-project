package core.basesyntax.model;

public record BookSearchParameters(String[] titles, String[] authors,
                                      String isbn, Integer minPrice, Integer maxPrice) {
}
