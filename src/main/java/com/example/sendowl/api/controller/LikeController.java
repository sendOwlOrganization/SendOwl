package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.LikeService;
import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.domain.like.dto.LikeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/like")
public class LikeController {

    final private LikeService likeService;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "게시글 좋아요",description = "게시글 좋아요", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping("/board")
    public ResponseEntity<LikeDto.BoardLikeResponse> boardLike(final @Valid @RequestBody LikeDto.BoardLikeRequest req) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(likeService.setBoardLike(req, principal.getUser()), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "게시글 좋아요 취소",description = "게시글 좋아요 취소", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/board")
    public void boardUnLike(final @Valid @RequestBody LikeDto.BoardUnLikeRequest req) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        likeService.setBoardUnLike(req, principal.getUser());
    }


    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "댓글 좋아요",description = "댓글 좋아요", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping("/comment")
    public ResponseEntity<LikeDto.CommentLikeResponse> commentLike(final @Valid @RequestBody LikeDto.CommentLikeRequest req) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity(likeService.setCommentLike(req, principal.getUser()), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "댓글 좋아요 취소",description = "댓글 좋아요 취소", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/comment")
    public void commentUnLike(final @Valid @RequestBody LikeDto.CommentUnLikeRequest req) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        likeService.setCommentUnlike(req, principal.getUser());
    }
}
