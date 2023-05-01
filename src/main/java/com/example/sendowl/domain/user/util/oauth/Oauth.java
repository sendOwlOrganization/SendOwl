package com.example.sendowl.domain.user.util.oauth;

import com.example.sendowl.domain.user.dto.Oauth2User;

public interface Oauth {
    public Oauth2User getOauth2User(String token);
}
