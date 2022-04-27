package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.entity.CategoryName;
import com.example.sendowl.domain.category.enums.CategoryErrorCode;
import com.example.sendowl.domain.category.exception.CategoryNameNotFoundException;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserNotFoundException;
import com.example.sendowl.domain.user.exception.enums.UserErrorCode;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.redis.service.RedisBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sendowl.domain.board.dto.BoardDto.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RedisBoardService redisBoardService;

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
                () -> new UserNotFoundException(UserErrorCode.NOT_FOUND));
        
        CategoryName categoryName = null;
        try {
            categoryName = CategoryName.valueOf(req.getCategoryName());
        } catch (RuntimeException ex) {
            throw new CategoryNameNotFoundException(CategoryErrorCode.NOT_FOUND);
        }

        // Enum기준으로 DB에 올라가기 때문에 나중엔 DB통한 검증이 사라져도 될거 같습니다.
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(CategoryErrorCode.NOT_FOUND));

        Board board = Board.builder()
                .user(user)
                .title(req.getTitle())
                .content(req.getContent())
                .category(category)
                .build();

        Board savedBoard = boardRepository.save(board);

        return new BoardsRes(savedBoard);
    }

    public DetailRes boardDetail(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        // Redis insert if Absent with shadowkey
        redisBoardService.setIfAbsent(id);

        return new DetailRes(board);
    }
}
