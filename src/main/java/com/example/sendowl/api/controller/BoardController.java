package com.example.sendowl.api.controller;


import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.api.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.sendowl.domain.board.dto.BoardDto.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "list api", description = "list api")
    @GetMapping(path = "/") // 게시글 목록
    public BaseResponseDto<List<BoardRes>> boards(){
        return new BaseResponseDto<List<BoardRes>>(boardService.getBoardList());
    }

    @Operation(summary = "board api", description = "board api")
    @GetMapping(path = "/{id}") // 게시글 상세
    public BaseResponseDto<BoardRes> boardDetail(@PathVariable Long id ){
        return new BaseResponseDto<BoardRes>(boardService.getBoard(id));
    }

    @Operation(summary = "board insert api", description = "board insert api")
    @PostMapping(path = "/board") // 게시글 등록
    public BaseResponseDto<BoardRes> insertBoard(final @Valid @RequestBody BoardReq rq){
        return new BaseResponseDto<>(boardService.insertBoard(rq));
    }

}
