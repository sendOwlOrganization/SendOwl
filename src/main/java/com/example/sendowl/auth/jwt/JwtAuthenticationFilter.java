package com.example.sendowl.auth.jwt;

import com.example.sendowl.auth.exception.JwtInvalidException;
import com.example.sendowl.auth.exception.JwtNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.sendowl.auth.exception.enums.JwtErrorCode.INVALID;
import static com.example.sendowl.auth.exception.enums.JwtErrorCode.NOT_FOUND;
import static com.example.sendowl.config.SecurityConfig.AUTH_WHITELIST;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean isContain = false;
        for(String antPattern: AUTH_WHITELIST){ // 다양한 whitelist 패턴에 대해 루프를 돌며 패턴 확인한다.
            if(checkAntPattern(antPattern,path)){
                isContain = true;
            }
        }
        return isContain;
    }
    private boolean checkAntPattern(String pattern, String inputStr) {
        return matches(pattern, inputStr);
    }
    private boolean matches(String pattern, String inputStr) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match(pattern, inputStr);
    }

    // request 로 들어오는 Jwt의 유효성을 검증 - JwtProvider.validationToken을 필터로서 FilterChain에 추가
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken((HttpServletRequest) request, "Bearer");
        // 토큰에 대한 인증을 진행한다.
        if(token == null ){
            throw new JwtNotFoundException(NOT_FOUND);
        }
        if(!jwtProvider.validationToken(token)) { // 토큰이 존재하는지 && 토큰의 날짜를 검증
            throw new JwtInvalidException(INVALID);
        }
        Authentication authentication = jwtProvider.getAuthentication(token); // 인증용 객체를 생성한다.
        SecurityContextHolder.getContext().setAuthentication(authentication); // 해당 스레드에서 사용하기 위해 저장한다.
        filterChain.doFilter(request, response); // 필터체인의 다음 체인을 실행하게 한다.
    }
}
