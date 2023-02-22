package com.example.sendowl.domain.board.repository;


import com.example.sendowl.domain.board.dto.PreviewBoardDto;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "where b.category_id=:categoryId",
            countQuery = "SELECT count(*) FROM board b where b.category_id=:categoryId",
            nativeQuery = true)
    List<PreviewBoardDto> findPreviewBoard(Long categoryId, Integer titleLength, Pageable pageable);

    @Query(value = "SELECT b FROM Board b join fetch b.user",
            countQuery = "SELECT COUNT(b) FROM Board b")
    Page<Board> findAllFetchJoin(Pageable pageable);

    @Query(value = "SELECT b FROM Board b join fetch b.user where b.isDeleted=false ",
            countQuery = "SELECT COUNT(b) FROM Board b where b.isDeleted=false"
    )
    Page<Board> findBoardFetchJoin(Pageable pageable);

    @Query(value = "SELECT b FROM Board b join fetch b.user where b.category.id = :categoryId",
            countQuery = "SELECT COUNT(b) FROM Board b where b.category.id = :categoryId")
    Page<Board> findAllByCategoryIdFetchJoin(Long categoryId, Pageable pageable);

    @Query(value = "SELECT b FROM Board b join fetch b.user where b.category.id = :categoryId and b.isDeleted=false",
            countQuery = "SELECT COUNT(b) FROM Board b where b.category.id = :categoryId and b.isDeleted=false")
    Page<Board> findBoardByCategoryIdFetchJoin(Long categoryId, Pageable pageable);

    Page<Board> findByTitleContaining(Pageable pageable, String text);

    Page<Board> findByContentContaining(Pageable pageable, String text);

    Optional<Long> countByUserAndRegDateBetween(User user, LocalDateTime today, LocalDateTime tomorrow);
}

