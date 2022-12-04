package com.example.sendowl.api.service;

import com.example.sendowl.domain.logging.entity.BoardKeyword;
import com.example.sendowl.domain.logging.repository.BoardKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoggingBoardKeywordService {

    private final BoardKeywordRepository boardKeywordRepository;

    @Transactional
    public void insertBoardKeywordLog(Long categoryId, String keyword, String mbti){

        BoardKeyword boardKeyword = BoardKeyword.builder()
                                    .categoryId(categoryId)
                                    .keyword(keyword)
                                    .mbti(mbti).build();
        //BoardKeyword savedlog = boardKeywordRepository.save(boardKeyword);
    }

}
