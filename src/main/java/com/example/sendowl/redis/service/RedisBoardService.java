package com.example.sendowl.redis.service;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;

public class RedisBoardService {

    private RedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;

    private RedisShadowService redisShadowService;
    private String prefixKey = RedisEnum.BOARD + ":"; // "board:"
    private Long ttl = 30L;

    public RedisBoardService(RedisTemplate redisTemplate, RedisShadowService redisShadowService) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.redisShadowService = redisShadowService;
    }

    public Integer getHit(Long id) {
        return Integer.parseInt(valueOperations.get(prefixKey + Long.toString(id)));
    }

    // key의 카운트를 반환
    @Async
    public void setAddCount(Long id) {
        String key = prefixKey + String.valueOf(id);
        valueOperations.increment(prefixKey + Long.toString(id));
        setShadowKeyTtl(id);
    }

    // 해당 key를 삭제
    public boolean delete(Long id) {
        return redisTemplate.delete(prefixKey + Long.toString(id));
    }

    public void setShadowKeyTtl(Long id) {
        redisShadowService.set(prefixKey + String.valueOf(id), ttl);
    }
}
