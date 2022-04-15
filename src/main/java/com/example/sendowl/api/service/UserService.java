package com.example.sendowl.api.service;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCode;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserNotFoundException;
import com.example.sendowl.domain.user.exception.UserNotValidException;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import static com.example.sendowl.domain.user.dto.UserDto.*;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public JoinRes save(JoinReq req) {
        if (Objects.isNull(req.getName())
                || Objects.isNull(req.getNickName())
                || Objects.isNull(req.getEmail())
                || Objects.isNull(req.getPassword())) {
            throw new UserNotValidException(INVALID_USER_INFO);
        }

        if (userRepository.existsUserByEmail(req.getEmail())) {
            throw new UserNotValidException(INVALID_EMAIL);
        }

        User user = User.builder()
                .name(req.getName())
                .nickName(req.getNickName())
                .email(req.getEmail())
                .password(req.getPassword())
                .introduction(req.getIntroduction())
                .profileImage(req.getProfileImage())
                .build();

        User entity = userRepository.save(user);
        return new JoinRes(entity);
    }

    public LoginRes login(LoginReq req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UserNotValidException(INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.createToken(user.getEmail(), user.getRole());
        String refreshToken = "";

        return new LoginRes(accessToken, refreshToken);
    }


    public UserRes getUser(Long id) {
        if (Objects.isNull(id)) {
            throw new BaseException(BaseErrorCode.NULL_ERROR, "getUser path variable 'id' is null");
        }
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        return new UserRes(user);
    }
}
