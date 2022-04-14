package com.example.sendowl.api.service;

//서비스 계층에서는 JPA를 통해 원하는 데이터를 가져오는 역할을 한다.

import com.example.sendowl.domain.user.dto.MemberRequest;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserNotValidException;
import com.example.sendowl.domain.user.exception.enums.UserErrorCode;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // JPA관련 인스턴스를 사용해야하기 때문에 선언이 필요하다.
public class MemberService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    public User addMember(final MemberRequest memberRequest){
        // 중복 검증
        if(userRepository.findByEmail(memberRequest.getMemEmail()).isPresent()){
            throw new UserNotValidException(UserErrorCode.NOT_VALID_EMAIL);
        }

        // 저장
        final User savedUser = User.builder()
                .email(memberRequest.getMemEmail())
                .password(passwordEncoder.encode(memberRequest.getMemPw())) // 암호화
                .name(memberRequest.getMemName())
                .build();
        System.out.println(savedUser.toString());
        userRepository.save(savedUser);
        return savedUser;
    }
    public String loginMember(final String memEmail, final String memPw){
        User savedUser = userRepository.findByEmail(memEmail).orElseThrow(()->new IllegalArgumentException("가입되지 않은 Email입니다."));
        System.out.println(savedUser.toString());
        System.out.println(memPw);
        if(!passwordEncoder.matches(memPw, savedUser.getPassword() )){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtProvider.createToken(savedUser.getEmail(), savedUser.getRole());
    }
    public User getMember(final Long id){
        // 데이터를 들고온다
        User user = userRepository.findById(id).get();

        if(user == null){
            System.out.println("db로 받아온 값이 null");
        }
        return user;
    }
}
