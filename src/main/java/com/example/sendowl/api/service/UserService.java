package com.example.sendowl.api.service;

import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.*;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.auth.jwt.JwtProvider;
import com.example.sendowl.kafka.producer.KafkaProducer;
import com.example.sendowl.redis.template.RedisEmailTokenService;
import com.example.sendowl.util.mail.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.example.sendowl.auth.jwt.JwtEnum.*;
import static com.example.sendowl.domain.user.dto.UserDto.*;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final KafkaProducer kafkaProducer;
    private final RedisEmailTokenService redisEmailTokenService;

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public JoinRes save(JoinReq req) {
        User user = User.builder()
                .name(req.getName())
                .nickName(req.getNickName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .introduction(req.getIntroduction())
                .profileImage(req.getProfileImage())
                .build();

        User entity = userRepository.save(user);
        return new JoinRes(entity);
    }

    public Map<String, String> login(LoginReq req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UserNotValidException(INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getEmail(), user.getRole());

        return new HashMap<>(Map.of(
                ACCESS_TOKEN, accessToken,
                REFRESH_TOKEN, refreshToken));
    }


    public UserRes getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        return new UserRes(user);
    }

    public EmailCheckRes emailCheck(EmailCheckReq req) {
        if (userRepository.existsUserByEmail(req.getEmail())) {
            throw new UserAlreadyExistException(EXISTING_EMAIL);
        }

        String token = new TokenGenerator().generateSixRandomNumber();
        new Thread(() -> {
            kafkaProducer.sendEmailVerification(req.getEmail(), token); // 인증 코드 전송
            redisEmailTokenService.save(req.getEmail(), token);
        }).start();


        return new EmailCheckRes().success();
    }

    public EmailVerifyRes emailVerify(EmailVerifyReq req) {
        String token = redisEmailTokenService.getToken(req.getEmail())
                .orElseThrow(() -> new UserVerifyTokenExpiredException(EXPIRED_VERIFICATION_TOKEN));

        return new EmailVerifyRes(token.equals(req.getToken()));
    }
}
