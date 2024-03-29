package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.UserService;
import com.example.sendowl.domain.user.dto.UserMbti;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 내부적으로 오브젝트랩퍼 잭슨을 사용한다. 시리얼라이즈를 해서 반환한다. toString이 걸려있으면 객체들을 계속 조회하는 경우가 발생한다.
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
@RequestMapping(path = "/api/mbti")
public class MbtiController {

    final private UserService userService;

    @Operation(summary = "유저들의 mbti 조회", description = "모든 유저들의 mbti 수를 반환한다.")
    @GetMapping("/users")
    public ResponseEntity<List<UserMbti>> getUserMbti() {
        return new ResponseEntity(userService.getUserMbti(), HttpStatus.OK);
    }

    @Operation(summary = "카테고리의 유저 mbti 조회.", description = "카테고리id를 통해 해당 카테고리에 게시글을 쓴 유저들의 mbti 순위(유저수)를 반환한다.")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<UserMbti>> getUserMbtiFromCategory(@PathVariable Long categoryId) {
        return new ResponseEntity(userService.getUserMbtiFromCategoryId(categoryId), HttpStatus.OK);
    }

}
