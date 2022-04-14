package com.example.sendowl.domain.board.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BoardResponse {
    @NotNull
    private final String id;

    @NotNull
    private final Long regId;

    @NotNull
    private final String title;

    @NotNull
    private final String content;

}
