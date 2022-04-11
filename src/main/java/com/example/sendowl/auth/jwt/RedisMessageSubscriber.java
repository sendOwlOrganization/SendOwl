package com.example.sendowl.auth.jwt;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.entity.RedisBoard;
import com.example.sendowl.excption.RedisBoardNotFoundException;
import com.example.sendowl.repository.RedisBoardRepository;
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

    @Override
    public void onMessage(Message message, byte[] bytes) { // Callback for processing received objects through Redis.

        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

        if(channel.split(":")[1].matches("expired")){
            System.out.println("Redis expired event!!");
            String[] bodys = body.split(":");
            String key = bodys[0];
            String tag = bodys[1];
            String id = bodys[2];

            // 만료된 경우 DB에 조회수를 증가시키는 코드 추가하기
            Long idL = Long.parseLong(id);
            RedisBoard redisboard =  redisBoardRepository.findById((idL)).orElseThrow(()->new RedisBoardNotFoundException(NOT_FOUND));
            Long count = redisboard.getCount();
            Board board = boardRepository.findById(idL).orElseThrow(()-> new BoardNotFoundException(NOT_FOUND));
            board.setHit((int) (board.getHit() + count));
            boardRepository.save(board);

            // 사용완료한 데이터를 제거한다.
            redisBoardRepository.deleteById(idL);
            return;
        }
        else{
            return;
        }
   }
}

