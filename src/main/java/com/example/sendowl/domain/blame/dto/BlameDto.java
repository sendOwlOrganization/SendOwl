package com.example.sendowl.domain.blame.dto;

import com.example.sendowl.domain.blame.Blame;
import com.example.sendowl.domain.board.entity.Board;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}