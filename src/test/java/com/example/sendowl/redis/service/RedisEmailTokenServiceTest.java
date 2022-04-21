package com.example.sendowl.redis.service;

import com.example.sendowl.redis.entity.RedisEmailToken;
import com.example.sendowl.redis.repository.RedisUserTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisEmailTokenServiceTest {

    @Autowired
    private RedisUserTokenService userTokenService;

    @Autowired
    private RedisUserTokenRepository userTokenRepository;

    @BeforeEach
    public void deleteAll() {
        userTokenRepository.deleteAll();
    }

    @Test
    public void testFindTokenByEmail() throws Exception {

        final String email = "EMAIL";
        final String token = "TOKEN";
        // given
        RedisEmailToken build = RedisEmailToken.builder()
                .token(token)
                .email(email)
                .build();
        // when
        userTokenService.save(build);

        // then
        Optional<RedisEmailToken> optional = userTokenService.findTokenByEmail(email);
        RedisEmailToken redisEmailToken = optional.get();
        System.out.println(redisEmailToken);
        assertEquals(redisEmailToken.getEmail(), email);
        assertEquals(redisEmailToken.getToken(), token);
    }

    @Test
    public void deleteTest() throws Exception {
        // given
        final String email = "EMAIL";
        final String token = "TOKEN";
        // given
        RedisEmailToken build = RedisEmailToken.builder()
                .token(token)
                .email(email)
                .build();
        // when
        RedisEmailToken userToken = userTokenService.save(build);
        System.out.println("when--------------------");
        for (RedisEmailToken redisEmailToken : userTokenRepository.findAll()) {
            System.out.println(redisEmailToken);
        }

        // when
        userTokenService.deleteByEmail(email);

        // then
        System.out.println("then--------------------");
        for (RedisEmailToken redisEmailToken : userTokenRepository.findAll()) {
            System.out.println(redisEmailToken);
        }
    }
}