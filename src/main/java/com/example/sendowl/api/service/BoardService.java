package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.dto.BoardRequest;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

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
        return boardRepository.getById(id);
    }
}
