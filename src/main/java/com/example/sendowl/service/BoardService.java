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
import com.example.sendowl.util.RedisShadowkey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final RedisBoardRepository redisBoardRepository;
    private final MemberRepository memberRepository;
    private final StringRedisTemplate redisTemplate;

    private final RedisShadowkey redisShadowkey;


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

        // DB select 쿼리
        Board board = boardRepository.findById(id).orElseThrow(()-> new BoardNotFoundException("게시글이 존재하지 않습니다."));

        // Redis update 쿼리
        RedisBoard redisBoard = redisBoardRepository.findById(id).orElse(new RedisBoard(id));
        redisBoard.setCount(redisBoard.getCount() + 1);
        redisBoardRepository.save(redisBoard);

        // Redis shadowKey 존재확인
        if(redisShadowkey.findByKey("board:"+Long.toString(id)) == null){
            redisShadowkey.set("board:"+Long.toString(id), "", 60L);
        }
        return board;
    }
}
