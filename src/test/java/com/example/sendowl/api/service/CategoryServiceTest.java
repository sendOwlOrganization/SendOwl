package com.example.sendowl.api.service;

import com.example.sendowl.domain.category.dto.CategoryCount;
import com.example.sendowl.domain.category.dto.CategoryDto;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.exception.CategoryNameAlreadyExistsException;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    private final Long CATEGORY_ID = 1L;
    private final Long CATEGORY_ID2 = 2L;
    private final String CATEGORY_NAME1 = "자유게시판";
    private final String CATEGORY_NAME2 = "기타";
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    private Category category;
    private Category category2;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME1).build();
        category2 = Category.builder()
                .id(CATEGORY_ID2)
                .name(CATEGORY_NAME2).build();
    }

    @Test
    void when_insertCategoryWithAlreadyExist_then_categoryNameAlreadyExistsException() {
        // given
        CategoryDto.CategoryInsertReq req = new CategoryDto.CategoryInsertReq();
        when(categoryRepository.existsByName(any())).thenReturn(true);
        // when
        // then
        Assertions.assertThrows(CategoryNameAlreadyExistsException.class, () -> {
            categoryService.insertCategory(req);
        });
    }

    @Test
    void when_insertCategory_then_categoryNameAlreadyExistsException() {
        // given
        CategoryDto.CategoryInsertReq req = new CategoryDto.CategoryInsertReq();
        when(categoryRepository.existsByName(any())).thenReturn(false);
        when(categoryRepository.save(any())).thenReturn(category);
        // when
        CategoryDto.CategoriesRes categoriesRes = categoryService.insertCategory(req);
        // then
        assertEquals(categoriesRes.getName(), category.getName());
    }

    @Test
    void when_getCategoryListEmpty_then_getCategoryList() {
        // given
        List<Category> categories = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(categories);
        // when
        List<CategoryDto.CategoriesRes> categoryList = categoryService.getCategoryList();
        // then
        assertEquals(categoryList.size(), 0);
    }

    @Test
    void when_getCategoryList_then_getCategoryList() {
        // given
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        categories.add(category2);
        when(categoryRepository.findAll()).thenReturn(categories);
        // when
        List<CategoryDto.CategoriesRes> categoryList = categoryService.getCategoryList();
        // then
        assertEquals(categoryList.size(), 2);
        assertEquals(categoryList.get(0).getId(), category.getId());
    }

    @Test
    void when_getCategoryWithBoardCountListWith_then_getCategoryList() {
        // given
        List<CategoryCount> categories = new ArrayList<>();
        when(categoryRepository.findCategoriesWithBoardCount()).thenReturn(categories);
        // when
        List<CategoryDto.CategoriesCountRes> categoryCountList = categoryService.getCategoryWithBoardCountList();
        // then
        assertEquals(categoryCountList.size(), 0);
    }

    @Test
    void when_getCategoryWithBoardCountListWithEmpty_then_getCategoryList() {
        // given
        List<CategoryCount> categories = new ArrayList<>();
        categories.add(new CategoryCount(category.getId(), category.getName(), 1L));
        categories.add(new CategoryCount(category2.getId(), category2.getName(), 2L));
        when(categoryRepository.findCategoriesWithBoardCount()).thenReturn(categories);
        // when
        List<CategoryDto.CategoriesCountRes> categoryCountList = categoryService.getCategoryWithBoardCountList();
        // then
        assertEquals(categoryCountList.size(), 2);
        assertEquals(categoryCountList.get(0).getId(), categories.get(0).getCategoryId());
    }

    @Test
    void when_updateCategoryEmpty_then_CategoryNotFoundException() {
        // given
        CategoryDto.CategoryUpdateReq req = new CategoryDto.CategoryUpdateReq();
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // when
        // then
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.updateCategory(req);
        });
    }

    @Test
    void when_updateCategory_then_getCategoryRes() {
        // given
        CategoryDto.CategoryUpdateReq req = new CategoryDto.CategoryUpdateReq(category2.getId(), category2.getName());
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));
        when(categoryRepository.save(any())).thenReturn(category2);
        // when
        CategoryDto.CategoriesRes categoriesRes = categoryService.updateCategory(req);
        // then
        assertEquals(categoriesRes.getId(), category2.getId());
        assertEquals(categoriesRes.getName(), category2.getName());
    }

    @Test
    void when_deleteCategory_then_void() {
        // given
        CategoryDto.CategoryDeleteReq req = new CategoryDto.CategoryDeleteReq();
        // when
        categoryService.deleteCategory(req);
        // then
    }
}