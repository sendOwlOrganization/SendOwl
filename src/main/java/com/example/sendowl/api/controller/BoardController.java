package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.sendowl.domain.board.dto.BoardDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "미리보기 게시글 목록 조회", description = "조건에 따라 미리보기 게시글을 조회한다.")
    @Parameters({
            @Parameter(name = "categoryId", example = "1", description = "게시글의 카테고리를 설정"),
            @Parameter(name = "titleLength", example = "10", description = "게시글 제목의 글자수 제한 설정"),
            @Parameter(name = "pageable", example = "{\"page\": 0, \"size\": 10, \"sort\": [\"reg_date,DESC\"]}"
                    , description = "페이지네이션을 위한 옵션")
    })
    @GetMapping(path = "/preview") // 게시글 목록
    public ResponseEntity<?> getPreviewBoards(
            final @RequestParam Long categoryId,
            final @RequestParam Integer titleLength,
            @PageableDefault(
                    size = 20,
                    sort = "board_id",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        List<PreviewBoardRes> boardsRes = boardService.getPreviewBoardList(categoryId, titleLength, pageable);
        return new ResponseEntity(boardsRes, HttpStatus.OK);
    }

    @Operation(summary = "게시글 목록 조회", description = "조건에 따라 게시글을 조회한다." +
            "http://localhost:8080/api/boards?categoryId=0&page=0&size=1&sort=id,DESC")
    @Parameters({
            @Parameter(name = "categoryId", example = "1", description = "게시글의 카테고리 id"),
            @Parameter(name = "textLength", example = "10", description = "게시글 제목의 글자수 제한"),
            @Parameter(name = "pageable", example = "{\"page\": 0, \"size\": 10, \"sort\": [\"reg_date,DESC\"]}"
                    , description = "페이지네이션을 위한 옵션")
    })
    @GetMapping(path = "") // 게시글 목록
    public ResponseEntity<?> boards(
            final @RequestParam Long categoryId,
            final @RequestParam Integer textLength,
            @PageableDefault(
                    size = 20,
                    sort = "board_id",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        BoardsRes boardsRes = boardService.getBoardList(categoryId, textLength, pageable);

        return new ResponseEntity(boardsRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "게시글 삽입", description = "새로운 게시글을 삽입한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping(path = "", produces = "application/json; charset=utf8") // 게시글 등록
    public ResponseEntity<?> board(final @Valid @RequestBody BoardReq boardReq) {

        DetailRes detailRes = boardService.insertBoard(boardReq);

        return new ResponseEntity(detailRes, HttpStatus.OK);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회한다.")
    @Parameters({
            @Parameter(name = "boardId", example = "1", description = "게시글의 id"),
    })
    @GetMapping(path = "/{boardId}") // 게시글 상세
    public ResponseEntity<?> boardDetail(@PathVariable Long boardId) {
        DetailRes detailRes = boardService.getBoardDetail(boardId);

        return new ResponseEntity(detailRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "게시글 수정한다", description = "게시글을 수정한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping(path = "") // 게시글 수정
    public ResponseEntity<?> boardUpdate(final @Valid @RequestBody UpdateBoardReq req) {
        UpdateBoardRes updatedRes = boardService.updateBoard(req);

        return new ResponseEntity(updatedRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "게시글 소프트 삭제", description = "게시글을 소프트 삭제한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @Parameters({
            @Parameter(name = "boardId", example = "1", description = "게시글의 id"),
    })
    @DeleteMapping(path = "/{boardId}") // 게시글 삭제
    public ResponseEntity<?> boardDelete(@PathVariable Long boardId) {

        boardService.deleteBoard(boardId);

        return new ResponseEntity(HttpStatus.OK);
    }

}
