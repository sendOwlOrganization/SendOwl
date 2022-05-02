package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.board.specification.BoardSpecification;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.enums.CategoryErrorCode;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserException.*;
import com.example.sendowl.domain.user.exception.enums.UserErrorCode;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.redis.service.RedisBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sendowl.domain.board.dto.BoardDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RedisBoardService redisBoardService;

    public BoardsRes getBoardList(Pageable pageable) {
        Page<Board> pages = boardRepository.findAll(pageable);
        BoardsRes boardsRes = new BoardsRes(pages);
        return boardsRes;
    }

    public BoardsRes getBoardBySearch(Pageable pageable, String where, String queryText){
        Specification<Board> spec = (root, query, builder) -> null;
        if(where.contains("title")){
            spec = spec.and(BoardSpecification.likeTitle(queryText));
        }
        if(where.contains("content")){
            spec = spec.and(BoardSpecification.likeContent(queryText));
        }
        if(where.contains("nickName")){
            spec = spec.and(BoardSpecification.likeUserNickName(queryText));
        }
        return new BoardsRes(boardRepository.findAll(spec, pageable));
    }

    @Transactional
    public DetailRes insertBoard(BoardReq req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.NOT_FOUND));

        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(CategoryErrorCode.NOT_FOUND));

        Board savedBoard = boardRepository.save(req.toEntity(user, category));
        return new DetailRes(savedBoard);
    }

    public DetailRes boardDetail(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        // Redis insert if Absent with shadowkey
        redisBoardService.setIfAbsent(id);

        return new DetailRes(board);
    }
}
