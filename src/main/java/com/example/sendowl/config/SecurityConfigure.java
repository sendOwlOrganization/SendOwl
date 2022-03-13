package com.example.sendowl.config;

import com.example.sendowl.util.JwtAuthenticationFilter;
import com.example.sendowl.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

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
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// JWT를 사용하므로 세션은 막는다.
                .and()
                .authorizeRequests()// 사용권한 체크
                .antMatchers("/api/mem/**").permitAll()
                .antMatchers("/api/admin/**").permitAll()
                .anyRequest().hasRole("USER") // 해당 요청만 가능 이외 요청은 인증된 회원만 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class);// jwt인증필터를 UsernamePasswrodAuthenticationFilter.call전에 넣는다.

//        http
//                .authorizeRequests()
//                .antMatchers("/mem/**","/swagger-ui/**").permitAll() // 해당 URI만 허용한다. permitAll은 무조건 허용
//                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // swagger관련 url에 대해서는 예외 처리
        web.ignoring().antMatchers("/v2/api-docs","/swagger-resources/**","/swagger-ui.html","/webjars/**", "/swagger/**");
    }
}
