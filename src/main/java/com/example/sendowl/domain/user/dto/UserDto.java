package com.example.sendowl.domain.user.dto;

import com.example.sendowl.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    public static class UserPublicRes {
        private Long id;
        private String nickName;
        private String profileImage;

        public UserPublicRes(User entity) {
            this.id = entity.getId();
            this.nickName = entity.getNickName();
            this.profileImage = entity.getProfileImage();
        }
    }

    @Data
    public static class JoinReq {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$",
                message = "비밀번호는 하나 이상의 대문자, 소문자, 숫자 및 특수 문자로 이루어진 6 ~ 10자이여야 합니다.")
        private String password;
        @NotBlank
        @Pattern(regexp = "^[가-힣]{2,4}$",
                message = "이름은 2 ~ 4자의 한글이여야 합니다.")
        private String name;
        private String nickName;
        private String introduction;
        private String profileImage;

        public User toEntity(String encodedPassword) {
            return User.builder()
                    .name(name)
                    .nickName(nickName)
                    .email(email)
                    .password(encodedPassword)
                    .introduction(introduction)
                    .profileImage(profileImage)
                    .build();
        }
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
    @Builder
    public static class LoginReq {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
    }

    @Data
    public static class EmailCheckReq {
        @Email
        private String email;
    }

    @Data
    public static class EmailCheckRes {
        private String message;

        public EmailCheckRes success() {
            this.message = "인증 코드 발송 완료";
            return this;
        }
    }

    @Data
    @AllArgsConstructor
    public static class EmailVerifyRes {
        private Boolean isVerified;
    }

    @Data
    public static class EmailVerifyReq {
        private String email;
        private String token;
    }
    @Getter
    @Builder
    public static class Oauth2Req {
        @NotBlank
        private String transactionId;
        @NotBlank
        private String token;
    }
}
