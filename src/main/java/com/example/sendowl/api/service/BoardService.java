package com.example.sendowl.api.service;

import com.example.sendowl.common.converter.EditorJsHelper;
import com.example.sendowl.domain.board.dto.BoardDto;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public BoardsRes getBoardList(Long categoryId,Integer textLength, Pageable pageable) {
        Page<Board> pages;
        if(categoryId == 0L){
            pages = boardRepository.findBoardFetchJoin(pageable);
        }
        else {
            pages = boardRepository.findBoardByCategoryIdFetchJoin(categoryId, pageable);
        }
        BoardsRes boardsRes = new BoardsRes(pages, textLength);
        return boardsRes;
    }

    public BoardsRes getBoardBySearch(Pageable pageable,Integer textLength, String where, String queryText){
        Specification<Board> spec = (root, query, builder) -> null;
        if(where.contains("title")){
            spec = spec.or(BoardSpecification.likeTitle(queryText));
        }
        if(where.contains("content")){
            spec = spec.or(BoardSpecification.likeContent(queryText));
        }
        if(where.contains("nickName")){
            spec = spec.or(BoardSpecification.likeUserNickName(queryText));
        }
        return new BoardsRes(boardRepository.findAll(spec, pageable), textLength);
    }

    @Transactional
    public DetailRes insertBoard(BoardReq req, User user) {
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(CategoryErrorCode.NOT_FOUND));

        //String refinedText = new MarkdownToText(req.getContent()).getRefinedText();
        String refinedText = new EditorJsHelper().extractText(req.getEditorJsContent());

        Board savedBoard = boardRepository.save(req.toEntity(user, category, refinedText));
        return new DetailRes(savedBoard);
    }

    @Transactional
    public DetailRes boardDetail(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        // Redis add hit count
//        redisBoardService.setAddCount(id);
//        Integer hit = redisBoardService.getHit(id);
        Integer hit = board.getHit();
        board.setHit(hit+1);

        return new DetailRes(board);
    }

    @Transactional
    public UpdateBoardRes updateBoard(UpdateBoardReq req, User user) {
        Board board = boardRepository.findById(req.getBoardId()).orElseThrow(
                () -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(CategoryErrorCode.NOT_FOUND));
        if(board.getUser().getId() != user.getId()){
            throw new UserUnauthorityException(UserErrorCode.UNAUTHORIZED);
        }

        //String refinedText = new MarkdownToText(req.getContent()).getRefinedText();
        String refinedText = new EditorJsHelper().extractText(req.getEditorJsContent());

        // editorJS내용을 content로 바꾼다.
        ObjectMapper objectMapper = new ObjectMapper();
        String content = "";
        try{
            content = objectMapper.writeValueAsString(req.getEditorJsContent());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        board.updateBoard(req.getTitle(), content, category, refinedText);

        boardRepository.save(board);
        UpdateBoardRes updatedBoard = new UpdateBoardRes(board);
        return updatedBoard;
    }

    @Transactional
    public void deleteBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        if(board.getUser().getId() != user.getId()){
            throw new UserUnauthorityException(UserErrorCode.UNAUTHORIZED);
        }

        board.delete();

        boardRepository.save(board);
    }
}
