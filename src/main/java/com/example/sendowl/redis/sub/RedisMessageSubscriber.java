package com.example.sendowl.redis.sub;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.redis.entity.RedisBoard;
import com.example.sendowl.redis.enums.RedisEnum;
import com.example.sendowl.redis.exception.RedisBoardNotFoundException;
import com.example.sendowl.redis.repository.RedisBoardRepository;
import com.example.sendowl.redis.service.RedisEmailTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

import static com.example.sendowl.domain.board.exception.enums.BoardErrorCode.*;

// MessageListener를 이용하여 redis에서 pub되는 메세지는 받는다.

public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    private RedisBoardRepository redisBoardRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private RedisEmailTokenService userTokenService;

    private final String EXPIRED_EVENT = RedisEnum.EXPIRE;

    @Override
    public void onMessage(Message message, byte[] bytes) { // Callback for processing received objects through Redis.
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String event = channel.split(":")[1];

        if (event.equals(EXPIRED_EVENT)) {
            String[] bodyData = body.split(":");
            String key = bodyData[0];
            String id = bodyData[1];

            switch (key) {
                case RedisEnum.BOARD:
                    handleRedisBoardExpired(id);
                    break;
                case RedisEnum.EMAIL_TOKEN:
                    handleRedisUserTokenExpired(id);
                    break;
            }
        }
    }

    private void handleRedisBoardExpired(String id) {
        // 만료된 경우 DB에 조회수를 증가시키는 코드 추가하기
        Long idL = Long.parseLong(id);
        RedisBoard redisboard = redisBoardRepository.findById((idL)).orElseThrow(() -> new RedisBoardNotFoundException(NOT_FOUND));
        Long count = redisboard.getCount();
        Board board = boardRepository.findById(idL).orElseThrow(() -> new BoardNotFoundException(NOT_FOUND));
        board.setHit((int) (board.getHit() + count));
        boardRepository.save(board);

        // 사용완료한 데이터를 제거한다.
        redisBoardRepository.deleteById(idL);
    }

    private void handleRedisUserTokenExpired(String id) {
        userTokenService.deleteById(Long.valueOf(id));
    }
}

