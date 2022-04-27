package com.example.sendowl.redis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisEmailTokenServiceTest {

    @Autowired
    private RedisEmailTokenService redisEmailTokenService;

    @BeforeEach
    public void deleteAll() {
        redisEmailTokenService.deleteAll();
    }

    @Test
    public void testAddEntity() throws Exception {
        // given
        final String email = "email@naver.com";
        final String token = "12345";

        // when
        redisEmailTokenService.save(email, token);

        // then
        Optional<String> optional = redisEmailTokenService.getToken(email);
        String redisEmailToken = optional.get();
        assertEquals(redisEmailToken, token);

        System.out.println("----------------------- sout");
        System.out.println(redisEmailToken);

    }

    @Test
    public void testGetAll() throws Exception {
        // given
        final String email = "email@naver.com";
        final String token = "12345";

        for (int i = 0; i < 3; i++) {
            redisEmailTokenService.save(email + i, token + i);
        }

        Set<String> allKeys = redisEmailTokenService.getAllKeys();
        System.out.println(allKeys);

        // then
        Map<String, String> all = redisEmailTokenService.getAll();
        System.out.println("testGetAll");
        System.out.println(all);
    }

    @Test
    public void testUpdate() throws Exception {
        final String email = "email@naver.com";
        final String token = "1111";

        // when
        redisEmailTokenService.save(email, token);

        Optional<String> optional = redisEmailTokenService.getToken(email);
        String redisEmailToken = optional.get();
        System.out.println("redisEmailToken=" + redisEmailToken);

        this.redisEmailTokenService.save(email, token + "2");

        Optional<String> optional2 = this.redisEmailTokenService.getToken(email);
        String redisEmailToken2 = optional2.get();
        System.out.println("redisEmailToken2=" + redisEmailToken2);
    }

}