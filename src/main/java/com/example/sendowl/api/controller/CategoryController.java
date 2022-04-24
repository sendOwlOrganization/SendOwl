package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.BoardService;
import com.example.sendowl.api.service.CategoryService;
import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.domain.category.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.sendowl.domain.category.dto.CategoryDto.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "list api", description = "list api")
    @GetMapping(path = "") // 게시글 목록
    public BaseResponseDto<List<CategoriesRes>> categories() {
        return new BaseResponseDto<>(categoryService.getCategoryList());
    }
}
