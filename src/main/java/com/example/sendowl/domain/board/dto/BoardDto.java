package com.example.sendowl.domain.board.dto;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.sendowl.domain.user.dto.UserDto.UserPublicRes;

public class BoardDto {

    @Getter
    public static class BoardsRes {
        private final List<ListRes> boards;
        private final Long totalElement;
        private final Integer totalPages;
        private final Pageable pageable;

        public BoardsRes(Page<Board> pages, Integer textLength) {
            this.boards = pages.get().map((page) -> new ListRes(page, textLength)).collect(Collectors.toList());
            this.totalElement = pages.getTotalElements();
            this.totalPages = pages.getTotalPages();
            this.pageable = pages.getPageable();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class BoardReq {
        @NotBlank
        private String title;
        private EditorJsContent editorJsContent;

        @NotNull(message = "카테고리 아이디가 올바르지 않습니다.") // Long형에는 NotNull을 써야한다고 합니다.
        private Long categoryId;

        public Board toEntity(User user, Category category, String refinedContent) {
            ObjectMapper objectMapper = new ObjectMapper();
            String value = "";
            try {
                value = objectMapper.writeValueAsString(editorJsContent);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return Board.builder()
                    .hit(0)
                    .title(title)
                    .content(value)
                    .refinedContent(refinedContent)
                    .user(user)
                    .category(category)
                    .build();
        }
    }

    @Getter
    public static class DetailRes {
        private final Long id;
        private final String title;
        private final String content;
        private final UserPublicRes user;
        private final LocalDateTime regDate;
        private final Integer hit;
        private Long boardLikeCount;


        public DetailRes(Board entity, Integer redisHit) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.user = new UserPublicRes(entity.getUser());
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit() + redisHit;
        }

        public DetailRes(Board entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.user = new UserPublicRes(entity.getUser());
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
            this.boardLikeCount = entity.getBoardLikeCount();
        }

    }

    @Getter
    public static class ListRes {
        private final Long id;
        private final String title;
        private final String nickname;
        private final LocalDateTime regDate;
        private final Integer hit;
        private String preview;
        private Long boardLikeCount;

        public ListRes(Board entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.preview = entity.getRefinedContent();
            if (preview.length() > 100) {
                this.preview = preview.substring(0, 100);
            }
            this.nickname = entity.getUser().getNickName();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
        }

        public ListRes(Board entity, Integer textLength) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.preview = entity.getRefinedContent();
            if (preview.length() > textLength)
                this.preview = preview.substring(0, textLength);
            this.nickname = entity.getUser().getNickName();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
            this.boardLikeCount = entity.getBoardLikeCount();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateBoardReq {
        private Long boardId;
        @NotBlank
        private String title;
        @JsonIgnore
        private String content;

        private EditorJsContent editorJsContent;

        @NotNull(message = "카테고리 아이디가 올바르지 않습니다.")
        private Long categoryId;
    }

    @Getter
    public static class UpdateBoardRes {
        private final Long id;
        private final String title;
        private final String content;
        private final UserPublicRes user;
        private final LocalDateTime regDate;
        private final Integer hit;
        private final Long boardLikeCount;

        public UpdateBoardRes(Board entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.user = new UserPublicRes(entity.getUser());
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
            this.boardLikeCount = entity.getBoardLikeCount();
        }
    }

    @Getter
    public static class EditorJsContent {
        private Long time;
        private EditorJsBlock[] blocks;
    }

    @Getter
    public static class EditorJsBlock {
        private String id;
        private String type;
        private EditorJsData data;

    }

    @Getter
    public static class EditorJsData {
        private String text;
        private String[] items;
    }
}