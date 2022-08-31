package com.example.sendowl.domain.balance.dto;

import com.example.sendowl.domain.blame.entity.Blame;
import com.example.sendowl.domain.blame.entity.BlameType;
import lombok.Getter;

public class BlameDto {

    @Getter
    public static class BlameReq {
        private Long userId;
        private Long blameType;
        private String blameDetails;
        private Long targetUserId;
        private Long contentType;
        private Long contentId;
        private String contentDetails;
        public Blame toEntity(){
            return Blame.builder()
                    .userId(userId)
                    .blameType(blameType)
                    .blameDetails(blameDetails)
                    .targetUserId(targetUserId)
                    .contentsType(contentType)
                    .contentsId(contentId)
                    .contentsDetails(contentDetails).build();
        }
    }

    @Getter
    public static class BlameTypeReq {
        private String name;

        public BlameType toEntity(){
            return BlameType.builder().name(name).build();
        }

    }
    @Getter
    public static class BlameTypeRes {
        private Long id;
        private String name;

        public BlameTypeRes(BlameType entity) {
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }

    @Getter
    public static class BlameTypeUpdateReq {
        private Long id; //  바꿀 아이디
        private String name;
    }
}