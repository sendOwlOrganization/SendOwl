package com.example.sendowl.service;

//서비스 계층에서는 JPA를 통해 원하는 데이터를 가져오는 역할을 한다.

import com.example.sendowl.entity.user.User;
import com.example.sendowl.excption.MemberNotValidException;
import com.example.sendowl.repository.UserRepository;
import com.example.sendowl.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor // JPA관련 인스턴스를 사용해야하기 때문에 선언이 필요하다.
public class MemberService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    public User addMember(final String memId, final String memPw, final String memName, final String memEmail){
        // 중복 검증
        if(userRepository.findByMemEmail(memEmail).isPresent()){
            throw new MemberNotValidException("이미 가입된 이메일 입니다.");
        }
        if(userRepository.findByMemId(memId).isPresent()){
            throw new MemberNotValidException("이미 가입된 아이디 입니다.");
        }

        // 저장
        final User savedUser = User.builder()
                .memId(memId)
                .memPw(passwordEncoder.encode(memPw)) // 암호화
                .memName(memName)
                .memEmail(memEmail)
                .roles(Collections.singletonList("ROLE_USER"))//최초가입시 USER로 설정 // 사이즈가 하나인 경우에 Collections.sigletonList를 사용한다. 왜냐면 ArraysList는 sigleton보다 커서
                .build();
        System.out.println(savedUser.toString());
        userRepository.save(savedUser);
        return savedUser;
    }
    public String loginMember(final String memEmail, final String memPw){
        User savedUser = userRepository.findByMemEmail(memEmail).orElseThrow(()->new IllegalArgumentException("가입되지 않은 Email입니다."));
        System.out.println(savedUser.toString());
        System.out.println(memPw);
        if(!passwordEncoder.matches(memPw, savedUser.getPassword() )){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        System.out.println("test!!");
        return jwtProvider.createToken(savedUser.getMemEmail(), savedUser.getRoles());
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
