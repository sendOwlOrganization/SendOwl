package com.example.sendowl.api.service;

import com.example.sendowl.auth.exception.TokenExpiredException;
import com.example.sendowl.auth.exception.TokenNotEqualsException;
import com.example.sendowl.auth.exception.enums.TokenErrorCode;
import com.example.sendowl.auth.jwt.JwtProvider;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.comment.repository.CommentRepository;
import com.example.sendowl.domain.tag.entity.Tag;
import com.example.sendowl.domain.tag.enums.TagErrorCode;
import com.example.sendowl.domain.tag.exception.TagNotFoundException;
import com.example.sendowl.domain.tag.repository.TagRepository;
import com.example.sendowl.domain.user.dto.Oauth2User;
import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.Oauth2Exception;
import com.example.sendowl.domain.user.exception.UserException.UserNotFoundException;
import com.example.sendowl.domain.user.exception.UserException.UserNotValidException;
import com.example.sendowl.domain.user.exception.enums.Oauth2ErrorCode;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.domain.user.util.oauth.GoogleOauth;
import com.example.sendowl.domain.user.util.oauth.KakaoOauth;
import com.example.sendowl.util.DateUtil;
import com.example.sendowl.util.mail.JwtUserParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.sendowl.auth.jwt.JwtProvider.REFRESH_TOKEN_VALIDSECOND;
import static com.example.sendowl.auth.jwt.TokenEnum.ACCESS_TOKEN;
import static com.example.sendowl.auth.jwt.TokenEnum.REFRESH_TOKEN;
import static com.example.sendowl.domain.user.dto.UserDto.*;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.INVALID_PASSWORD;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final ExpService expService;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtUserParser jwtUserParser;
    private final DateUtil dateUtil;
    private final GoogleOauth googleOauth;
    private final KakaoOauth kakaoOauth;

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public JoinRes save(JoinReq req) {
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        User entity = userRepository.save(req.toEntity(encodedPassword));
        return new JoinRes(entity);
    }

    @Transactional
    public UserRes login(LoginReq req, HttpServletResponse servletResponse) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UserNotValidException(INVALID_PASSWORD);
        }

        setAccessToken(servletResponse, jwtProvider.createAccessToken(user));
        setRefreshToken(servletResponse, user, jwtProvider.createRefreshToken());

        return new UserRes(user);
    }

    @Transactional
    public UserRes infiniteLogin(LoginReq req, HttpServletResponse servletResponse) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UserNotValidException(INVALID_PASSWORD);
        }

        setAccessToken(servletResponse, jwtProvider.createInfiniteAccessToken(user));
        setRefreshToken(servletResponse, user, jwtProvider.createRefreshToken());

        return new UserRes(user);
    }

    public UserRes getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        return new UserRes(user);
    }

    public UserSelfRes getUserSelf() {
        User user = jwtUserParser.getUser();
        // 게시한 글
        Long boardCount = boardRepository.countByUserAndDelDateIsNull(user);
        Long commentCount = commentRepository.countByUserAndDelDateIsNull(user);

        return new UserSelfRes(user, boardCount, commentCount);
    }

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public Oauth2Res oauthService(Oauth2Req req, HttpServletResponse servletResponse) {

        Boolean alreadyJoined = true;
        Boolean alreadySetted = true;
        User retUser = null;
        Oauth2User oauthUser = null;

        // 토큰의 유효성 검증
        try {
            if (req.getTransactionId().equals("google"))
                oauthUser = googleOauth.getOauth2User(req.getToken());
            else if (req.getTransactionId().equals("kakao"))
                oauthUser = kakaoOauth.getOauth2User(req.getToken());
            else
                throw new Oauth2Exception.TransactionIdNotValid(Oauth2ErrorCode.BAD_TRANSACTIONID);
        } catch (HttpStatusCodeException httpStatusCodeException) {
            throw new Oauth2Exception.TokenNotValid(Oauth2ErrorCode.UNAUTHORIZED);
        }

        // 회원여부 확인
        Optional<User> optionalUser = userRepository.findUserByEmailAndTransactionId(oauthUser.getEmail(), oauthUser.getTransactionId());
        if (optionalUser.isEmpty()) {
            retUser = userRepository.save(
                    User.builder()
                            .email(oauthUser.getEmail())
                            .name(oauthUser.getName())
                            .transactionId(oauthUser.getTransactionId())
                            .build()
            );
            alreadyJoined = false;
        } else {
            retUser = optionalUser.get();

            // 사용자 초기화 되었는지 확인 - 사용자가 초기화 되지 않은 경우 초기화가 필요함을 알려줌.
            if (retUser.getNickName() == null || retUser.getMbti() == null) {
                alreadySetted = false;
            }
            setAccessToken(servletResponse, jwtProvider.createAccessToken(retUser));
            setRefreshToken(servletResponse, retUser, jwtProvider.createRefreshToken());
        }
        return new Oauth2Res(alreadyJoined, alreadySetted, retUser);
    }

    public List<UserMbti> getUserMbti() {
        return userRepository.findAllUserMbtiWithCount();
    }

    public List<UserMbti> getUserMbtiFromCategoryId(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new TagNotFoundException(TagErrorCode.NOT_FOUND)
        );
        return userRepository.findUserMbtiFromCategory(tag);
    }

    public boolean duplicationCheckNickName(String nickName) {
        return userRepository.existsUserByNickName(nickName);
    }

    @Transactional
    public UserRes setUserProfile(ProfileReq req) {
        User user = jwtUserParser.getUser();
        User savedUser = userRepository.findByEmailAndTransactionId(user.getEmail(), user.getTransactionId()).get();
        savedUser.setMbti(req.getMbti());
        savedUser.setNickName(req.getNickName());
        savedUser.setAge(req.getAge());
        savedUser.setGender(req.getGender());
        return new UserRes(savedUser);
    }

    private void setRefreshToken(HttpServletResponse servletResponse, User user, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setMaxAge(REFRESH_TOKEN_VALIDSECOND);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        // TODO: 리프레쉬 가능 유일 경로 설정 (프론트의 경로에 따라 접근이 달라져서 프론트 개발시 참조)
        cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
        servletResponse.addCookie(cookie);

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenRegDate(dateUtil.getNowLocalDateTime());
    }

    private void setAccessToken(HttpServletResponse servletResponse, String token) {
        servletResponse.addHeader(ACCESS_TOKEN, token);
        servletResponse.addHeader("Access-Control-Expose-Headers", "access-token");
    }

    @Transactional
    public void getAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse servletResponse) {
        String token = jwtProvider.resolveToken(request, "Bearer");
        Long userId = jwtProvider.getUserId(token);
        User user = userRepository.getById(userId);

        verifyRefreshToken(refreshToken, user);

        setAccessToken(servletResponse, jwtProvider.createAccessToken(user));
        setRefreshToken(servletResponse, user, jwtProvider.createRefreshToken());
    }

    private void verifyRefreshToken(String refreshToken, User user) {
        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new TokenNotEqualsException(TokenErrorCode.NOT_EQUALS);
        }

        LocalDateTime refreshTokenRegDate = user.getRefreshTokenRegDate();
        LocalDateTime validLocalDateTime = refreshTokenRegDate.plusSeconds(REFRESH_TOKEN_VALIDSECOND);
        LocalDateTime nowLocalDateTime = dateUtil.getNowLocalDateTime();

        if (validLocalDateTime.isBefore(nowLocalDateTime)) {
            throw new TokenExpiredException(TokenErrorCode.EXPIRED);
        }
    }

    @Transactional
    public UserSelfRes updateUser(UpdateUserReq updateUserReq) {
        User user = jwtUserParser.getUser();

        if (!StringUtils.isEmpty(updateUserReq.getNickname())) {
            user.setNickName(updateUserReq.getNickname());
        }
        if (!StringUtils.isEmpty(updateUserReq.getMbti())) {
            user.setMbti(updateUserReq.getMbti());
        }
        if (!(updateUserReq.getGender() == null)) {
            user.setGender(updateUserReq.getGender());
        }
        if (!(updateUserReq.getAge() == null)) {
            user.setAge(updateUserReq.getAge());
        }
        User save = userRepository.save(user);
        return new UserSelfRes(save);
    }

    @Transactional
    public Boolean unregister() {
        User user = userRepository.findById(jwtUserParser.getUser().getId()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));

        user.delete();

        return true;
    }
}