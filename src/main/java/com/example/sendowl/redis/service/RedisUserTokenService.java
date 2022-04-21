package com.example.sendowl.redis.service;

import com.example.sendowl.redis.entity.RedisEmailToken;
import com.example.sendowl.redis.repository.RedisUserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisUserTokenService {

    private final RedisUserTokenRepository userTokenRepository;

    public RedisEmailToken save(RedisEmailToken userToken) {
        return userTokenRepository.save(userToken);
    }

    public Optional<RedisEmailToken> findTokenByEmail(String email) {
        return userTokenRepository.findByEmail(email);
    }

    public void deleteByEmail(String email) {
        Optional<RedisEmailToken> optional = userTokenRepository.findByEmail(email);
        optional.ifPresent(userToken -> userTokenRepository.deleteById(userToken.getId()));
    }

    public void deleteById(Long id) {
        if (userTokenRepository.existsById(id)) {
            userTokenRepository.deleteById(id);
        }
    }
}
