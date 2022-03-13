package com.example.sendowl.service;

//서비스 계층에서는 JPA를 통해 원하는 데이터를 가져오는 역할을 한다.

import com.example.sendowl.entity.Member;
import com.example.sendowl.excption.MemberNotFoundException;
import com.example.sendowl.repository.MemberRepository;
import com.example.sendowl.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor // JPA관련 인스턴스를 사용해야하기 때문에 선언이 필요하다.
public class MemberService {

    private final MemberRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    public Member addMember(final String memId, final String memPw, final String memName, final String memEmail){
        // 중복 검증
        // 유효성 검증
        // 저장
        final Member savedUser = Member.builder()
                .memId(memId)
                .memPw(passwordEncoder.encode(memPw)) // 암호화
                .memName(memName)
                .memEmail(memEmail)
                .roles(Collections.singletonList("ROLE_USER"))//최초가입시 USER로 설정
                .build();
        System.out.println(savedUser.toString());
        userRepository.save(savedUser);
        return savedUser;
    }
    public String loginMember(final String memEmail, final String memPw){
        Member savedMember = userRepository.findByMemEmail(memEmail).orElseThrow(()->new IllegalArgumentException("가입되지 않은 Email입니다."));
        System.out.println(savedMember.toString());
        System.out.println(memPw);
        if(!passwordEncoder.matches(memPw, savedMember.getPassword() )){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        System.out.println("test!!");
        return jwtProvider.createToken(savedMember.getMemEmail(), savedMember.getRoles());
    }
    public Member getMember(final Long id){
        // 데이터를 들고온다
        Member user = userRepository.findById(id).get();

        if(user == null){
            System.out.println("db로 받아온 값이 null");
        }
        return user;
    }
}
