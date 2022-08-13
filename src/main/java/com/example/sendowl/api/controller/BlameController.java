package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.BlameService;
import com.example.sendowl.domain.blame.dto.BlameDto;
import com.example.sendowl.domain.board.dto.BoardDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/blame")
public class BlameController {
    private final BlameService blameService;

    @Operation(summary = "신고하기", description = "부적절한 글이나 댓글을 신고할 때 사용한다.")
    @PostMapping(path = "") // 게시글 목록
    public ResponseEntity<?> blame(final @Valid @RequestBody BlameDto.BlameReq rq){
        blameService.insertBlame(rq);
        
        return new ResponseEntity(null,HttpStatus.OK);
    }
}
