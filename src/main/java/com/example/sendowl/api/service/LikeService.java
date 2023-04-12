package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.comment.exception.CommentNotFoundException;
import com.example.sendowl.domain.comment.exception.enums.CommentErrorCode;
import com.example.sendowl.domain.comment.repository.CommentRepository;
import com.example.sendowl.domain.like.dto.LikeDto;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.like.entity.CommentLike;
import com.example.sendowl.domain.like.exception.LikeNoAuthException;
import com.example.sendowl.domain.like.exception.LikeNotFoundException;
import com.example.sendowl.domain.like.exception.enums.LikeErrorCode;
import com.example.sendowl.domain.like.repository.BoardLikeRepository;
import com.example.sendowl.domain.like.repository.CommentLikeRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.util.mail.JwtUserParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    final private BoardLikeRepository boardLikeRepository;
    final private CommentLikeRepository commentLikeRepository;
    final private BoardRepository boardRepository;
    final private CommentRepository commentRepository;
    final private JwtUserParser jwtUserParser;

    @Transactional
    public LikeDto.BoardLikeResponse setBoardLike(LikeDto.BoardLikeRequest req) {

        Board board = boardRepository.findById(req.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        User user = jwtUserParser.getUser();
        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board)
                .orElseGet(() -> boardLikeRepository.save(req.toEntity(user, board)));
        if (boardLike.isDeleted()) {
            boardLike.restore();
        }

        return new LikeDto.BoardLikeResponse(boardLike);
    }

    @Transactional
    public boolean setBoardUnLike(@Valid Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        User user = jwtUserParser.getUser();
        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board)
                .orElseThrow(() -> new LikeNotFoundException(LikeErrorCode.NOT_FOUND));

        if (Objects.equals(boardLike.getUser().getId(), user.getId())) { // 권한 확인
            boardLike.delete();
            boardLikeRepository.save(boardLike);
            return true;
        } else {
            throw new LikeNoAuthException(LikeErrorCode.NO_AUTH);
        }
    }

    @Transactional
    public LikeDto.CommentLikeResponse setCommentLike(LikeDto.CommentLikeRequest req) {
        Comment comment = commentRepository.findById(req.getCommentId())
                .orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.NOT_FOUND));

        User user = jwtUserParser.getUser();
        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment)
                .orElseGet(() -> commentLikeRepository.save(req.toEntity(user, comment)));
        if (commentLike.isDeleted()) {
            commentLike.restore();
        }
        return new LikeDto.CommentLikeResponse(commentLike);
    }

    @Transactional
    public boolean setCommentUnlike(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.NOT_FOUND));

        User user = jwtUserParser.getUser();
        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new LikeNotFoundException(LikeErrorCode.NOT_FOUND));

        if (Objects.equals(commentLike.getUser().getId(), user.getId())) { // 권한 확인
            commentLike.delete();
            commentLikeRepository.save(commentLike);
            return true;
        } else {
            throw new LikeNoAuthException(LikeErrorCode.NO_AUTH);
        }
    }
}
