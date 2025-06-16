package core.basesyntax.booktests.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.CreateBookRequestDto;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTests {
    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach(@Autowired WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(
            scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(
            scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Get all books - success")
    void getAllBooksSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(responseBody.contains("Java"));
        Assertions.assertTrue(responseBody.contains("History of UA"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(
            scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(
            scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Get book by id - success")
    void getBookByIdSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books/1")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(responseBody.contains("Java"));
        Assertions.assertFalse(responseBody.contains("History of UA"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(
            scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(
            scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Get book by params - success")
    void getBookByParamsSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books/search")
                        .param("authors", "History author")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(responseBody.contains("History of UA"));
        Assertions.assertFalse(responseBody.contains("Java"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Create book - success")
    @Sql(
            scripts = "classpath:database/books/create-categories-test.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/books/delete-test-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void createBookSuccess() throws Exception {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setPrice(BigDecimal.valueOf(50));
        createBookRequestDto.setIsbn("978-0123456789");
        createBookRequestDto.setAuthor("Author");
        createBookRequestDto.setDescription("Java book");
        createBookRequestDto.setTitle("Java");
        createBookRequestDto.setCoverImage("image");
        createBookRequestDto.setCategoryIds(Set.of(1L));

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setPrice(BigDecimal.valueOf(50));
        expected.setIsbn("978-0123456789");
        expected.setAuthor("Author");
        expected.setDescription("Java book");
        expected.setTitle("Java");
        expected.setCoverImage("image");
        expected.setCategoryIds(Set.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);

        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        BookDto actual = objectMapper.readValue(responseBody, BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected,
                actual, "id", "categoryIds"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Update book - success")
    @Sql(
            scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void updateBookByIdSuccess() throws Exception {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setPrice(BigDecimal.valueOf(50));
        createBookRequestDto.setIsbn("978-0123456789");
        createBookRequestDto.setAuthor("Authorrr");
        createBookRequestDto.setDescription("Javar book");
        createBookRequestDto.setTitle("Javar");
        createBookRequestDto.setCoverImage("imager");
        createBookRequestDto.setCategoryIds(Set.of(1L));

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setPrice(BigDecimal.valueOf(50));
        expected.setIsbn("978-0123456789");
        expected.setAuthor("Authorrr");
        expected.setDescription("Javar book");
        expected.setTitle("Javar");
        expected.setCoverImage("imager");
        expected.setCategoryIds(Set.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);

        MvcResult result = mockMvc.perform(
                        put("/books/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        BookDto actual = objectMapper.readValue(responseBody, BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected,
                actual, "id", "categoryIds"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Delete book - success")
    @Sql(
            scripts = "classpath:database/books/create-one-test-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void deleteBookSuccess() throws Exception {
        mockMvc.perform(delete("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/books/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
