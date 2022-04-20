package com.example.sendowl.redis.repository;


import com.example.sendowl.redis.entity.RedisUserToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisUserTokenRepository extends CrudRepository<RedisUserToken, Long> {
    Optional<RedisUserToken> findByEmail(String email);
}
