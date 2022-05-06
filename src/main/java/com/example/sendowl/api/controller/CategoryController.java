package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.BoardService;
import com.example.sendowl.api.service.CategoryService;
import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.domain.board.dto.BoardDto;
import com.example.sendowl.domain.category.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.sendowl.domain.category.dto.CategoryDto.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "list api", description = "list api")
    @GetMapping(path = "") // 카테고리 목록
    public BaseResponseDto<List<CategoriesRes>> categories() {
        return new BaseResponseDto<>(categoryService.getCategoryList());
    }

    @Operation(summary = "insert category api", description = "insert category api")
    @PostMapping(path = "") // 카테고리 저장
    public BaseResponseDto<CategoriesRes> insertCategory(final @Valid @RequestBody CategoryInsertReq rq) {
        return new BaseResponseDto<>(categoryService.insertCategory(rq));
    }

    @Operation(summary = "update category api", description = "update category api")
    @PutMapping(path = "") // 카테고리 변경
    public BaseResponseDto<CategoriesRes> updateCategory(final @Valid @RequestBody CategoryUpdateReq rq) {
        return new BaseResponseDto<>(categoryService.updateCategory(rq));
    }

    @Operation(summary = "update category api", description = "update category api")
    @DeleteMapping(path = "") // 카테고리 삭제
    public void updateCategory(final @Valid @RequestBody CategoryDeleteReq rq) {
        categoryService.deleteCategory(rq);
    }
}
