package com.example.sendowl.domain.user.util.oauth;

import com.example.sendowl.domain.user.dto.Oauth2User;
import com.example.sendowl.domain.user.exception.Oauth2Exception;
import com.example.sendowl.domain.user.exception.enums.Oauth2ErrorCode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Component
public class GoogleOauth implements Oauth{

    public Oauth2User getOauth2User(String token){

        final String url = "https://www.googleapis.com/oauth2/v3/userinfo";
        ResponseEntity<Map> response = null;
        Oauth2User oauth2User = null;


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity("",headers);

        try{
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        }catch(HttpStatusCodeException e){
            throw new Oauth2Exception.TokenNotValid(Oauth2ErrorCode.UNAUTHORIZED);
        }

        oauth2User = Oauth2User.builder()
                .name(Objects.requireNonNull(response.getBody()).get("name").toString())
                .email(Objects.requireNonNull(response.getBody()).get("email").toString())
                .transactionId("google")
                .build();

        return oauth2User;
    }

}
