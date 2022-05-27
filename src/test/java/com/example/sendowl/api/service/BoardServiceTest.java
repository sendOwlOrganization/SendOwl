package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.dto.BoardDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @RepeatedTest(value=10000)
    @DisplayName("redis성능테스트")
//    @Test
    void boardDetail() {
        // given

        // when
        BoardDto.DetailRes detailRes = boardService.boardDetail(1L);

        // then
    }


}