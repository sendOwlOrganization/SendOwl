package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.sendowl.domain.category.dto.CategoryDto.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록 조회", description = "카테고리의 리스트만 반환한다.")
    @GetMapping(path = "") // 카테고리 목록
    public ResponseEntity<?> categories() {
        List<CategoriesRes> categoriesRes = categoryService.getCategoryList();
        return new ResponseEntity(categoriesRes, HttpStatus.OK);
    }
    @Operation(summary = "카테고리의 카운트(인기도)순으로 조회", description = "카테고리의 카운트(인기도)순으로 반환한다. 조인을 하기 때문에 일반 리스트 반환보다 조금 느림")
    @GetMapping(path = "/popular") // 카테고리 목록
    public ResponseEntity<?> categoriesCount() {
        List<CategoriesCountRes> categoriesRes = categoryService.getCategoryCountList();
        return new ResponseEntity(categoriesRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "카테고리 삽입", description = "새로운 카테고리를 삽입한다.")
    @PostMapping(path = "") // 카테고리 저장
    public ResponseEntity<?> insertCategory(final @Valid @RequestBody CategoryInsertReq rq) {
        CategoriesRes categoriesRes = categoryService.insertCategory(rq);

        return new ResponseEntity(categoriesRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "카테고리 변경", description = "카테고리의 이름을 변경한다.")
    @PutMapping(path = "") // 카테고리 변경
    public ResponseEntity<?> updateCategory(final @Valid @RequestBody CategoryUpdateReq rq) {
        CategoriesRes categoriesRes = categoryService.updateCategory(rq);

        return new ResponseEntity(categoriesRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "카테고리 소프트 수정", description = "카테고리를 소프트 삭제한다.")
    @DeleteMapping(path = "") // 카테고리 삭제
    public ResponseEntity<?> updateCategory(final @Valid @RequestBody CategoryDeleteReq rq) {
        categoryService.deleteCategory(rq);

        return new ResponseEntity(HttpStatus.OK);
    }
}
