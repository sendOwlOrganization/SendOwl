Skip to content
Search or jump to…
Pull requests
Issues
Marketplace
Explore
 
@pis03133 
sendOwlOrganization
/
SendOwl
Public
Code
Issues
2
Pull requests
2
Projects
Wiki
Security
Insights
Settings
SendOwl/src/main/java/com/example/sendowl/config/SecurityConfig.java /
@Dev-Guccin
Dev-Guccin del: unneccessary auth link
Latest commit 83fa665 13 hours ago
 History
 1 contributor
87 lines (77 sloc)  3.93 KB

package com.example.sendowl.config;

import com.example.sendowl.auth.jwt.JwtAuthenticationFilter;
import com.example.sendowl.auth.jwt.JwtExceptionFilter;
import com.example.sendowl.auth.jwt.JwtProvider;
import com.example.sendowl.domain.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // SecurityConfigure에서 한번에 관리하는게 좋을거 같아서 AUTH_WHITELIST를 분리하여 정의해놓았습니다.
    public static final List<String> AUTH_WHITELIST = Collections.unmodifiableList(
            Arrays.asList(
//       -- Swagger UI v3 (OpenAPI)
            "/v3/**",
            "/v2/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",
            "/error"));

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
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable() // 사용자 인증방법으로는 HTTP Basic Authentication을 사용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// JWT를 사용하므로 세션은 막는다.
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)// JwtAuthenticationFilter를 JwtAuthenticationFilter 앞에 추가한다.(먼저 실행된다.)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000"); // 허용할 URL
        configuration.addAllowedHeader("*"); // 허용할 Header
        configuration.addAllowedMethod("*"); // 허용할 Http Method
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
Footer
© 2022 GitHub, Inc.
Footer navigation
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
You have unread notifications