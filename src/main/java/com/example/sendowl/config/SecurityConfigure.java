package com.example.sendowl.config;

import com.example.sendowl.auth.jwt.JwtAuthenticationFilter;
import com.example.sendowl.auth.jwt.JwtExceptionFilter;
import com.example.sendowl.auth.jwt.JwtProvider;
import com.example.sendowl.domain.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_LIST = {
            // other public endpoints of your API may be appended to this array
            "/api/admin/**",
            "/api/boards/**",
            "/api/comment/**"
    };
    public static final String[] AUTH_WHITELIST = {
            "/api/users/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/**",
            "/v2/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",

    };

    private final JwtProvider jwtProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable() // 사용자 인증방법으로는 HTTP Basic Authentication을 사용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// JWT를 사용하므로 세션은 막는다.
                .and()
                .authorizeRequests()// 사용권한 체크
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(AUTH_LIST).permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().hasRole(Role.USER.getKey()) // 주어진 역할이 있다면 허용 아니면 반환 // userDetailService에서 Authority를 가져올때 자동으로 ROLE을 붙여서 확인한다.
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)// JwtAuthenticationFilter를 JwtAuthenticationFilter 앞에 추가한다.(먼저 실행된다.)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

//        http
//                .authorizeRequests()
//                .antMatchers("/mem/**","/swagger-ui/**").permitAll() // 해당 URI만 허용한다. permitAll은 무조건 허용
//                .anyRequest().authenticated();
    }
}
