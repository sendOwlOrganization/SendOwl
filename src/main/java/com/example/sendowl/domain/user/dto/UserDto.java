package com.example.sendowl.domain.user.dto;

import com.example.sendowl.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

public class UserDto {

    @Data
    public static class UserRes {
        private Long id;
        private String email;
        private String name;
        private String nickName;
        private String introduction;
        private String profileImage;

        public UserRes(User entity) {
            this.id = entity.getId();
            this.email = entity.getEmail();
            this.name = entity.getName();
            this.nickName = entity.getNickName();
            this.introduction = entity.getIntroduction();
            this.profileImage = entity.getProfileImage();
        }
    }

    @Data
    public static class JoinReq {
        private String email;
        private String password;
        private String name;
        private String nickName;
        private String introduction;
        private String profileImage;
    }

    @Data
    public static class JoinRes {
        private Long id;
        private String email;
        private String name;
        private String nickName;
        private String introduction;
        private String profileImage;

        public JoinRes(User entity) {
            this.id = entity.getId();
            this.email = entity.getEmail();
            this.name = entity.getName();
            this.nickName = entity.getNickName();
            this.introduction = entity.getIntroduction();
            this.profileImage = entity.getProfileImage();
        }
    }

    @Data
    public static class LoginReq {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class LoginRes {
        private String accessToken;
        private String refreshToken;
    }
}
