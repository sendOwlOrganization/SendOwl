package com.example.sendowl.domain.user.util.oauth;

import com.example.sendowl.domain.user.dto.Oauth2User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface Oauth {
    public Oauth2User getOauth2User(String token);

    public default boolean disconnectUser(String token){
        return false;
    };

    public ResponseEntity<Map> requestApi(String accessToken);

}
