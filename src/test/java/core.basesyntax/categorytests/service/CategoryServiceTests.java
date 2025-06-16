package core.basesyntax.categorytests.service;

import core.basesyntax.dto.category.CategoryDto;
import core.basesyntax.dto.category.CreateCategoryRequestDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.CategoryMapper;
import core.basesyntax.model.Category;
import core.basesyntax.repository.category.CategoryRepository;
import core.basesyntax.service.impl.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Find all categories should return all categories")
    void findAllShouldReturnPagedDtos() {
        Category category = createCategory();
        CategoryDto dto = createCategoryDto();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(List.of(category));

        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(dto);

        Page<CategoryDto> result = categoryService.findAll(pageable);

        Assertions.assertTrue(result.hasContent());
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(1, result.getTotalPages());
        assertCategoryDtoEquals(dto, result.getContent().get(0));
    }

    @Test
    @DisplayName("Get category by id should return correct dto")
    void findByIdShouldReturnCorrectDto() {
        Category category = createCategory();
        CategoryDto dto = createCategoryDto();

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(dto);

        CategoryDto result = categoryService.getById(1L);

        Assertions.assertNotNull(result);
        assertCategoryDtoEquals(dto, result);
    }

    @Test
    @DisplayName("Get category by id should throw exception if not found")
    void findByIdShouldThrowIfNotFound() {
        Mockito.when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> categoryService.getById(99L));
    }

    @Test
    @DisplayName("Save category should return correct dto")
    void saveCategoryShouldReturnCorrectDto() {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        Category category = createCategory();
        CategoryDto dto = createCategoryDto();

        Mockito.when(categoryMapper.toCategory(requestDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(dto);

        CategoryDto result = categoryService.save(requestDto);

        Assertions.assertNotNull(result);
        assertCategoryDtoEquals(dto, result);
    }

    @Test
    @DisplayName("Update category should return correct dto")
    void updateCategoryShouldReturnCorrectDto() {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        Category initCategory = createCategory();
        Category updatedCategory = createCategory();
        CategoryDto dto = createCategoryDto();

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(initCategory));
        Mockito.doNothing().when(categoryMapper).updateCategory(initCategory, requestDto);
        Mockito.when(categoryRepository.save(initCategory)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.toDto(updatedCategory)).thenReturn(dto);

        CategoryDto result = categoryService.update(1L, requestDto);

        Assertions.assertNotNull(result);
        assertCategoryDtoEquals(dto, result);
    }

    @Test
    @DisplayName("Delete category by id should call repository")
    void deleteByIdShouldCallRepository() {
        Long id = 1L;
        categoryService.deleteById(id);
        Mockito.verify(categoryRepository).deleteById(id);
    }

    private Category createCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("test");
        category.setDeleted(false);
        return category;
    }

    private CategoryDto createCategoryDto() {
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setName("test");
        return dto;
    }

    private CreateCategoryRequestDto createCategoryRequestDto() {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto();
        dto.setName("test");
        return dto;
    }

    private void assertCategoryDtoEquals(CategoryDto expected, CategoryDto actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
    }

}
