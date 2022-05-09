package com.example.sendowl.api.controller;


import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.api.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.example.sendowl.domain.comment.dto.CommentDto.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "comment insert api", description = "comment insert api")
    @PostMapping(path = "") // 댓글 등록
    public ResponseEntity<?> insertComment(@RequestBody CommentReq vo){
        Optional<Comment> comment = this.commentService.insertComment(vo);

        if(comment.isPresent()){
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "comment list api", description = "list api")
    @GetMapping(path = "/{boardId}") // 댓글 목록
    public ResponseEntity<?> getCommentList(@RequestParam("board-id") Long boardId){
        List<CommentRes> commentResList = commentService.selectCommentList(boardId);

        return ResponseEntity.ok(commentResList);
    }

    @Operation(summary = "comment delete api", description = "delete api")
    @PutMapping(path = "") // 댓글 내용 수정
    public ResponseEntity<?> updateComment(updCommentReq crq){
        Optional<Comment> comment = this.commentService.updateComment(crq);

        if(comment.isPresent()){
            return ResponseEntity.ok(comment);
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "comment delete api", description = "delete api")
    @DeleteMapping(path = "{commentId}") // 댓글 삭제
    public ResponseEntity<?> deleteComment(@RequestParam("comment-id") Long commentId){
        Optional<Comment> comment = this.commentService.deleteComment(commentId);
        
        if(comment.isPresent()){
            return ResponseEntity.ok(comment);
        }

        return ResponseEntity.badRequest().build();
    }


}
