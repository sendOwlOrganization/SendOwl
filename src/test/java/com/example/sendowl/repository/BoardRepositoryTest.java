package com.example.sendowl.repository;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@AutoConfigureMockMvc
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void 컬럼을대입하여쿼리조회(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Board> pages = boardRepository.findByContentContaining(pageable, "s");
        System.out.println(pages.getTotalPages());
        for(Board board : pages.getContent()){
        }
    }

}
