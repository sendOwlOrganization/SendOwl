package com.example.sendowl.service;

//서비스 계층에서는 JPA를 통해 원하는 데이터를 가져오는 역할을 한다.

import com.example.sendowl.entity.User;
import com.example.sendowl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // JPA관련 인스턴스를 사용해야하기 때문에 선언이 필요하다.
public class UserService {

    private final UserRepository userRepository;


    public User addUser(final String userId, final String password, final String username, final String email){
        // 중복 검증
        // 유효성 검증

        // 저장
        final User savedUser = User.builder().userId(userId).password(password).username(username).email(email).build();
        userRepository.save(savedUser);
        return savedUser;
    }
    public User getUser(final Long id){
        // 데이터를 들고온다
        User user = userRepository.findById(id).get();

        if(user == null){
            System.out.println("db로 받아온 값이 null");
        }
        return user;
    }
}
