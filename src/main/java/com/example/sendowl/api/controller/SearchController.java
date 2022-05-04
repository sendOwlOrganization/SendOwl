package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.BoardService;
import com.example.sendowl.common.dto.BaseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.sendowl.domain.board.dto.BoardDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/search")
public class SearchController {

    private final BoardService boardService;

    @Operation(summary = "list api", description = "http://localhost:8080/api/search?page=0&size=10&sort=id,DESC&query=sendowl&type=title")
    @GetMapping(path = "") // 게시글 목록
    public BaseResponseDto<BoardsRes> boards(Pageable pageable,
                                             @RequestParam(name = "query", defaultValue = "") String query,
                                             @RequestParam(name = "where", defaultValue = "title") String where
    ){
        return new BaseResponseDto<BoardsRes>(boardService.getBoardBySearch(pageable, where, query));
    }
}
