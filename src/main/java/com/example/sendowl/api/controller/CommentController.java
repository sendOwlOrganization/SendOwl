package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.sendowl.domain.comment.dto.CommentDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 삽입", description = "댓글을 삽입한다.")
    @PostMapping(path = "") // 댓글 등록
    public ResponseEntity<?> insertComment(@RequestBody CommentReq vo){
        CommentRes commentRes = this.commentService.insertComment(vo);

        return new ResponseEntity(commentRes, HttpStatus.OK);
    }

    @Operation(summary = "댓글 목록 조회", description = "게시글의 id를 통해 댓글 목록을 가져온다.")
    @GetMapping(path = "/{boardId}") // 댓글 목록
    public ResponseEntity<?> getCommentList(@RequestParam("board-id") Long boardId){
        List<CommentRes> commentResList = commentService.selectCommentList(boardId);

        return new ResponseEntity(commentResList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 내용 수정", description = "댓글의 내용을 수정한다.")
    @PutMapping(path = "") // 댓글 내용 수정
    public ResponseEntity<?> updateComment(UpdateReq crq){
        CommentRes commentRes = commentService.updateComment(crq);

        return new ResponseEntity(commentRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 소프트 삭제", description = "댓글을 소프트 삭제한다.")
    @DeleteMapping(path = "{commentId}") // 댓글 삭제
    public ResponseEntity<?> deleteComment(@RequestParam("comment-id") Long commentId){
        commentService.deleteComment(commentId);

        return new ResponseEntity(HttpStatus.OK);
    }


}
