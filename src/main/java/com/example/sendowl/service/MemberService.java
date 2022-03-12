package com.example.sendowl.service;

//서비스 계층에서는 JPA를 통해 원하는 데이터를 가져오는 역할을 한다.

import com.example.sendowl.entity.Member;
import com.example.sendowl.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // JPA관련 인스턴스를 사용해야하기 때문에 선언이 필요하다.
public class MemberService {

    private final MemberRepository userRepository;


    public Member addMember(final String userId, final String password, final String username, final String email){
        // 중복 검증
        // 유효성 검증

        // 저장
        final Member savedUser = Member.builder().memId(userId).memPw(password).memName(username).memEmail(email).build();
        userRepository.save(savedUser);
        return savedUser;
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
