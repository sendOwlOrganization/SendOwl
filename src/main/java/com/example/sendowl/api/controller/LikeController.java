package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.sendowl.domain.like.dto.LikeDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/like")
public class LikeController {

    final private LikeService likeService;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "게시글 좋아요", description = "게시글 좋아요", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("/board")
    public ResponseEntity<BoardLikeResponse> boardLike(final @Valid @RequestBody BoardLikeRequest req) {
        return new ResponseEntity(likeService.setBoardLike(req), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "게시글 좋아요 취소", description = "게시글 좋아요 취소", security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Boolean> boardUnLike(final @PathVariable("boardId") Long boardId) {
        return new ResponseEntity(likeService.setBoardUnLike(boardId), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "댓글 좋아요", description = "댓글 좋아요", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("/comment")
    public ResponseEntity<CommentLikeResponse> commentLike(final @Valid @RequestBody CommentLikeRequest req) {
        return new ResponseEntity(likeService.setCommentLike(req), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "댓글 좋아요 취소", description = "댓글 좋아요 취소", security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Boolean> commentUnLike(final @PathVariable("commentId") Long commentId) {
        return new ResponseEntity(likeService.setCommentUnlike(commentId), HttpStatus.OK);
    }
}
