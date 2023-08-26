package com.example.sendowl.repository;

import com.example.sendowl.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void 카테고리조회가문자열인경우() {
        tagRepository.findByName("FREE");
    }

}
