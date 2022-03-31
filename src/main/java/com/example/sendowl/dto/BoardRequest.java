package com.example.sendowl.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class BoardRequest {
    @NotNull
    private final Long id;

    @NotNull
    private final String title;

    @NotNull
    private final String content;

    @NotNull
    private final Long regId;
}
