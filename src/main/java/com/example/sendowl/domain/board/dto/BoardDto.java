package com.example.sendowl.domain.board.dto;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.user.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.sendowl.domain.user.dto.UserDto.UserPublicRes;

public class BoardDto {

    @Getter
    public static class BoardsRes {
        private List<ListRes> boards;
        private Long totalElement;
        private Integer totalPages;
        private Pageable pageable;

        public BoardsRes(Page<Board> pages) {
            this.boards = pages.get().map(ListRes::new).collect(Collectors.toList());
            this.totalElement = pages.getTotalElements();
            this.totalPages = pages.getTotalPages();
            this.pageable = pages.getPageable();
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
        private UserPublicRes user;
        private LocalDateTime regDate;
        private Integer hit;

        public DetailRes(Board entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.user = new UserPublicRes(entity.getUser());
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
        }
    }

    @Getter
    public static class ListRes {
        private Long id;
        private String title;
        private String content;
        private String nickname;
        private LocalDateTime regDate;
        private Integer hit;

        public ListRes(Board entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            if(entity.getContent().length() > 100) {
                this.content = entity.getContent().substring(0, 100);
            }
            this.nickname = entity.getUser().getNickName();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
        }
    }

    @Data
    @NoArgsConstructor
    public static class UpdateReq {
        @NotBlank
        private Long id;
        @NotBlank
        private String title;
        @NotBlank
        private String content;

        @NotNull(message = "카테고리 아이디가 올바르지 않습니다.")
        private Long categoryId;

    }

    @Getter
    public static class UpdateRes {
        private Long id;
        private String title;
        private String content;
        private UserPublicRes user;
        private LocalDateTime regDate;
        private Integer hit;

        public UpdateRes(Board entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.user = new UserPublicRes(entity.getUser());
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
        }
    }
}