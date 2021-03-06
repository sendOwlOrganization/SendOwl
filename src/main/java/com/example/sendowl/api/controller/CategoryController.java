package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.BoardService;
import com.example.sendowl.api.service.CategoryService;
import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.domain.board.dto.BoardDto;
import com.example.sendowl.domain.category.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.sendowl.domain.category.dto.CategoryDto.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리의 리스트만 반환한다.", description = "카테고리의 리스트만 반환한다.")
    @GetMapping(path = "") // 카테고리 목록
    public ResponseEntity<?> categories() {
        List<CategoriesRes> categoriesRes = categoryService.getCategoryList();
        return new ResponseEntity(categoriesRes, HttpStatus.OK);
    }
    @Operation(summary = "카테고리의 카운트(인기도)순으로 반환한다.", description = "카테고리의 카운트(인기도)순으로 반환한다. 조인을 하기 때문에 일반 리스트 반환보다 조금 느림")
    @GetMapping(path = "/popular") // 카테고리 목록
    public ResponseEntity<?> categoriesCount() {
        List<CategoriesCountRes> categoriesRes = categoryService.getCategoryCountList();
        return new ResponseEntity(categoriesRes, HttpStatus.OK);
    }


    @Operation(summary = "insert category api", description = "insert category api")
    @PostMapping(path = "") // 카테고리 저장
    public ResponseEntity<?> insertCategory(final @Valid @RequestBody CategoryInsertReq rq) {
        CategoriesRes categoriesRes = categoryService.insertCategory(rq);

        return new ResponseEntity(categoriesRes, HttpStatus.OK);
    }

    @Operation(summary = "update category api", description = "update category api")
    @PutMapping(path = "") // 카테고리 변경
    public ResponseEntity<?> updateCategory(final @Valid @RequestBody CategoryUpdateReq rq) {
        CategoriesRes categoriesRes = categoryService.updateCategory(rq);

        return new ResponseEntity(categoriesRes, HttpStatus.OK);
    }

    @Operation(summary = "update category api", description = "update category api")
    @DeleteMapping(path = "") // 카테고리 삭제
    public ResponseEntity<?> updateCategory(final @Valid @RequestBody CategoryDeleteReq rq) {
        categoryService.deleteCategory(rq);

        return new ResponseEntity(HttpStatus.OK);
    }
}
