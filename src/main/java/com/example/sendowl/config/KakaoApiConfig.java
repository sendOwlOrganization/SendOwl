package com.example.sendowl.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("kakao-oauth")
public class KakaoApiConfig {
    private String clientId;
    private String clientSecret;
    private String authorizationGrantType;
    private String redirectUri;
    private String clientAuthenticationMethod;
    private String authorizationUri;
    private String tokenUri;
    private String userInfoUri;
}
