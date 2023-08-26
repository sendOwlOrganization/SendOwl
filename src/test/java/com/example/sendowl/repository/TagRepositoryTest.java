package com.example.sendowl.repository;

import com.example.sendowl.domain.tag.dto.TagCount;
import com.example.sendowl.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void 카테고리조회가문자열인경우() {
        tagRepository.findByName("FREE");
    }

    @Test
    public void 카테고리조회시게시글순으로() {
        List<TagCount> categories = tagRepository.findTagsWithBoardCount();
    }
}
