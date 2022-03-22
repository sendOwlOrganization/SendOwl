package com.example.sendowl.controller;


import com.example.sendowl.dto.BoardRequest;
import com.example.sendowl.dto.BoardResponse;
import com.example.sendowl.dto.CommentRequest;
import com.example.sendowl.entity.Board;
import com.example.sendowl.entity.Comment;
import com.example.sendowl.service.BoardService;
import com.example.sendowl.service.CommentService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @Operation(summary = "board insert api", description = "board insert api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 201, message = "CREATED !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PostMapping(path = "/comment")
    public ResponseEntity<Comment> insertComment(@RequestBody CommentRequest vo){
        commentService.insertComment(vo);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // 대댓글 등록
    @Operation(summary = "board insert api", description = "board insert api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 201, message = "CREATED !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PostMapping(path = "/comment/nest")
    public ResponseEntity<Comment> insertNestedComment(@RequestBody CommentRequest vo){
        commentService.insertNestedComment(vo);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
//    // 게시글 목록
//    @Operation(summary = "list api", description = "list api")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "OK !!"),
//            @ApiResponse(code = 201, message = "CREATED !!"),
//            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
//            @ApiResponse(code = 404, message = "NOT FOUND !!"),
//            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
//    })
//    @GetMapping(path = "/list") // join
//    public ResponseEntity<List<Board>> list(
//            ){
//        List<Board> boardList = boardService.getBoardList();
//
//        return new ResponseEntity<List<Board>>(boardList, HttpStatus.OK);
//    }
//
//    // 게시글 상세
//    @Operation(summary = "board api", description = "board api")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "OK !!"),
//            @ApiResponse(code = 201, message = "CREATED !!"),
//            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
//            @ApiResponse(code = 404, message = "NOT FOUND !!"),
//            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
//    })
//    @GetMapping(path = "/list/{id}") // join
//    public ResponseEntity<BoardResponse> boardDetail(
//            @PathVariable Long id
//    ){
//        System.err.println(id);
//
//        Board boardDetail = boardService.getBoard(id);
//
//        BoardResponse boardResponse = BoardResponse.builder()
//                .title(boardDetail.getTitle())
//                .content(boardDetail.getContent())
//                .id(boardDetail.getId() + "")
//                .build();
//
//        return new ResponseEntity<BoardResponse>(boardResponse, HttpStatus.OK);
//    }

    

}
