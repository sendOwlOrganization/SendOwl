package com.example.sendowl.domain.board.dto;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.user.entity.User;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static com.example.sendowl.domain.user.dto.UserDto.*;

public class BoardDto {

    @Getter
    public static class BoardsRes {
        private Long id;
        private UserRes user;
        private String title;
        private String content;
        private LocalDateTime regDate;
        private Integer hit;

        public BoardsRes(Board entity) {
            this.id = entity.getId();
            this.user = new UserRes(entity.getUser());
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
        }
    }

    @Getter // @Data는 사용하지 않는 것을 추천한다고 합니다. 왜냐하면 양방향 호출을 통해 무한루프가 발생할 수 있습니다.
    @NoArgsConstructor
    public static class BoardReq {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$",
                message = "올바른 이메일 형식이 아닙니다.")
        private String email;
        @NotNull(message = "카테고리 아이디가 올바르지 않습니다.") // Long형에는 NotNull을 써야한다고 합니다.
        private Long categoryId;

        public Board toEntity(User user, Category category) {
            return Board.builder()
                    .hit(0)
                    .title(title)
                    .content(content)
                    .user(user)
                    .category(category)
                    .build();
        }
    }

    @Getter
    public static class DetailRes {
        private Long id;
        private String title;
        private String content;
        private UserRes user;
        private Integer hit;

        public DetailRes(Board entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.user = new UserRes(entity.getUser());
            this.content = entity.getContent();
            this.hit = entity.getHit();
        }
    }


}
