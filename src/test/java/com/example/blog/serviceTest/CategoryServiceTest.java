package com.example.blog.serviceTest;

import com.example.blog.model.Category;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Test
    public void testFindAll() {
        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        CategoryService categoryService = new CategoryService(categoryRepository);

        Category category1 = new Category();
        category1.setCategoryId(1L);
        category1.setCategoryName("Technology");
        Category category2 = new Category();
        category2.setCategoryId(2L);
        category2.setCategoryName("Science");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categories = categoryService.findAll();
        assertEquals(2, categories.size());
        assertEquals(category1, categories.get(0));
        assertEquals(category2, categories.get(1));
    }

    @Test
    public void testSave() {
        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        CategoryService categoryService = new CategoryService(categoryRepository);

        Category categoryToSave = new Category();
        categoryToSave.setCategoryName("Travel");

        when(categoryRepository.save(categoryToSave)).thenReturn(new Category(1L, "Travel"));

        Category savedCategory = categoryService.save(categoryToSave);
        assertEquals(1L, savedCategory.getCategoryId());
        assertEquals("Travel", savedCategory.getCategoryName());
    }
}