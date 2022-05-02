package com.example.sendowl.api.controller;


import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.api.service.BoardService;
import com.example.sendowl.domain.board.entity.Board;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public BaseResponseDto<BoardsRes> boards(Pageable pageable){
        return new BaseResponseDto<BoardsRes>(boardService.getBoardList(pageable));
    }

    @Operation(summary = "board insert api", description = "board insert api")
    @PostMapping(path = "/board") // 게시글 등록
    public BaseResponseDto<DetailRes> board(final @Valid @RequestBody BoardReq rq){
        return new BaseResponseDto<DetailRes>(boardService.insertBoard(rq));
    }

    @Operation(summary = "board api", description = "board api")
    @GetMapping(path = "/{id}") // 게시글 상세
    public BaseResponseDto<DetailRes> boardDetail(@PathVariable Long id){
        return new BaseResponseDto<DetailRes>(boardService.boardDetail(id));
    }

}
