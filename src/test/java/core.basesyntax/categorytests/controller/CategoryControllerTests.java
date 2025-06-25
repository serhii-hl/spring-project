package core.basesyntax.categorytests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.basesyntax.dto.book.BookDto;
import core.basesyntax.dto.category.CategoryDto;
import core.basesyntax.dto.category.CreateCategoryRequestDto;
import jakarta.transaction.Transactional;
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
public class CategoryControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(@Autowired WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(scripts = "classpath:database/categories/insert-test-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/delete-two-test-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all categories - success")
    void getAllCategoriesSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/categories")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        JsonNode root = objectMapper.readTree(json);
        JsonNode contentNode = root.get("content");

        CategoryDto[] categories = objectMapper.treeToValue(contentNode,
                CategoryDto[].class);

        assertThat(categories)
                .extracting(CategoryDto::getName)
                .containsExactlyInAnyOrder("Category 1", "Category 2");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @Sql(scripts = "classpath:database/categories/insert-test-categories-getbyid.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/delete-two-test-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get category by category id - success")
    void getOneCategoryByIdSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/categories/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), CategoryDto.class);

        assertThat(actual.getName()).isEqualTo("Category 1");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("Get books by category id - success")
    @Sql(scripts = "classpath:database/books/add-books-to-test-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/delete-books-from-test-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getBookByCategoryByIdSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/categories/1/books")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        JsonNode root = objectMapper.readTree(json);
        JsonNode contentNode = root.get("content");

        BookDto[] books = objectMapper.treeToValue(contentNode, BookDto[].class);

        assertThat(books)
                .extracting(BookDto::getTitle)
                .contains("Java")
                .doesNotContain("History of UA");

        assertThat(books)
                .extracting(BookDto::getAuthor)
                .contains("Java author")
                .doesNotContain("History author");
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Update category - success")
    @Sql(scripts = "classpath:database/categories/create-test-update-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/delete-test-update-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCategoryByIdSuccess() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("new category");
        requestDto.setDescription("new category description");

        CategoryDto expected = new CategoryDto();
        expected.setName("new category");
        expected.setDescription("new category description");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/categories/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Create category - success")
    @Sql(scripts = "classpath:database/categories/delete-test-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createCategorySuccess() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("test");
        requestDto.setDescription("description");

        CategoryDto expected = new CategoryDto();
        expected.setName("test");
        expected.setDescription("description");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @DisplayName("Delete category - success")
    @Sql(scripts = "classpath:database/categories/create-test-update-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/delete-test-update-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteCategorySuccess() throws Exception {
        mockMvc.perform(delete("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/categories/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
