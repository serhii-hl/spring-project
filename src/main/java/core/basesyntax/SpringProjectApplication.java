package core.basesyntax;

import core.basesyntax.model.Book;
import core.basesyntax.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringProjectApplication {
    private final BookService bookService;

    @Autowired
    public SpringProjectApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("1984");
            book.setAuthor("Orwell");
            book.setPrice(BigDecimal.valueOf(1000));
            book.setIsbn("978-3-16-148410-0");
            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
