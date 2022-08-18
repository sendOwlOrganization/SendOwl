package com.example.sendowl.user;

import com.example.sendowl.api.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void checkUserNickName(){
        boolean res = userService.duplicationCheckNickName("t1");
        Assertions.assertThat(res).isEqualTo(true);
    }
}
