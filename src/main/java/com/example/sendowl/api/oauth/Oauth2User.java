package com.example.sendowl.api.oauth;

import lombok.Getter;

@Getter
public class Oauth2User {
    protected String name;
    protected String email;
    protected String transactionId;

    public Oauth2User(String name, String email, String transactionId) {
        this.name = name;
        this.email = email;
        this.transactionId = transactionId;
    }
}
