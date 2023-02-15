package com.example.sendowl.repository;


import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
public class H2ExampleTest {

    private final String userName = "testUser";
    private final String userName2 = "testUser2";
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name(userName)
                .mbti("estj")
                .email("a1@naver.com")
                .nickName("testNickName")
                .password("123123")
                .transactionId("google")
                .build();
        userRepository.save(user);

        User user2 = User.builder()
                .name(userName2)
                .mbti("estj")
                .email("a1@naver.com")
                .nickName("testNickName")
                .password("123123")
                .transactionId("google")
                .build();
        userRepository.save(user2);
    }

    @Test
    void getUser() {
        Optional<User> optionalUser = userRepository.findById(1L);
        User user = optionalUser.get();
        assertEquals(userName, user.getName());
    }

}
