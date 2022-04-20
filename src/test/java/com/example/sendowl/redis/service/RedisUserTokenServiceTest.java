package com.example.sendowl.redis.service;

import com.example.sendowl.redis.entity.RedisUserToken;
import com.example.sendowl.redis.repository.RedisUserTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUserTokenServiceTest {

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
        RedisUserToken build = RedisUserToken.builder()
                .token(token)
                .email(email)
                .build();
        // when
        userTokenService.save(build);

        // then
        Optional<RedisUserToken> optional = userTokenService.findTokenByEmail(email);
        RedisUserToken redisUserToken = optional.get();
        System.out.println(redisUserToken);
        assertEquals(redisUserToken.getEmail(), email);
        assertEquals(redisUserToken.getToken(), token);
    }

    @Test
    public void deleteTest() throws Exception {
        // given
        final String email = "EMAIL";
        final String token = "TOKEN";
        // given
        RedisUserToken build = RedisUserToken.builder()
                .token(token)
                .email(email)
                .build();
        // when
        RedisUserToken userToken = userTokenService.save(build);
        System.out.println("when--------------------");
        for (RedisUserToken redisUserToken : userTokenRepository.findAll()) {
            System.out.println(redisUserToken);
        }

        // when
        userTokenService.deleteByEmail(email);

        // then
        System.out.println("then--------------------");
        for (RedisUserToken redisUserToken : userTokenRepository.findAll()) {
            System.out.println(redisUserToken);
        }
    }
}