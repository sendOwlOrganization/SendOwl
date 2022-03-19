package com.example.sendowl.controller;


import com.example.sendowl.dto.BoardRequest;
import com.example.sendowl.dto.MemberRequest;
import com.example.sendowl.dto.MemberResponse;
import com.example.sendowl.entity.Board;
import com.example.sendowl.entity.Member;
import com.example.sendowl.service.BoardService;
import com.example.sendowl.service.MemberService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/board")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "board api", description = "board api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 201, message = "CREATED !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    // 게시글 목록
    @GetMapping(path = "/list") // join
    public ResponseEntity<List<Board>> board(
            ){
        List<Board> boardList = boardService.getBoardList();

        return new ResponseEntity<List<Board>>(boardList, HttpStatus.CREATED);
    }

    // 게시글 등록
    @PostMapping(path = "/board")
    public void insertBoard(@RequestBody BoardRequest vo){
        Board board = Board.builder()
                        .title(vo.getTitle())
                                .content(vo.getContent())
                                        .regId(vo.getRegId()).build();

        boardService.insertBoard(board);
    }

    // JUST Test!! Insomnia Test
    //@GetMapping(path = "/{board}") // join
    //public ResponseEntity getBoard(
    //        @PathVariable String board // 파라미터로 값 바로 받는 @Anno
    //){
    //    //List<Board> boardList = boardService.getBoardList();

    //    return new ResponseEntity(board, HttpStatus.CREATED);
    //}
}
