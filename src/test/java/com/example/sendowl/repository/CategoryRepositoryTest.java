package com.example.sendowl.repository;

import com.example.sendowl.domain.category.entity.CategoryName;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void 카테고리조회가문자열인경우(){
        categoryRepository.findByCategoryName("FREE");
    }

}
