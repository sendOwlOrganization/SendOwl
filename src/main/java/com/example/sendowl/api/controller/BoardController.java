package com.example.sendowl.api.controller;


import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.api.service.BoardService;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.user.dto.UserDto;
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
    @GetMapping(path = "") // 게시글 목록
    public BaseResponseDto<List<BoardsRes>> boards(){
        return new BaseResponseDto<List<BoardsRes>>(boardService.getBoardList());
    }

    @Operation(summary = "board insert api", description = "board insert api")
    @PostMapping(path = "/board") // 게시글 등록 BaseResponseDto<BoardRes>
    public BaseResponseDto<BoardsRes> board(final @Valid @RequestBody BoardReq rq){

        return new BaseResponseDto<BoardsRes>(boardService.board(rq));
    }

    @Operation(summary = "board api", description = "board api")
    @GetMapping(path = "/{id}") // 게시글 상세
    public BaseResponseDto<DetailRes> boardDetail(@PathVariable Long id ){
        return new BaseResponseDto<DetailRes>(boardService.boardDetail(id));
    }

}
