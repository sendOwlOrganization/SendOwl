package com.example.sendowl.redis.sub;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.redis.enums.RedisEnum;
import com.example.sendowl.redis.exception.RedisBoardNotFoundException;
import com.example.sendowl.redis.repository.RedisBoardRepository;
import com.example.sendowl.redis.service.RedisEmailTokenService;
import com.example.sendowl.redis.template.RedisBoard;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static com.example.sendowl.domain.board.exception.enums.BoardErrorCode.*;

// MessageListener를 이용하여 redis에서 pub되는 메세지는 받는다.
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {
    private final RedisBoard redisBoard;
    private final BoardRepository boardRepository;
    //private final RedisEmailTokenService userTokenService;
    private final String EXPIRED_EVENT = RedisEnum.EXPIRE;

    @Override
    public void onMessage(Message message, byte[] bytes) { // Callback for processing received objects through Redis.
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String event = channel.split(":")[1];
        System.out.println(channel);
        System.out.println(body);
        System.out.println(event);

        if (event.equals(EXPIRED_EVENT)) {
            String[] bodyData = body.split(":");
            String key = bodyData[0];
            String id = bodyData[1];

            switch (key) {
                case RedisEnum.BOARD:
                    handleRedisBoardExpired(Long.parseLong(id));
                    break;
                case RedisEnum.EMAIL_TOKEN:
                    handleRedisUserTokenExpired(id);
                    break;
            }
        }
    }

    private void handleRedisBoardExpired(Long id) {
        Long hit = redisBoard.getHit(id);

        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException(NOT_FOUND));
        board.setHit((int) (board.getHit() + hit));
        boardRepository.save(board);

        redisBoard.delete(id);
    }

    private void handleRedisUserTokenExpired(String id) {
//        userTokenService.deleteById(Long.valueOf(id));
    }
}

