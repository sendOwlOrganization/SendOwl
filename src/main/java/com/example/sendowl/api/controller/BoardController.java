package com.example.sendowl.api.controller;


import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.api.service.BoardService;
import com.example.sendowl.domain.board.entity.Board;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "list api", description = "http://localhost:8080/api/boards?page=0&size=10&sort=id,DESC")
    @GetMapping(path = "") // 게시글 목록
    public ResponseEntity<?> boards(Pageable pageable){
        BoardsRes boardsRes = boardService.getBoardList(pageable);
        return new ResponseEntity(boardsRes, HttpStatus.OK);
    }

    @Operation(summary = "board insert api", description = "board insert api")
    @PostMapping(path = "") // 게시글 등록
    public ResponseEntity<?> board(final @Valid @RequestBody BoardReq rq){

        DetailRes detailRes = boardService.insertBoard(rq);

        return new ResponseEntity(detailRes, HttpStatus.OK);
    }

    @Operation(summary = "board api", description = "board api")
    @GetMapping(path = "/{id}") // 게시글 상세
    public ResponseEntity<?> boardDetail(@PathVariable Long id){
        DetailRes detailRes = boardService.boardDetail(id);

        return new ResponseEntity(detailRes, HttpStatus.OK);
    }

    @Operation(summary = "board update api", description = "board update api")
    @PutMapping(path = "") // 게시글 수정
    public ResponseEntity<?> boardUpdate(final @Valid @RequestBody UpdateReq req){
        UpdateRes updatedRes = boardService.updateBoard(req);

        return new ResponseEntity(updatedRes, HttpStatus.OK);
    }

    @Operation(summary = "board delete api", description = "board delete api")
    @DeleteMapping(path = "/{id}") // 게시글 삭제
    public ResponseEntity<?> boardDelete(@PathVariable Long id){
        boardService.deleteBoard(id);

        return new ResponseEntity(HttpStatus.OK);
    }

}
