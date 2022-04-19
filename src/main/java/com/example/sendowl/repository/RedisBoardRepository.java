package com.example.sendowl.repository;

import com.example.sendowl.redis.entity.RedisBoard;
import org.springframework.data.repository.CrudRepository;

public interface RedisBoardRepository extends CrudRepository<RedisBoard, Long> {
}
