package com.example.sendowl.api.service;

import com.example.sendowl.common.converter.EditorJsHelper;
import com.example.sendowl.domain.board.dto.PreviewBoardDto;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.util.mail.JwtUserParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.sendowl.domain.board.dto.BoardDto.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    private final String refinedText = "refinedText";
    private final String boardContent = "content1";
    private final Long CATEGORY_ID = 1L;
    private final Integer TITLE_LENGTH = 10;
    private final Integer TEXT_LENGTH = 10;
    private final String CATEGORY_NAME = "자유게시판";
    @InjectMocks
    private BoardService boardService;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private JwtUserParser jwtUserParser;
    @Mock
    private ExpService expService;
    @Mock
    private EditorJsHelper editorJsHelper;
    private Pageable pageable;
    private User user;
    private Category category;
    private Board board;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .transactionId("google")
                .name("sendowl")
                .email("a1@naver.com")
                .mbti("esfp")
                .build();

        category = Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME).build();

        board = Board.builder().user(user).category(category).content(boardContent).build();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void when_insertBoardWrongCategoryId_then_categoryNotFoundExcpetion() {
        // given
        BoardReq req = new BoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            boardService.insertBoard(req);
        });
    }

    @Test
    void when_insertBoard_then_board() {
        // given
        BoardReq req = new BoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));
        when(editorJsHelper.extractText(any())).thenReturn(refinedText);
        when(boardRepository.save(any())).thenReturn(board);
        // then
        DetailRes detailRes = boardService.insertBoard(req);
        assertEquals(detailRes.getContent(), board.getContent());
    }

    @Test
    void when_getPreviewBoardList_then_categoryNotFoundException() {
        // given
        // when
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            boardService.getPreviewBoardList(CATEGORY_ID, TITLE_LENGTH, pageable);
        });
    }

    @Test
    void when_getPreviewBoardList_then_ListPreviewBoardRes() {
        // given
        List<PreviewBoardDto> previewBoard = new ArrayList<>();
        // when
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));
        when(boardRepository.findPreviewBoard(any(), any(), any())).thenReturn(previewBoard);
        // then
        List<PreviewBoardRes> previewBoardList = boardService.getPreviewBoardList(CATEGORY_ID, TITLE_LENGTH, pageable);
        assertEquals(previewBoardList.size(), 0);
    }

    @Test
    void when_getBoardList_then_categoryNotFoundException() {
        // given
        // when
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            boardService.getBoardList(CATEGORY_ID, TEXT_LENGTH, pageable);
        });
    }
}