package com.example.sendowl.util.mail;

import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtUserParser {

    public User getUser() {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }

}
