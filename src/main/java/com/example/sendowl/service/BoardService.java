package com.example.sendowl.service;

import com.example.sendowl.dto.BoardRequest;
import com.example.sendowl.dto.BoardResponse;
import com.example.sendowl.entity.Board;
import com.example.sendowl.entity.Member;
import com.example.sendowl.entity.RedisBoard;
import com.example.sendowl.excption.BoardNotFoundException;
import com.example.sendowl.excption.MemberNotValidException;
import com.example.sendowl.excption.RedisBoardNotFoundException;
import com.example.sendowl.repository.BoardRepository;
import com.example.sendowl.repository.MemberRepository;
import com.example.sendowl.repository.RedisBoardRepository;
import com.example.sendowl.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final RedisBoardRepository redisBoardRepository;
    private final MemberRepository memberRepository;

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

        Board board = boardRepository.findById(id).orElseThrow(()-> new BoardNotFoundException("게시글이 존재하지 않습니다."));

        RedisBoard redisBoard = redisBoardRepository.findById(id).orElse(new RedisBoard(id));
        redisBoard.setCount(redisBoard.getCount() + 1);
        redisBoardRepository.save(redisBoard);

        return board;
    }
}
