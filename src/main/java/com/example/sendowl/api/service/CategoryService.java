package com.example.sendowl.api.service;

import com.example.sendowl.domain.category.dto.CategoryCount;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.exception.CategoryNameAlreadyExistsException;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.sendowl.domain.category.dto.CategoryDto.*;
import static com.example.sendowl.domain.category.enums.CategoryErrorCode.ALREADY_EXISTS;
import static com.example.sendowl.domain.category.enums.CategoryErrorCode.NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoriesRes> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoriesRes::new).collect(Collectors.toList());
    }
    public List<CategoriesCountRes> getCategoryCountList() {
        List<CategoryCount> categories = categoryRepository.findCategoriesWithCount();
        return categories.stream().map(CategoriesCountRes::new).collect(Collectors.toList());
    }


    @Transactional
    public CategoriesRes insertCategory(CategoryInsertReq rq) {
        if(categoryRepository.existsByName(rq.getName())){
            throw new CategoryNameAlreadyExistsException(ALREADY_EXISTS);
        }
        Category category = categoryRepository.save(rq.toEntity());
        return new CategoriesRes(category);
    }

    @Transactional
    public CategoriesRes updateCategory(CategoryUpdateReq rq) {
        Category category = categoryRepository.findById(rq.getId()).orElseThrow(()->new CategoryNotFoundException(NOT_FOUND));
        category.setName(rq.getName());
        Category savedCategory = categoryRepository.save(category);
        return new CategoriesRes(savedCategory);
    }

    @Transactional
    public void deleteCategory(CategoryDeleteReq rq) {
        categoryRepository.deleteById(rq.getId());
    }
}
