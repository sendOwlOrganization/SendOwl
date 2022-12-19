package com.example.sendowl.redis.service;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RedisEmailTokenService {

    private final String key = RedisEnum.EMAIL_TOKEN;

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOps;

    public RedisEmailTokenService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }

    private String getKey(String email) {
        return key + ":" + email;
    }

    public void save(String email, String token) {
        valueOps.set(getKey(email), token, 180L, TimeUnit.SECONDS);
    }

    public Optional<String> getToken(String email) {
        return Optional.ofNullable(valueOps.get(getKey(email)));
    }

    public Set<String> getAllKeys() {
        return redisTemplate.keys(key + ":*");
    }

    public Map<String, String> getAll() {
        return getAllKeys().stream()
                .collect(Collectors.toMap(k -> k, k -> getToken(k.split(":")[1]).get()));
    }

    public void delete(String email) {
        redisTemplate.delete(getKey(email));
    }

    public void deleteAll() {
        getAllKeys().forEach(k -> delete(k.split(":")[1]));
    }
}
