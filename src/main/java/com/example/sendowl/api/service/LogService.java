package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.logging.entity.BoardLogs;
import com.example.sendowl.domain.logging.entity.LikeLogs;
import com.example.sendowl.domain.logging.entity.SearchLogs;
import com.example.sendowl.domain.logging.repository.BoardLogsRepository;
import com.example.sendowl.domain.logging.repository.LikeLogsRepository;
import com.example.sendowl.domain.logging.repository.SearchLogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final BoardLogsRepository boardLogsRepository;
    private final SearchLogsRepository searchLogsRepository;
    private final LikeLogsRepository likeLogsRepository;

    private final BoardRepository boardRepository;


    @Transactional
    public void LoggingBoardLog(Long categoryId, String mbti){

        BoardLogs boardLogs = BoardLogs.builder()
                                    .categoryId(categoryId)
                                    .mbti(mbti).build();
        boardLogsRepository.save(boardLogs);
    }

    @Transactional
    public void LoggingSearchKeywordLog(String keyword, String mbti){

        SearchLogs searchLogs = SearchLogs.builder()
                .keyword(keyword)
                .mbti(mbti).build();
        searchLogsRepository.save(searchLogs);
    }

    @Transactional
    public void LoggingLikeLog(Long boardId, String mbti, boolean isLike){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        LikeLogs likeLogs = LikeLogs.builder()
                .boardTitle(board.getTitle())
                .isLike(isLike)
                .mbti(mbti).build();
        likeLogsRepository.save(likeLogs);
    }

}
