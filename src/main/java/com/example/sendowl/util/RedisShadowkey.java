package com.example.sendowl.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
public class RedisShadowkey {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private String prefix = "shadow:";

    public String findByKey(String key){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(prefix+key);
    }
    public void set(String key, String value, Long exTime){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(prefix+key,value);
        valueOperations.getAndExpire(prefix+key, exTime, TimeUnit.SECONDS);
    }
    public void delete(String key){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.getAndDelete(prefix+key);
    }
}
