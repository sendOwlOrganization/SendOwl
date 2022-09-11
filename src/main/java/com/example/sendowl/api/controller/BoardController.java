package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.sendowl.domain.board.dto.BoardDto.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시글 목록 조회", description = "조건에 따라 게시글을 조회한다." +
            "http://localhost:8080/api/boards?categoryId=0&page=0&size=1&sort=id,DESC")
    @GetMapping(path = "") // 게시글 목록
    public ResponseEntity<?> boards(final @RequestParam Long categoryId, Pageable pageable){
        BoardsRes boardsRes = boardService.getBoardList(categoryId, pageable);

        return new ResponseEntity(boardsRes, HttpStatus.OK);
    }

    @Operation(summary = "게시글 삽입", description = "새로운 게시글을 삽입한다.")
    @PostMapping(path = "", produces = "application/json; charset=utf8") // 게시글 등록
    public @ResponseBody ResponseEntity<?> board(final @Valid @RequestBody BoardReq rq){

        DetailRes detailRes = boardService.insertBoard(rq);

        return new ResponseEntity(detailRes, HttpStatus.OK);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회한다.")
    @GetMapping(path = "/{id}") // 게시글 상세
    public ResponseEntity<?> boardDetail(@PathVariable Long id){
        DetailRes detailRes = boardService.boardDetail(id);

        return new ResponseEntity(detailRes, HttpStatus.OK);
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정한다.")
    @PutMapping(path = "") // 게시글 수정
    public ResponseEntity<?> boardUpdate(final @Valid @RequestBody UpdateReq req){
        UpdateRes updatedRes = boardService.updateBoard(req);

        return new ResponseEntity(updatedRes, HttpStatus.OK);
    }

    @Operation(summary = "게시글 소프트 삭제", description = "게시글을 소프트 삭제한다.")
    @DeleteMapping(path = "/{id}") // 게시글 삭제
    public ResponseEntity<?> boardDelete(@PathVariable Long id){
        boardService.deleteBoard(id);

        return new ResponseEntity(HttpStatus.OK);
    }

}
