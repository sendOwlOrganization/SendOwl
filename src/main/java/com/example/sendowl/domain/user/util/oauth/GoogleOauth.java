package com.example.sendowl.domain.user.util.oauth;

import com.example.sendowl.domain.user.dto.Oauth2User;
import org.springframework.http.*;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Component
public class GoogleOauth implements Oauth{

    public Oauth2User getOauth2User(String token){

        final String URL = "https://www.googleapis.com/oauth2/v3/userinfo";
        ResponseEntity<Map> response = null;
        response = Oauth.requestApi(HttpMethod.GET,URL,null,null,token);

        Oauth2User oauth2User = null;

        oauth2User = Oauth2User.builder()
                .name(Objects.requireNonNull(response.getBody()).get("name").toString())
                .email(Objects.requireNonNull(response.getBody()).get("email").toString())
                .transactionId("google")
                .build();

        return oauth2User;

    }

    public boolean disconnectUser(String token){
        return false;
    }
}
