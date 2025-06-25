package core.basesyntax.categorytests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.dto.category.CategoryDto;
import core.basesyntax.dto.category.CreateCategoryRequestDto;
import core.basesyntax.exception.EntityNotFoundException;
import core.basesyntax.mapper.CategoryMapper;
import core.basesyntax.model.Category;
import core.basesyntax.repository.category.CategoryRepository;
import core.basesyntax.service.impl.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        Page<Category> categoryPage = new PageImpl<>(List.of(category), pageable, 1);
        Page<CategoryDto> expectedPage = new PageImpl<>(List.of(dto), pageable, 1);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(dto);

        Page<CategoryDto> result = categoryService.findAll(pageable);

        assertEquals(expectedPage, result);
    }

    @Test
    @DisplayName("Get category by id should return correct dto")
    void findByIdShouldReturnCorrectDto() {
        Category category = createCategory();
        CategoryDto expectedDto = createCategoryDto();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        CategoryDto result = categoryService.getById(1L);

        assertEquals(expectedDto, result);
    }

    @Test
    @DisplayName("Get category by id should throw exception if not found")
    void findByIdShouldThrowIfNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(99L));
    }

    @Test
    @DisplayName("Save category should return correct dto")
    void saveCategoryShouldReturnCorrectDto() {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        Category category = createCategory();
        CategoryDto expectedDto = createCategoryDto();

        when(categoryMapper.toCategory(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        CategoryDto result = categoryService.save(requestDto);

        assertEquals(expectedDto, result);
    }

    @Test
    @DisplayName("Update category should return correct dto")
    void updateCategoryShouldReturnCorrectDto() {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        Category initCategory = createCategory();
        Category updatedCategory = createCategory();
        CategoryDto expectedDto = createCategoryDto();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(initCategory));
        doNothing().when(categoryMapper).updateCategory(initCategory, requestDto);
        when(categoryRepository.save(initCategory)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(expectedDto);

        CategoryDto result = categoryService.update(1L, requestDto);

        assertEquals(expectedDto, result);
    }

    @Test
    @DisplayName("Delete category by id should call repository")
    void deleteByIdShouldCallRepository() {
        Long id = 1L;

        categoryService.deleteById(id);

        verify(categoryRepository).deleteById(id);
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
}
