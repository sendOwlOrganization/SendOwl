package com.example.sendowl.api.service;

import com.example.sendowl.domain.logging.entity.BoardLogs;
import com.example.sendowl.domain.logging.repository.BoardLogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardLogsService {

    private final BoardLogsRepository boardLogsRepository;

    @Transactional
    public void insertBoardKeywordLog(Long categoryId, String mbti){

        BoardLogs boardLogs = BoardLogs.builder()
                                    .categoryId(categoryId)
                                    .mbti(mbti).build();
        boardLogsRepository.save(boardLogs);
    }

}
