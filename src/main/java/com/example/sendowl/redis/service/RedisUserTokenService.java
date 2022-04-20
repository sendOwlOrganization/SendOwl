package com.example.sendowl.redis.service;

import com.example.sendowl.redis.entity.RedisUserToken;
import com.example.sendowl.redis.repository.RedisUserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisUserTokenService {

    private final RedisUserTokenRepository userTokenRepository;

    public RedisUserToken save(RedisUserToken userToken) {
        return userTokenRepository.save(userToken);
    }

    public Optional<RedisUserToken> findTokenByEmail(String email) {
        return userTokenRepository.findByEmail(email);
    }

    public void deleteByEmail(String email) {
        Optional<RedisUserToken> optional = userTokenRepository.findByEmail(email);
        optional.ifPresent(userToken -> userTokenRepository.deleteById(userToken.getId()));
    }

    public void deleteById(Long id) {
        if (userTokenRepository.existsById(id)) {
            userTokenRepository.deleteById(id);
        }
    }
}
