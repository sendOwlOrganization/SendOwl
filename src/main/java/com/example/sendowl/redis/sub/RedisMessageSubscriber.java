package com.example.sendowl.redis.sub;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.redis.enums.RedisEnum;
import com.example.sendowl.redis.template.RedisBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

import static com.example.sendowl.domain.board.exception.enums.BoardErrorCode.*;

// MessageListener를 이용하여 redis에서 pub되는 메세지는 받는다.
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {
    private final RedisBoard redisBoard;
    private final BoardRepository boardRepository;

    @Override
    public void onMessage(Message message, byte[] bytes) { // Callback for processing received objects through Redis.
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String event = channel.split(":")[1];

        if (event.equals(RedisEnum.EXPIRE)) {
            System.out.println(channel);
            System.out.println(body);
            System.out.println(event);

            String[] bodyData = body.split(":");
            String prefix = bodyData[0];
            String key = bodyData[1];

            switch (key) {
                case RedisEnum.BOARD:
                    String id = bodyData[2];
                    handleRedisBoardExpired(Long.parseLong(id));
                    break;
            }
        }
    }

    private void handleRedisBoardExpired(Long id) {
        Long hit = Long.parseLong(redisBoard.getHit(id).orElseThrow(NullPointerException::new));

        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException(NOT_FOUND));
        board.setHit((int) (board.getHit() + hit));
        boardRepository.save(board);

        redisBoard.delete(id);
    }
}

