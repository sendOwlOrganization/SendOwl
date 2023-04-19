package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.comment.exception.CommentNotFoundException;
import com.example.sendowl.domain.comment.repository.CommentRepository;
import com.example.sendowl.domain.like.dto.LikeDto;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.like.entity.CommentLike;
import com.example.sendowl.domain.like.exception.LikeNoAuthException;
import com.example.sendowl.domain.like.exception.LikeNotFoundException;
import com.example.sendowl.domain.like.repository.BoardLikeRepository;
import com.example.sendowl.domain.like.repository.CommentLikeRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.util.mail.JwtUserParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    private final Long USER_ID = 1L;
    private final Long USER_ID2 = 2L;
    private final Long CATEGORY_ID = 1L;
    private final String CATEGORY_NAME = "자유게시판";
    private final Long BOARD_ID = 1L;
    private final Long BOARD_ID2 = 2L;
    private final String refinedText = "refinedText";
    private final String boardContent = "content1";
    private final Long COMMENT_ID = 1L;
    private final Long COMMENT_ID2 = 2L;
    @InjectMocks
    private LikeService likeService;
    @Mock
    private BoardLikeRepository boardLikeRepository;
    @Mock
    private CommentLikeRepository commentLikeRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private JwtUserParser jwtUserParser;
    private Board board;
    private Board board2;
    private User user;
    private User user2;
    private Category category;
    private BoardLike boardLike;
    private BoardLike boardLike2;
    private CommentLike commentLike;
    private CommentLike commentLike2;
    private Comment comment;
    private Comment comment2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(USER_ID)
                .transactionId("google")
                .name("sendowl")
                .email("a1@naver.com")
                .mbti("esfp")
                .build();
        user2 = User.builder()
                .id(USER_ID2)
                .transactionId("google")
                .name("sendowl2")
                .email("a2@naver.com")
                .mbti("estj")
                .build();
        category = Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME).build();
        board = Board.builder()
                .id(BOARD_ID)
                .user(user)
                .category(category)
                .content(boardContent)
                .refinedContent(refinedText)
                .hit(0).build();
        board2 = Board.builder()
                .id(BOARD_ID2)
                .user(user2)
                .category(category)
                .content(boardContent)
                .refinedContent(refinedText)
                .hit(0).build();
        comment = Comment.builder()
                .id(1L)
                .user(user)
                .content("comment").build();
        comment2 = Comment.builder()
                .id(2L)
                .user(user2)
                .content("comment").build();
        boardLike = BoardLike.builder().id(1L).user(user).board(board).build();
        boardLike2 = BoardLike.builder().id(2L).user(user2).board(board2).build();
        commentLike = CommentLike.builder().id(1L).user(user).comment(comment).build();
        commentLike2 = CommentLike.builder().id(2L).user(user2).comment(comment2).build();
    }

    @Test
    void when_setBoardLikeWithWrongBoardId_then_boardNotFoundException() {
        // given
        LikeDto.BoardLikeRequest req = new LikeDto.BoardLikeRequest(-1L);
        // when
        // then
        Assertions.assertThrows(BoardNotFoundException.class, () -> {
            likeService.setBoardLike(req);
        });
    }

    @Test
    void when_setBoardLike_then_boardLikeResponse() {
        // given
        LikeDto.BoardLikeRequest req = new LikeDto.BoardLikeRequest(BOARD_ID);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardLikeRepository.findByUserAndBoard(any(), any())).thenReturn(Optional.of(boardLike));
        // when
        LikeDto.BoardLikeResponse boardLikeResponse = likeService.setBoardLike(req);
        // then
        assertEquals(boardLikeResponse.getBoardId(), board.getId());
    }

    @Test
    void when_setBoardLikeWithDeletedBoardLike_then_boardLikeResponse() {
        // given
        LikeDto.BoardLikeRequest req = new LikeDto.BoardLikeRequest(BOARD_ID);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardLikeRepository.findByUserAndBoard(any(), any())).thenReturn(Optional.of(boardLike));
        boardLike.delete();
        // when
        LikeDto.BoardLikeResponse boardLikeResponse = likeService.setBoardLike(req);
        // then
        assertEquals(boardLikeResponse.getBoardId(), board.getId());
    }

    @Test
    void when_setBoardUnLikeWithWrongBoardId_then_boardNotFoundException() {
        // given
        // when
        // then
        Assertions.assertThrows(BoardNotFoundException.class, () -> {
            likeService.setBoardUnLike(-1L);
        });
    }

    @Test
    void when_setBoardUnLikeWithWrongBoardLike_then_likeNotFoundException() {
        // given
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardLikeRepository.findByUserAndBoard(any(), any())).thenReturn(Optional.ofNullable(null));
        // when
        // then
        Assertions.assertThrows(LikeNotFoundException.class, () -> {
            likeService.setBoardUnLike(BOARD_ID);
        });
    }

    @Test
    void when_setBoardUnLikeWithNoAuth_then_likeNoAuthException() {
        // given
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardLikeRepository.findByUserAndBoard(any(), any())).thenReturn(Optional.ofNullable(boardLike2));
        // when
        // then
        Assertions.assertThrows(LikeNoAuthException.class, () -> {
            likeService.setBoardUnLike(BOARD_ID2);
        });
    }

    @Test
    void when_setBoardUnLike_then_true() {
        // given
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardLikeRepository.findByUserAndBoard(any(), any())).thenReturn(Optional.ofNullable(boardLike));
        // when
        boolean res = likeService.setBoardUnLike(BOARD_ID);
        // then
        assertEquals(res, true);
    }

    @Test
    void when_setCommentLikeWithWrongId_then_commentNotFoundException() {
        // given
        LikeDto.CommentLikeRequest req = new LikeDto.CommentLikeRequest(-1L);
        // when
        // then
        Assertions.assertThrows(CommentNotFoundException.class, () -> {
            likeService.setCommentLike(req);
        });
    }

    @Test
    void when_setCommentLike_then_commentLikeResponse() {
        // given
        LikeDto.CommentLikeRequest req = new LikeDto.CommentLikeRequest(COMMENT_ID);
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(commentLikeRepository.findByUserAndComment(any(), any())).thenReturn(Optional.of(commentLike));
        // when
        LikeDto.CommentLikeResponse commentLikeResponse = likeService.setCommentLike(req);
        // then
        assertEquals(commentLikeResponse.getCommentId(), COMMENT_ID);
    }

    @Test
    void when_setCommentLikeWithDeletedCommentLike_then_commentLikeResponse() {
        // given
        LikeDto.CommentLikeRequest req = new LikeDto.CommentLikeRequest(COMMENT_ID);
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(commentLikeRepository.findByUserAndComment(any(), any())).thenReturn(Optional.of(commentLike));
        commentLike.delete();
        // when
        LikeDto.CommentLikeResponse commentLikeResponse = likeService.setCommentLike(req);
        // then
        assertEquals(commentLikeResponse.getCommentId(), COMMENT_ID);
    }

    @Test
    void when_setCommentUnLikeWithWrongBoardId_then_boardNotFoundException() {
        // given
        // when
        // then
        Assertions.assertThrows(CommentNotFoundException.class, () -> {
            likeService.setCommentUnlike(-1L);
        });
    }

    @Test
    void when_setCommentUnLikeWithWrongBoardLike_then_likeNotFoundException() {
        // given
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(commentLikeRepository.findByUserAndComment(any(), any())).thenReturn(Optional.ofNullable(null));
        // when
        // then
        Assertions.assertThrows(LikeNotFoundException.class, () -> {
            likeService.setCommentUnlike(COMMENT_ID);
        });
    }

    @Test
    void when_setCommentUnLikeWithNoAuth_then_likeNoAuthException() {
        // given
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment2));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(commentLikeRepository.findByUserAndComment(any(), any())).thenReturn(Optional.of(commentLike2));
        // when
        // then
        Assertions.assertThrows(LikeNoAuthException.class, () -> {
            likeService.setCommentUnlike(COMMENT_ID2);
        });
    }

    @Test
    void when_setCommentUnLike_then_true() {
        // given
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        when(jwtUserParser.getUser()).thenReturn(user);
        when(commentLikeRepository.findByUserAndComment(any(), any())).thenReturn(Optional.of(commentLike));
        // when
        boolean res = likeService.setCommentUnlike(COMMENT_ID);
        // then
        assertEquals(res, true);
    }
}