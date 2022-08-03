package com.example.sendowl.repository;

import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void 카테고리를기반으로기여한유저의mbti를반환(){

        Optional<Category> category = categoryRepository.findById(2L);
        System.out.println(category.get().getName());

        List<UserMbti> categoryMbti = userRepository.findUserMbtiFromCategory(category.get());
        for (UserMbti mbti: categoryMbti
             ) {
            System.out.println(mbti.getMbti());
            System.out.println(mbti.getCount());
        }
    }
}
