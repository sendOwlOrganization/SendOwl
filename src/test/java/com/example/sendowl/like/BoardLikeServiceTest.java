package com.example.sendowl.like;

import com.example.sendowl.api.service.LikeService;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.like.dto.LikeDto;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.like.repository.BoardLikeRepository;
import com.example.sendowl.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardLikeServiceTest {
    private final Long board_id = 1L;
    @InjectMocks
    private LikeService likeService;
    @Mock
    private BoardLikeRepository boardLikeRepository;
    @Mock
    private BoardRepository boardRepository;
    private Board board;
    private User user;
    private BoardLike boardLike;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@gmail.com")
                .transactionId("google")
                .name("test")
                .mbti("estj")
                .build();
        board = Board.builder().user(user).title("testTitle").build();
        boardLike = BoardLike.builder()
                .id(1L)
                .user(user)
                .board(board)
                .build();
    }

    @Test
    public void WhenBoradLikeIsExist_ThenGetExistBoardLike() {
        // given
        LikeDto.BoardLikeRequest boardLikeRequest = new LikeDto.BoardLikeRequest(board_id);

        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(boardLikeRepository.findByUserAndBoard(any(), any())).thenReturn(Optional.ofNullable(boardLike));

        // when
        LikeDto.BoardLikeResponse boardLikeResponse = likeService.setBoardLike(boardLikeRequest);

        // then
        assertThat(boardLikeResponse.getId()).isEqualTo(boardLike.getId());
    }

    @Test
    public void WhenBoradLikeNotExist_ThenCreateBoardLike() {
        // given
        LikeDto.BoardLikeRequest boardLikeRequest = new LikeDto.BoardLikeRequest(board_id);

        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(boardLikeRepository.findByUserAndBoard(any(), any())).thenReturn(Optional.empty());
        when(boardLikeRepository.save(any())).thenReturn(boardLike);

        // when
        LikeDto.BoardLikeResponse boardLikeResponse = likeService.setBoardLike(boardLikeRequest);

        // then
        assertThat(boardLikeResponse.getId()).isEqualTo(boardLike.getId());
    }
}
