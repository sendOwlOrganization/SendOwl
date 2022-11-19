package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.like.dto.LikeDto;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.like.exception.LikeNoAuthException;
import com.example.sendowl.domain.like.exception.LikeNotFoundException;
import com.example.sendowl.domain.like.exception.enums.LikeErrorCode;
import com.example.sendowl.domain.like.repository.BoardLikeRepository;
import com.example.sendowl.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    final private BoardLikeRepository boardLikeRepository;
    final private BoardRepository boardRepository;

    @Transactional
    public LikeDto.BoardLikeResponse setBoardLike(LikeDto.BoardLikeRequest req, User user) {

        Board board = boardRepository.findById(req.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board)
                .orElseGet(()-> boardLikeRepository.save(req.toEntity(user, board)));
        if (boardLike.isDeleted()){
            boardLike.restore();
        }

        return new LikeDto.BoardLikeResponse(boardLike);
    }
    @Transactional
    public void setBoardUnLike(LikeDto.BoardUnLikeRequest req, User user) {
        Board board = boardRepository.findById(req.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board)
                .orElseThrow(() -> new LikeNotFoundException(LikeErrorCode.NOT_FOUND));

        if(Objects.equals(boardLike.getUser().getId(), user.getId())){ // 권한 확인
            boardLike.delete();
            boardLikeRepository.save(boardLike);
        }
        else{
            throw new LikeNoAuthException(LikeErrorCode.NO_AUTH);
        }
    }
}
