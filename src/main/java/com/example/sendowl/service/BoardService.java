package com.example.sendowl.service;

import com.example.sendowl.dto.BoardRequest;
import com.example.sendowl.dto.BoardResponse;
import com.example.sendowl.entity.Board;
import com.example.sendowl.entity.Member;
import com.example.sendowl.excption.MemberNotValidException;
import com.example.sendowl.repository.BoardRepository;
import com.example.sendowl.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public List<Board> getBoardList() {
       int active = 1;

        return boardRepository.findByActive(active);
    }

    public void insertBoard(BoardRequest vo) {

        Member member = memberRepository.findById(vo.getId()).get();

        Board board = Board.builder()
                .title(vo.getTitle())
                .content(vo.getContent())
                .member(member)
                .build();

        boardRepository.save(board);
    }

    public Board getBoard(long id) {
        return boardRepository.getById(id);
    }
}
