package com.example.sendowl.domain.blame.dto;

import com.example.sendowl.domain.blame.entity.BlameContentType;
import com.example.sendowl.domain.blame.entity.BlameType;
import lombok.Getter;

public class BlameDto {

    @Getter
    public static class BlameReq {
        private Long blameTypeId;
        private String blameDetails;
        private Long targetUserId;
        private BlameContentType blameContentType;
        private Long contentId;
        private String contentDetails;
    }

    @Getter
    public static class BlameTypeReq {
        private String blameTypeName;

        public BlameType toEntity() {
            return BlameType.builder().name(blameTypeName).build();
        }

    }

    @Getter
    public static class BlameTypeRes {
        private final Long blameTypeId;
        private final String blameTypeName;

        public BlameTypeRes(BlameType entity) {
            this.blameTypeId = entity.getId();
            this.blameTypeName = entity.getName();
        }
    }

    @Getter
    public static class BlameTypeUpdateReq {
        private Long blameTypeId;
        private String blameTypeName;
    }
}