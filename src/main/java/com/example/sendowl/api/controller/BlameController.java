package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.BlameService;
import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.domain.blame.dto.BlameDto;
import com.example.sendowl.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/blame")
public class BlameController {
    private final BlameService blameService;

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "신고하기", description = "부적절한 글이나 댓글을 신고할 때 사용한다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping(path = "") // 게시글 목록
    public ResponseEntity<?> blame(final @Valid @RequestBody BlameDto.BlameReq rq){
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();

        blameService.insertBlame(rq, user);

        return new ResponseEntity(null,HttpStatus.OK);
    }
    @Operation(summary = "신고 종류", description = "신고 가능한 종류를 나타낸다.")
    @GetMapping(path = "/type") // 게시글 목록
    public ResponseEntity<?> blameTypeList(){
        List<BlameDto.BlameTypeRes> blameTypeList = blameService.getBlameTypeList();
        blameTypeList.forEach(blameTypeRes -> System.out.println(blameTypeRes));
        return new ResponseEntity(blameTypeList,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "신고 종류 삽입(ADMIN)", description = "신고 가능 종류 하나를 새롭게 삽입한다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping(path = "/type") // 게시글 목록
    public ResponseEntity<?> insertBlameType(final @Valid @RequestBody BlameDto.BlameTypeReq rq){
        blameService.insertBlameType(rq);
        return new ResponseEntity(null,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "신고 종류 수정(ADMIN)", description = "신고 가능한 종류를 수정한다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PutMapping(path = "/type") // 게시글 목록
    public ResponseEntity<?> insertBlameType(final @Valid @RequestBody BlameDto.BlameTypeUpdateReq rq){
        blameService.updateBlameType(rq);
        return new ResponseEntity(null,HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "신고 종류 제거(ADMIN)", description = "신고 가능한 종류 중 하나를 삭제한다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping(path = "/type/{id}") // 게시글 목록
    public ResponseEntity<?> insertBlameType(final @PathVariable Long id){
        blameService.deleteBlameType(id);
        return new ResponseEntity(null,HttpStatus.OK);
    }

}
