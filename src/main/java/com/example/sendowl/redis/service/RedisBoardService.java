package com.example.sendowl.redis.service;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

public class RedisBoardService {

    private RedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;

    private RedisShadowService redisShadowService;
    private String prefixKey = RedisEnum.BOARD + ":"; // "board:"
    private Long ttl = 60L;

    public RedisBoardService(RedisTemplate redisTemplate, RedisShadowService redisShadowService) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.redisShadowService = redisShadowService;
    }

    public Optional<String> getHit(Long id) {
        return Optional.ofNullable(valueOperations.get(prefixKey + Long.toString(id)));
    }

    // key의 카운트를 반환
    public void setIfAbsent(Long id) {
        String key = prefixKey + String.valueOf(id);
        if (!valueOperations.setIfAbsent(key, "1")) {
            valueOperations.increment(prefixKey + Long.toString(id));
        } else {
            setShadowKeyTtl(id);
        }
    }

    // 해당 key를 삭제
    public boolean delete(Long id) {
        return redisTemplate.delete(prefixKey + Long.toString(id));
    }

    public void setShadowKeyTtl(Long id) {
        redisShadowService.set(prefixKey + String.valueOf(id), ttl);
    }
}
