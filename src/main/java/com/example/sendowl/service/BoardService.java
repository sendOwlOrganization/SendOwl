package com.example.sendowl.service;

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

    public List<Board> getBoardList() {
        String active = "Y";

        return BoardRepository.findAll();
    }
}
