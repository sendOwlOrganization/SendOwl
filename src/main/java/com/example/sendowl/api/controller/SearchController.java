package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.sendowl.domain.board.dto.BoardDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/search")
public class SearchController {

    private final BoardService boardService;

    @Operation(summary = "검색 조회", description = "원하는 쿼리, 분류를 통해 검색한다." +
            "http://localhost:8080/api/search?page=0&size=10&sort=id,DESC&query=sendowl&type=title")

    @GetMapping // 게시글 목록
    public ResponseEntity<BoardsRes> boards(Pageable pageable,
                                             @RequestParam(name = "query", defaultValue = "mbti") String query,
                                             @RequestParam(name = "where", defaultValue = "title/content/nickName") String where
    ){
        return new ResponseEntity(boardService.getBoardBySearch(pageable, where, query), HttpStatus.OK);
    }
}
