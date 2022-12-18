package com.example.sendowl.api.oauth;

import lombok.Getter;

import java.util.Map;

@Getter
public class GoogleUser extends Oauth2User {
    private final String sub;
    private final String givenName;
    private final String familName;
    private final String pictureUrl;
    private final String locale;

    public GoogleUser(Map<String, String> res) {
        super(res.get("name"), res.get("email"), "google");
        this.sub = res.get("sub");
        this.givenName = res.get("givenName");
        this.familName = res.get("familName");
        this.pictureUrl = res.get("pictureUrl");
        this.locale = res.get("locale");
    }
}
