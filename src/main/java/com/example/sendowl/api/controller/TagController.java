package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.TagService;
import com.example.sendowl.domain.tag.dto.TagDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.sendowl.domain.tag.dto.TagDto.TagsCountRes;
import static com.example.sendowl.domain.tag.dto.TagDto.TagsRes;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/tags")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "태그 목록 조회", description = "태그의 리스트만 반환한다.")
    @GetMapping(path = "") // 태그 목록
    public ResponseEntity<?> tags() {
        List<TagsRes> tagsRes = tagService.getTagList();
        return new ResponseEntity(tagsRes, HttpStatus.OK);
    }

    @Operation(summary = "태그의 카운트(인기도-게시글개수)순으로 조회", description = "태그의 카운트(인기도-게시글개수)순으로 반환한다. 조인을 하기 때문에 일반 리스트 반환보다 조금 느림")
    @GetMapping(path = "/popular") // 태그 목록
    public ResponseEntity<?> tagsCount() {
        List<TagsCountRes> tagsCountRes = tagService.getTagWithBoardCountList();
        return new ResponseEntity(tagsCountRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "태그 삽입", description = "새로운 태그를 삽입한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping(path = "") // 태그 저장
    public ResponseEntity<?> insertTag(final @Valid @RequestBody TagDto.TagInsertReq rq) {
        TagsRes tagsRes = tagService.insertTag(rq);
        return new ResponseEntity(tagsRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "태그 변경", description = "태그의 이름을 변경한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping(path = "") // 태그 변경
    public ResponseEntity<?> updateTag(final @Valid @RequestBody TagDto.TagUpdateReq rq) {
        TagsRes tagsRes = tagService.updateTag(rq);
        return new ResponseEntity(tagsRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "태그 삭제", description = "태그를 삭제한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping(path = "") // 태그 삭제
    public ResponseEntity<?> deleteTag(final @Valid @RequestBody TagDto.TagDeleteReq rq) {
        tagService.deleteTag(rq);
        return new ResponseEntity(HttpStatus.OK);
    }
}
