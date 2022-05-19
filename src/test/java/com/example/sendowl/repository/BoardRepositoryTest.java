package com.example.sendowl.repository;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@DataJpaTest(
        properties = {"spring.jpa.properties.hibernate.default_batch_fetch_size=1000"}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    @Test
    public void when카테고리이름으로페치조인thenPage반환(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Board> pages = boardRepository.findAllFetchJoin(pageable);
        for(Board b : pages.getContent()){
            System.out.println(b.getId());
            System.out.println(b.getTitle());
            System.out.println(b.getContent());
            System.out.println(b.getUser());
        }
    }

}
