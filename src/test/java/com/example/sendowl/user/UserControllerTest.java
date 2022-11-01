package com.example.sendowl.user;

import com.example.sendowl.domain.user.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc // 서블릿 컨테이너를 모킹하기 위해서 사용한다.
@SpringBootTest // 테스트에 필요한 거의 모든 의존성을 제공한다.
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addTest() throws Exception {
        // 준비
        String url = "/add?A=1&B=2";
        // 실행
        final ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON));
        // 단언
        actions.andExpect(result -> {
            MockHttpServletResponse response = result.getResponse();
            System.out.println(response.getContentAsString());
            assertEquals("3", response.getContentAsString());
        });
    }

    @Test
    @DisplayName("@Email 에러 테스트")
    public void isInvalidObject() throws Exception {
        // given
        UserDto.LoginReq loginReq = UserDto.LoginReq.builder()
                .email("email")
                .build();
        // when
        String string = objectMapper.writeValueAsString(loginReq);

        // then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(string))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
}
