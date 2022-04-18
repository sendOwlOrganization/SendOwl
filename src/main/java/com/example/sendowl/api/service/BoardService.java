package com.example.sendowl.api.service;

import com.example.sendowl.auth.jwt.RedisShadowkey;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.entity.RedisBoard;
import com.example.sendowl.repository.RedisBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static com.example.sendowl.domain.board.exception.enums.BoardErrorCode.*;
import static com.example.sendowl.domain.board.dto.BoardDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final RedisBoardRepository redisBoardRepository;
    private final RedisShadowkey redisShadowkey;

    public List<BoardRes> getBoardList() {
       boolean active = true;
       return boardRepository.findByActive(active);
    }

    @Transactional
    public BoardRes insertBoard(BoardReq vo) {

        Board board = Board.builder()
                .title(vo.getTitle())
                .content(vo.getContent())
                .build();

        Board entity = boardRepository.save(board);
        return new BoardRes(entity);
    }

    public BoardRes getBoard(long id) {

        Board board = boardRepository.findById(id).orElseThrow(()-> new BoardNotFoundException(NOT_FOUND));

        // Redis update 쿼리
        RedisBoard redisBoard = redisBoardRepository.findById(id).orElse(new RedisBoard(id));
        redisBoard.setCount(redisBoard.getCount() + 1);
        redisBoardRepository.save(redisBoard);

        // Redis shadowKey 존재확인
        if(redisShadowkey.findByKey("board:"+Long.toString(id)) == null){
            redisShadowkey.set("board:"+Long.toString(id), "", 60L);
        }

        // 수정 예정
        BoardRes boardRes = BoardRes.builder().
                id(board.getId()).
                title(board.getTitle()).
                content(board.getContent()).
                regName(board.getUser().getName()).
                categoryName(board.getCategory().getCategoryName()).
                hit(Math.toIntExact(redisBoard.getCount())).build();

        return boardRes;
    }
}
