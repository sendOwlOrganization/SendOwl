package com.example.sendowl.api.service;

import com.example.sendowl.common.converter.EditorJsHelper;
import com.example.sendowl.domain.board.dto.PreviewBoardDto;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.tag.entity.Tag;
import com.example.sendowl.domain.tag.exception.TagNotFoundException;
import com.example.sendowl.domain.tag.repository.TagRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserException;
import com.example.sendowl.util.mail.JwtUserParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final Long BOARD_ID1 = 1L;
    private final Long BOARD_ID2 = 2L;
    private final Long BOARD_ID3 = 3L;
    private final Long CATEGORY_ID = 1L;
    private final Long ALL_CATEGORY_ID = 0L;
    private final Integer TITLE_LENGTH = 10;
    private final Integer TEXT_LENGTH = 10;
    private final String CATEGORY_NAME1 = "자유게시판";
    private final String CATEGORY_NAME2 = "기타";
    private final Long USER_ID1 = 1L;
    private final Long USER_ID2 = 2L;
    @InjectMocks
    private BoardService boardService;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private JwtUserParser jwtUserParser;
    @Mock
    private ExpService expService;
    @Mock
    private EditorJsHelper editorJsHelper;
    private Pageable pageable;
    private User user;
    private User user2;
    private Tag tag;
    private Tag tagAll;
    private Board board;
    private Board boardWithAnotherUser;
    private Board boardWithAllCategory;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(USER_ID1)
                .transactionId("google")
                .name("sendowl")
                .email("a1@naver.com")
                .mbti("esfp")
                .build();
        user2 = User.builder()
                .id(USER_ID2)
                .transactionId("kakao")
                .name("sendowl")
                .email("2@naver.com")
                .mbti("infp")
                .build();

        tag = Tag.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME1).build();
        tagAll = Tag.builder()
                .id(ALL_CATEGORY_ID)
                .name(CATEGORY_NAME2).build();

        board = Board.builder().id(BOARD_ID1).user(user).content(boardContent).refinedContent(refinedText).hit(0).build();

        boardWithAnotherUser = Board.builder().id(BOARD_ID2).user(user2).content(boardContent).refinedContent(refinedText).hit(0).build();

        boardWithAllCategory = Board.builder().id(BOARD_ID3).user(user).content(boardContent).refinedContent(refinedText).build();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void when_insertBoardWrongCategoryId_then_categoryNotFoundExcpetion() {
        // given
        BoardReq req = new BoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            boardService.insertBoard(req);
        });
    }

    @Test
    void when_insertBoard_then_board() {
        // given
        BoardReq req = new BoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(tag));
        when(editorJsHelper.extractText(any())).thenReturn(refinedText);
        when(boardRepository.save(any())).thenReturn(board);
        // then
        DetailRes detailRes = boardService.insertBoard(req);
        assertEquals(detailRes.getId(), board.getId());
    }

    @Test
    void when_getPreviewBoardList_then_categoryNotFoundException() {
        // given
        // when
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            boardService.getPreviewBoardList(CATEGORY_ID, TITLE_LENGTH, pageable);
        });
    }

    @Test
    void when_getPreviewBoardList_then_ListPreviewBoardRes() {
        // given
        List<PreviewBoardDto> previewBoard = new ArrayList<>();
        // when
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(tag));
        when(boardRepository.findPreviewBoard(any(), any(), any())).thenReturn(previewBoard);
        // then
        List<PreviewBoardRes> previewBoardList = boardService.getPreviewBoardList(CATEGORY_ID, TITLE_LENGTH, pageable);
        assertEquals(previewBoardList.size(), 0);
    }

    @Test
    void when_getBoardList_then_categoryNotFoundException() {
        // given
        // when
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            boardService.getBoardList(CATEGORY_ID, TEXT_LENGTH, pageable);
        });
    }

    @Test
    void when_getBoardList_then_boardRes() {
        // given
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        Page<Board> pages = new PageImpl<>(boards);
        // when
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(tag));
        when(boardRepository.findBoardByCategoryIdFetchJoin(any(), any())).thenReturn(pages);
        // then
        BoardsRes boardsRes = boardService.getBoardList(CATEGORY_ID, TEXT_LENGTH, pageable);
        assertEquals(boardsRes.getBoards().get(0).getNickname(), board.getUser().getNickName());
    }

    @Test
    void when_insertBoard_then_categoryNotFoundException() {
        // given
        BoardReq req = new BoardReq();
        // when
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            boardService.insertBoard(req);
        });
    }

    @Test
    void when_insertBoard_then_detailRes() {
        // given
        BoardReq req = new BoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(tag));
        when(editorJsHelper.extractText(any())).thenReturn(refinedText);
        when(boardRepository.save(any())).thenReturn(board);
        // then
        DetailRes detailRes = boardService.insertBoard(req);
        assertEquals(detailRes.getId(), board.getId());
    }

    @Test
    void when_getBoardDetail_then_boardNotFoundException() {
        // given
        // when
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(BoardNotFoundException.class, () -> {
            boardService.getBoardDetail(BOARD_ID1);
        });
    }

    @Test
    void when_getBoardDetail_then_detailRes() {
        // given
        // when
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        // then
        DetailRes detailRes = boardService.getBoardDetail(BOARD_ID1);
        assertEquals(detailRes.getId(), board.getId());
    }

    @Test
    void when_updateBoard_then_boardNotFoundException() {
        // given
        UpdateBoardReq req = new UpdateBoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(BoardNotFoundException.class, () -> {
            boardService.updateBoard(req);
        });
    }

    @Test
    void when_updateBoard_then_categoryNotFoundException() {
        // given
        UpdateBoardReq req = new UpdateBoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            boardService.updateBoard(req);
        });
    }

    @Test
    void when_updateBoard_then_userUnauthorityException() {
        // given
        UpdateBoardReq req = new UpdateBoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(boardWithAnotherUser));
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(tag));
        // then
        Assertions.assertThrows(UserException.UserUnauthorityException.class, () -> {
            boardService.updateBoard(req);
        });
    }

    @Test
    void when_updateBoard_then_updateBoardRes() {
        // given
        UpdateBoardReq req = new UpdateBoardReq();
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(tag));
        when(editorJsHelper.extractText(any())).thenReturn(refinedText);
        // then
        UpdateBoardRes updateBoardRes = boardService.updateBoard(req);
        assertEquals(updateBoardRes.getId(), board.getId());
    }

    @Test
    void when_deleteBoard_then_boardNotFoundException() {
        // given
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // then
        Assertions.assertThrows(BoardNotFoundException.class, () -> {
            boardService.deleteBoard(BOARD_ID1);
        });
    }

    @Test
    void when_deleteBoard_then_userUnauthorityException() {
        // given
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(boardWithAnotherUser));
        // then
        Assertions.assertThrows(UserException.UserUnauthorityException.class, () -> {
            boardService.deleteBoard(BOARD_ID1);
        });
    }

    @Test
    void when_deleteBoard_then_void() {
        // given
        // when
        when(jwtUserParser.getUser()).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        // then
        boardService.deleteBoard(BOARD_ID1);
    }

}