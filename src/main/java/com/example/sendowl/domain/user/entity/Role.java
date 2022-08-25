package com.example.sendowl.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN","관리자"),
    USER("USER","사용자");

    private final String key;
    private final String title;

    @Override
    public String getAuthority() {
        return key;
    }
}