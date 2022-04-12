package com.example.sendowl.service;

import com.example.sendowl.dto.BoardRequest;
import com.example.sendowl.entity.board.Board;
import com.example.sendowl.repository.BoardRepository;
import com.example.sendowl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<Board> getBoardList() {
       Long active = 1L;
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
        return boardRepository.getById(id);
    }
}
