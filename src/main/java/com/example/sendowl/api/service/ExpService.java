package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.comment.repository.CommentRepository;
import com.example.sendowl.domain.exp.entity.ExpHistory;
import com.example.sendowl.domain.exp.entity.ExpType;
import com.example.sendowl.domain.exp.entity.ExpTypeSize;
import com.example.sendowl.domain.exp.repository.ExpHistoryRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ExpHistoryRepository expHistoryRepository;
    private final DateUtil dateUtil;

    /*
    하루에 1번 가능
    TODO : 유저의 마지막 로그인 날짜를 가지고 있어야 비교가 가능하다.
     문제는 계속 토큰이 만료되어 다시 토큰을 갱신하는 경우 마지막 로그인 날짜를 갱신하는 형태로 수정해야한다. (만료에 대한 정책을 정해야함.)
     */
    @Transactional
    public void addExpLogin(User retUser) {
        try {
            LocalDateTime today = dateUtil.getTodayLocalDateTime();
            LocalDateTime tomorrow = dateUtil.getTomorrowLocalDateTime();

            Optional<User> opUser = userRepository.findUserByIdAndModDateBetween(retUser.getId(), today, tomorrow);
            if (opUser.isEmpty()) { // 첫 로그인일 경우
                retUser.addExp(ExpTypeSize.LOGIN.getExp());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
    하루에 2번 가능
     */
    @Transactional
    public void addExpBoard(User jwtUser) {
        try {
            System.out.println("[start]addExpBoard");

            LocalDateTime today = dateUtil.getTodayLocalDateTime();
            LocalDateTime tomorrow = dateUtil.getTomorrowLocalDateTime();

            Long count = boardRepository.countByUserAndRegDateBetween(jwtUser, today, tomorrow).orElseThrow(
                    () -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND)
            );
            System.out.println(count);

            if (count < 3) {
                expHistoryRepository.save(
                        ExpHistory.builder()
                                .addExp(ExpTypeSize.BOARD.getExp())
                                .type(ExpType.BOARD)
                                .user(jwtUser)
                                .build()
                );
                User user = userRepository.getById(jwtUser.getId());
                user.addExp(5L);
            }
            System.out.println("[end]addExpBoard");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
    하루에 5번 가능
     */
    @Transactional
    public void addExpComment(User jwtUser) {
        try {
            System.out.println("[start]addExpComment");

            LocalDateTime today = dateUtil.getTodayLocalDateTime();
            LocalDateTime tomorrow = dateUtil.getTomorrowLocalDateTime();

            Long count = commentRepository.countByUserAndRegDateBetween(jwtUser, today, tomorrow).orElseThrow(
                    () -> new BoardNotFoundException(BoardErrorCode.NOT_FOUND)
            );

            if (count < 6) {
                expHistoryRepository.save(
                        ExpHistory.builder()
                                .addExp(ExpTypeSize.COMMENT.getExp())
                                .type(ExpType.BOARD)
                                .user(jwtUser)
                                .build()
                );
                User user = userRepository.getById(jwtUser.getId());
                user.addExp(2L);
            }
            System.out.println("[end]addExpComment");
        } catch (Exception ex) {

        }

    }

    /*
    좋아요를 받는 경우
     */
    @Transactional
    public void addExpGetLike(User user) {
        user.addExp(ExpTypeSize.LIKE.getExp());
    }
}
