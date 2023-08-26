package com.example.sendowl.domain.tag.dto;

import com.example.sendowl.domain.tag.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TagDto {

    @Getter
    @NoArgsConstructor
    public static class TagsRes {
        private Long id;
        private String name;

        public TagsRes(Tag entity) {
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TagsCountRes {
        private Long id;
        private String name;
        private Long count;

        public TagsCountRes(TagCount dto) {
            this.id = dto.getTagId();
            this.name = dto.getName();
            this.count = dto.getCount();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TagInsertReq {
        @NotBlank
        @Schema(description = "태그 이름", nullable = false, example = "기타")
        private String name;

        public Tag toEntity() {
            return new Tag(name);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagUpdateReq {
        @NotNull
        @Schema(description = "태그 id", nullable = false, example = "1")
        private Long id;
        @NotBlank
        @Schema(description = "태그 이름", nullable = false, example = "기타")
        private String name;

        public Tag toEntity() {
            return new Tag(name);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TagDeleteReq {
        @NotNull
        private Long id;
    }
}
