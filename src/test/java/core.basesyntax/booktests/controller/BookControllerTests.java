package core.basesyntax.booktests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.basesyntax.booktests.TestUtil;
import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.book.CreateBookRequestDto;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

@Transactional
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
    @Sql(scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all books - success")
    void getAllBooksSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        Map<String, Object> actual = objectMapper.readValue(json,
                new TypeReference<>() {});

        List<Map<String, Object>> actualContent =
                (List<Map<String, Object>>) actual.get("content");

        List<BookDto> expectedBooks = TestUtil.createAllBooks();

        assertThat(actualContent).hasSize(expectedBooks.size());

        for (BookDto expected : expectedBooks) {
            boolean found = actualContent.stream().anyMatch(map ->
                    expected.getId().equals(((Number) map.get("id")).longValue())
                            && expected.getTitle().equals(map.get("title"))
                            && expected.getAuthor().equals(map.get("author"))
                            && expected.getIsbn().equals(map.get("isbn"))
                            && BigDecimal.valueOf(Double.parseDouble(map.get("price")
                                    .toString()))
                                    .compareTo(expected.getPrice()) == 0
            );
            assertThat(found)
                    .withFailMessage(
                            "Book with id not found or doesn't match",
                            expected.getId()).isTrue();
        }

        assertThat(((Number) actual.get("totalElements"))
                .longValue()).isEqualTo(expectedBooks.size());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get book by id - success")
    void getBookByIdSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        Map<String, Object> actual = objectMapper.readValue(json, new TypeReference<>() {});
        BookDto expected = TestUtil.createJavaBookDto();

        assertThat(actual)
                .extracting(
                        m -> ((Number)m.get("id")).longValue(),
                        m -> m.get("title"),
                        m -> m.get("author"),
                        m -> m.get("isbn"),
                        m -> BigDecimal.valueOf(Double
                                .parseDouble(m.get("price").toString()))
                )
                .containsExactly(
                        expected.getId(),
                        expected.getTitle(),
                        expected.getAuthor(),
                        expected.getIsbn(),
                        expected.getPrice()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get book by params - success")
    void getBookByParamsSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books/search")
                        .param("authors", "History author")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        Map<String, Object> actual = objectMapper.readValue(json, new TypeReference<>() {});
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> actualContent = (List<Map<String, Object>>) actual.get("content");

        assertThat(actualContent).hasSize(1);

        Map<String, Object> actualNode = actualContent.get(0);
        BookDto expected = TestUtil.createHistoryBookDto();

        assertThat(actualNode)
                .extracting(
                        m -> ((Number)m.get("id")).longValue(),
                        m -> m.get("title"),
                        m -> m.get("author"),
                        m -> m.get("isbn"),
                        m -> BigDecimal.valueOf(Double
                                .parseDouble(m.get("price").toString()))
                )
                .containsExactly(
                        expected.getId(),
                        expected.getTitle(),
                        expected.getAuthor(),
                        expected.getIsbn(),
                        expected.getPrice()
                );
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Create book - success")
    @Sql(scripts = "classpath:database/books/create-categories-test.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-test-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBookSuccess() throws Exception {
        CreateBookRequestDto createBookRequestDto = TestUtil.createBook(
                "Java", "Author", "Java book",
                "978-0123456789", "image");

        BookDto expected = TestUtil.expectedBook(
                1L, "Java", "Author", "Java book",
                "978-0123456789", "image");

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

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "categoryIds")
                .isEqualTo(expected);
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Update book - success")
    @Sql(scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateBookByIdSuccess() throws Exception {
        CreateBookRequestDto createBookRequestDto = TestUtil.createBook(
                "Javar", "Authorrr", "Javar book",
                "978-0123456789", "imager");

        BookDto expected = TestUtil.expectedBook(
                1L, "Javar", "Authorrr", "Javar book",
                "978-0123456789", "imager");

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

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "categoryIds")
                .isEqualTo(expected);
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Delete book - success")
    @Sql(scripts = "classpath:database/books/create-one-test-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteBookSuccess() throws Exception {
        mockMvc.perform(delete("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
