package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.CommentService;
import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.example.sendowl.domain.comment.dto.CommentDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 삽입", description = "댓글을 삽입한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping(path = "") // 댓글 등록
    public ResponseEntity<?> insertComment(@RequestBody CommentReq vo) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();
        CommentRes commentRes = this.commentService.insertComment(vo, user);

        return new ResponseEntity(commentRes, HttpStatus.OK);
    }

    @Operation(summary = "댓글 목록 조회", description = "게시글의 id를 통해 댓글 목록을 가져온다.")
    @Parameters({
            @Parameter(name = "boardId", required = true, description = "게시글 id", example = "1"),
            @Parameter(name = "pageable", required = false, description = "example: {\"page\": 0,\"size\": 1,\n" +
                    "  \"sort\": [\"regDate,desc\"]\n" + "}")
    })
    @GetMapping(path = "") // 댓글 목록
    public ResponseEntity<?> getCommentList(@RequestParam(name = "boardId") Long boardId,
                                            @PageableDefault(sort = "regDate", direction = Sort.Direction.DESC, size = 10, page = 0) Pageable pageable) {
        Page<CommentRes> commentResList = commentService.selectCommentList(boardId, pageable);

        return new ResponseEntity(commentResList, HttpStatus.OK);
    }

    @Operation(summary = "베스트 댓글 목록 조회", description = "게시글의 id를 통해 베스트 댓글 목록을 가져온다.")
    @Parameters(@Parameter(name = "size", required = true, description = "example: 5"))
    @GetMapping(path = "/best") // 댓글 목록
    public ResponseEntity<?> getCommentList(@RequestParam(name="boardId") Long boardId,
                                            @RequestParam(name="size") Long size){
        List<CommentRes> commentResList = commentService.selectBestCommentList(boardId, size);

        return new ResponseEntity(commentResList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 내용 수정", description = "댓글의 내용을 수정한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping(path = "") // 댓글 내용 수정
    public ResponseEntity<?> updateComment(UpdateReq updateReq) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();

        CommentRes commentRes = commentService.updateComment(updateReq, user);

        return new ResponseEntity(commentRes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "댓글 소프트 삭제", description = "댓글을 소프트 삭제한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @Parameters(
            @Parameter(name = "commentId", required = true, example = "1", description = "삭제할 댓글 id")
    )
    @DeleteMapping(path = "/{commentId}") // 댓글 삭제
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();

        commentService.deleteComment(commentId, user);

        return new ResponseEntity(HttpStatus.OK);
    }
}
