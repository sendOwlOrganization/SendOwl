package com.example.sendowl.api.service;

import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserNotFoundException;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.redis.RedisShadowkey;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.redis.entity.RedisBoard;
import com.example.sendowl.redis.repository.RedisBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sendowl.domain.board.dto.BoardDto.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RedisBoardRepository redisBoardRepository;
    private final RedisShadowkey redisShadowkey;

    public List<BoardsRes> getBoardList() {
       boolean active = true;
        //List<Board> boards = boardRepository.findByActive(active);
        List<Board> boards = boardRepository.findAll();
        List<BoardsRes> boardRes = new ArrayList<>();
        for(Board i : boards){
            BoardsRes temp = new BoardsRes(i);
            boardRes.add(temp);
        }

        return boardRes;
    }

    @Transactional
    public BoardsRes insertBoard(BoardReq req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));

        Category category = categoryRepository.findByCategoryName(req.getCategoryName()).orElseThrow(() -> new CategoryNotFoundException(NOT_FOUND));

        Board board = Board.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .category(category)
                .user(user).build();

        boardRepository.save(board);

        return new BoardsRes(board);
    }

    public DetailRes boardDetail(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(NOT_FOUND));

        // Redis update 쿼리
        RedisBoard redisBoard = redisBoardRepository.findById(id).orElse(new RedisBoard(id));
        redisBoard.setCount(redisBoard.getCount() + 1);
        redisBoardRepository.save(redisBoard);

        // Redis shadowKey 존재확인
        if(redisShadowkey.findByKey("board:"+Long.toString(id)) == null){
            redisShadowkey.set("board:"+Long.toString(id), "", 60L);
        }

        return new DetailRes(board);
    }
}
