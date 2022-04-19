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
@RequestMapping(path = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "comment insert api", description = "board insert api")
    @PostMapping(path = "") // 댓글 등록
    public ResponseEntity<Comment> insertComment(@RequestBody CommentRequest vo){
        commentService.insertComment(vo);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @Operation(summary = "nested comment insert api", description = "board insert api")
    @PostMapping(path = "/nest") // 대댓글 등록
    public ResponseEntity<Comment> insertNestedComment(@RequestBody CommentRequest vo){
        commentService.insertNestedComment(vo);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @Operation(summary = "comment list api", description = "list api")
    @GetMapping(path = "") // 댓글 목록
    public ResponseEntity<List<Comment>> getCommentList(@RequestParam("board-id") Long boardId){
        List<Comment> comments = commentService.selectCommentList(boardId);
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

}
