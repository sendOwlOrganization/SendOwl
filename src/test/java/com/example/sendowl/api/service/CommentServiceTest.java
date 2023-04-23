package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.comment.dto.CommentDto;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.comment.repository.CommentRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.util.mail.JwtUserParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.sendowl.domain.comment.dto.CommentDto.CommentReq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    private final Long BOARD_ID = 1L;
    private final Long COMMENT_ID = 1L;
    private final Long PARENT_ID = 1L;
    private final String CONTENT = "content";
    @InjectMocks
    CommentService commentService;
    @Mock
    JwtUserParser jwtUserParser;
    @Mock
    BoardRepository boardRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    ExpService expService;
    User user;
    Board board;
    Comment comment;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L).name("a1").email("a1@naver.com").build();
        board = Board.builder().id(BOARD_ID).build();
        comment = Comment.builder().id(COMMENT_ID)
                .user(user).content("comment-content").build();
    }

    @Test
    void when_insertCommentWithWrongBoardId_then_boardNotFoundException() {
        // given
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        CommentReq req = new CommentReq();
        // when
        // then
        assertThrows(BoardNotFoundException.class, () -> {
            commentService.insertComment(req);
        });
    }

    @Test
    void when_insertCommentParentIdNull_then_commentRes() {
        // given
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(commentRepository.save(any())).thenReturn(comment);
        CommentReq req = new CommentReq();
        // when
        CommentDto.CommentRes commentRes = commentService.insertComment(req);
        //then
        assertEquals(commentRes.getId(), comment.getId());
    }

    @Test
    void when_insertComment_then_commentRes() {
        // given
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(commentRepository.save(any())).thenReturn(comment);
        CommentReq req = new CommentReq();
        req.setParentId(PARENT_ID);
        // when
        CommentDto.CommentRes commentRes = commentService.insertComment(req);
        //then
        assertEquals(commentRes.getId(), comment.getId());
    }

    @Test
    void when_selectCommentListWithWrongBoardId_then_boardNotFoundException() {
        // given
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Pageable pageable = PageRequest.of(0, 10);
        // when
        // then
        Assertions.assertThrows(BoardNotFoundException.class, () -> {
            commentService.selectCommentList(BOARD_ID, pageable);
        });
    }

    @Test
    void when_selectCommentListWithNoBoard_then_boardNotFoundException() {
        // given
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(commentRepository.findAllByBoard(any(), any())).thenReturn(Optional.ofNullable(null));
        Pageable pageable = PageRequest.of(0, 10);
        // when
        // then
        Assertions.assertThrows(BoardNotFoundException.class, () -> {
            commentService.selectCommentList(BOARD_ID, pageable);
        });
    }

    @Test
    @Disabled
    void when_selectCommentList_then_() {
        // given
        List<Comment> comments = new ArrayList();
        comments.add(comment);
        Page<Comment> pageComment = new PageImpl<>(comments);
        Pageable pageable = PageRequest.of(0, 10);

        int pageNumber = pageComment.getPageable().getPageNumber();
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(commentRepository.findAllByBoard(any(), any())).thenReturn(Optional.ofNullable(pageComment));
        // when
        Page<CommentDto.CommentRes> commentRes = commentService.selectCommentList(BOARD_ID, pageable);
        // then

    }
}