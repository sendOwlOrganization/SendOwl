package com.example.sendowl.domain.board.dto;

import java.time.LocalDateTime;

public interface PreviewBoardDto {
    String getBoardId();

    String getTitle();

    String getUserId();

    String getNickName();

    String getMbti();

    LocalDateTime getRegDate();

    Long getLikeCount();

    Long getCommentCount();
}
