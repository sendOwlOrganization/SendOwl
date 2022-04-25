package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.dto.BoardDto;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.category.dto.CategoryDto;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.sendowl.domain.category.dto.CategoryDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoriesRes> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoriesRes::new).collect(Collectors.toList());
    }
}
