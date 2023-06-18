package com.example.sendowl.domain.board.repository;


import com.example.sendowl.domain.board.dto.PreviewBoardDto;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor {

    @Query(value = "SELECT b.board_id as boardId, " +
            "LEFT(b.title,:titleLength) as title, u.user_id as userId, u.nick_name as nickName, u.mbti as mbti, b.reg_date as regDate, " +
            "bl.board_like_count as likeCount, c.comment_count as commentCount " +
            "FROM board b " +
            "left join user u on b.user_id = u.user_id " +
            "left join (select board_id, COUNT(*) as board_like_count " +
            "from board_like " +
            "group by board_id) bl on b.board_id = bl.board_id " +
            "left join (select board_id, COUNT(*) as comment_count " +
            "from comment " +
            "group by board_id) c on b.board_id = c.board_id " +
            "where b.category_id=:categoryId and b.del_date is NULL",
            countQuery = "SELECT count(*) FROM board b where b.category_id=:categoryId",
            nativeQuery = true)
    List<PreviewBoardDto> findPreviewBoard(Long categoryId, Integer titleLength, Pageable pageable);

    Page<Board> findAllByDelDateIsNull(Specification<Board> spec, Pageable pageable);

    Optional<Board> findByIdAndDelDateIsNull(Long boardId);

    @Query(value = "SELECT b FROM Board b join fetch b.user where b.delDate is null",
            countQuery = "SELECT COUNT(b) FROM Board b where b.delDate is null"
    )
    Page<Board> findBoardFetchJoin(Pageable pageable);

    @Query(value = "SELECT b FROM Board b join fetch b.user where b.category.id = :categoryId and b.delDate is null",
            countQuery = "SELECT COUNT(b) FROM Board b where b.category.id = :categoryId and b.delDate is null")
    Page<Board> findBoardByCategoryIdFetchJoin(Long categoryId, Pageable pageable);

    Optional<Long> countByUserAndRegDateBetween(User user, LocalDateTime today, LocalDateTime tomorrow);
}

