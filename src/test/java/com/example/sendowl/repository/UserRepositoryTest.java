package com.example.sendowl.repository;

import com.example.sendowl.domain.tag.entity.Tag;
import com.example.sendowl.domain.tag.repository.TagRepository;
import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    TagRepository tagRepository;

    @Test
    public void 카테고리를기반으로기여한유저의mbti를반환() {

        Optional<Tag> category = tagRepository.findById(2L);

        List<UserMbti> categoryMbti = userRepository.findUserMbtiFromCategory(category.get());
    }
}
