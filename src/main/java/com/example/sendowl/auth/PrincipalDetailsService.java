package com.example.sendowl.auth;

import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http:localhost:8080/login 요청이 오면
// 해당 서비스의 loadUserByUsername메소드가 동작시키기 위해 Filter에 등록해야한다
// 왜냐하면 SecurityConfig에서 formLogin().disable() 하였기 때문이다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername");
        User userEntity = userRepository.findByEmail(email).get();
        return new PrincipalDetails(userEntity);
    }
}