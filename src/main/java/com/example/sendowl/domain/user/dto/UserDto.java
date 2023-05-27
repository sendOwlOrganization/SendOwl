package com.example.sendowl.domain.user.dto;

import com.example.sendowl.domain.user.entity.Gender;
import com.example.sendowl.domain.user.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    @Data
    public static class UserRes {
        private Long id;
        private String nickName;
        private String mbti;
        private Gender gender;
        private Integer age;
        private String email;

        public UserRes(User entity) {
            this.id = entity.getId();
            this.nickName = entity.getNickName();
            this.mbti = entity.getMbti();
            this.gender = entity.getGender();
            this.age = entity.getAge();
            this.email = entity.getEmail();
        }

        public UserRes() {
        }
    }

    @Data
    public static class UserPublicRes {
        private Long id;
        private String nickName;
        private String mbti;
        private String profileImage;

        public UserPublicRes(User entity) {
            this.id = entity.getId();
            this.nickName = entity.getNickName();
            this.mbti = entity.getMbti();
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
        private String mbti;
        private String introduction;
        private String profileImage;

        public JoinRes(User entity) {
            this.id = entity.getId();
            this.email = entity.getEmail();
            this.name = entity.getName();
            this.nickName = entity.getNickName();
            this.mbti = entity.getMbti();
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

        public LoginReq(String email, String password) {
            this.email = email;
            this.password = password;
        }

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

    @Getter
    public static class Oauth2Res {
        private final Boolean alreadyJoined;
        private final Boolean alreadySetted;
        private final UserRes userRes;

        public Oauth2Res(Boolean alreadyJoined, Boolean alreadySetted, User user) {
            this.alreadyJoined = alreadyJoined;
            this.alreadySetted = alreadySetted;
            this.userRes = new UserRes(user);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileReq {
        @Pattern(regexp = "[EI][SN][TF][JP]")
        private String mbti;
        @Pattern(regexp = "[a-zA-Zㄱ-ㅎ|ㅏ-ㅣ|가-힣]{1,60}")
        private String nickName;
        @Range(min = 5, max = 100)
        private Integer age;
        private Gender gender;
    }

    @Getter
    public static class UserSelfRes {
        private Long id;
        private String nickName;
        private String mbti;
        private Gender gender;
        private Integer age;
        private String email;
        private String profileImage;
        private String introduction;

        public UserSelfRes(User user) {
            this.id = user.getId();
            this.nickName = user.getNickName();
            this.mbti = user.getMbti();
            this.gender = user.getGender();
            this.age = user.getAge();
            this.email = user.getEmail();
            this.profileImage = user.getProfileImage();
            this.introduction = user.getIntroduction();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class UpdateUserReq {
        private String nickname;
        @Pattern(regexp = "[EI][SN][FT][JP]")
        private String mbti;
        @Size(min = 0, max = 100)
        private Integer age;
        private Gender gender;
    }
}
