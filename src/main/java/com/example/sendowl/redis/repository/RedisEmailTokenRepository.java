package com.example.sendowl.redis.repository;


import com.example.sendowl.redis.entity.RedisEmailToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisEmailTokenRepository extends CrudRepository<RedisEmailToken, Long> {
    Optional<RedisEmailToken> findByEmail(String email);
}
