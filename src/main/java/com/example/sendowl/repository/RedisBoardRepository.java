package com.example.sendowl.repository;

import com.example.sendowl.entity.RedisBoard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisBoardRepository extends CrudRepository<RedisBoard, Long> {
}
