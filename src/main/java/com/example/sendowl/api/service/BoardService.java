package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.dto.BoardRequest;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.entity.RedisBoard;
import com.example.sendowl.repository.RedisBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sendowl.domain.board.exception.enums.BoardErrorCode.*;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final RedisBoardRepository redisBoardRepository;

    public List<Board> getBoardList() {
       boolean active = true;
       return boardRepository.findByActive(active);
    }

    public void insertBoard(BoardRequest vo) {

        Board board = Board.builder()
                .title(vo.getTitle())
                .content(vo.getContent())
                .build();

        boardRepository.save(board);
    }

    public Board getBoard(long id) {

        Board board = boardRepository.findById(id).orElseThrow(()-> new BoardNotFoundException(NOT_FOUND));

        RedisBoard redisBoard = redisBoardRepository.findById(id).orElse(new RedisBoard(id));
        redisBoard.setCount(redisBoard.getCount() + 1);
        redisBoardRepository.save(redisBoard);

        return board;
    }
}
