package com.example.sendowl.repository;

import com.example.sendowl.domain.category.dto.CategoryCount;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void 카테고리조회가문자열인경우() {
        categoryRepository.findByName("FREE");
    }

    @Test
    public void 카테고리조회시게시글순으로() {
        List<CategoryCount> categories = categoryRepository.findCategoriesWithCount();
    }
}
