package com.example.sendowl.redis.service;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;

public class RedisBoardService {

    private final RedisTemplate redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    private final RedisShadowService redisShadowService;
    private final String prefixKey = RedisEnum.BOARD + ":"; // "board:"
    private final Long ttl = 30L;

    public RedisBoardService(RedisTemplate redisTemplate, RedisShadowService redisShadowService) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.redisShadowService = redisShadowService;
    }

    public Integer getHit(Long id) {
        return Integer.parseInt(valueOperations.get(prefixKey + id));
    }

    // key의 카운트를 반환
    @Async
    public void setAddCount(Long id) {
        String key = prefixKey + id;
        valueOperations.increment(prefixKey + id);
        setShadowKeyTtl(id);
    }

    // 해당 key를 삭제
    public boolean delete(Long id) {
        return redisTemplate.delete(prefixKey + id);
    }

    public void setShadowKeyTtl(Long id) {
        redisShadowService.set(prefixKey + id, ttl);
    }
}
