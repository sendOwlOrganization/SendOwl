package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.like.dto.LikeDto;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.like.repository.BoardLikeRepository;
import com.example.sendowl.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new RuntimeException());

        BoardLike boardLike;
        Optional<BoardLike> opBoardLike = boardLikeRepository.findByUserAndBoard(user, board);
        if (opBoardLike.isEmpty()) {
            boardLike = boardLikeRepository.save(req.toEntity(user, board));
        } else {
            boardLike = opBoardLike.get();
        }

        return new LikeDto.BoardLikeResponse(boardLike);
    }
}
