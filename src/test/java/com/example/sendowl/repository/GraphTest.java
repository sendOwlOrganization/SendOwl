package com.example.sendowl.repository;

import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest(
        properties = {"spring.jpa.properties.hibernate.default_batch_fetch_size=1000"}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GraphTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void test() {
        List<User> all = userRepository.findAll();
    }

    @Test
    void userMbtiGraph() {
        // given
        List<UserMbti> all = userRepository.findAllUserMbtiWithCount();

        // when

        // then
    }
}
