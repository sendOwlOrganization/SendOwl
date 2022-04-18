package com.example.sendowl.api.controller;


import com.example.sendowl.domain.comment.dto.CommentRequest;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.api.service.CommentService;
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
    @Operation(summary = "comment insert api", description = "board insert api")
    @PostMapping(path = "/comment")
    public ResponseEntity<Comment> insertComment(@RequestBody CommentRequest vo){
        commentService.insertComment(vo);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // 대댓글 등록
    @Operation(summary = "nested comment insert api", description = "board insert api")
    @PostMapping(path = "/comment/nest")
    public ResponseEntity<Comment> insertNestedComment(@RequestBody CommentRequest vo){
        commentService.insertNestedComment(vo);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
    // 댓글 목록
    @Operation(summary = "comment list api", description = "list api")
    @GetMapping(path = "/comment/list")
    public ResponseEntity<List<Comment>> getCommentList(@RequestParam("board-id") Long boardId){
        List<Comment> comments = commentService.selectCommentList(boardId);
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }
/*
    // 게시글 상세
    @Operation(summary = "board api", description = "board api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 201, message = "CREATED !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping(path = "/list/{id}") // join
    public ResponseEntity<BoardResponse> boardDetail(
            @PathVariable Long id
    ){
        System.err.println(id);

        Board boardDetail = boardService.getBoard(id);

        BoardResponse boardResponse = BoardResponse.builder()
                .title(boardDetail.getTitle())
                .content(boardDetail.getContent())
                .id(boardDetail.getId() + "")
                .build();

        return new ResponseEntity<BoardResponse>(boardResponse, HttpStatus.OK);
    }
*/


}
