package com.example.sendowl.domain.comment.repository;


import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.comment.dto.CommentDto;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.user WHERE c.board=:board and c.depth=0 and c.delDate is null",
            countQuery = "SELECT COUNT(*) FROM Comment c where c.board =:board and c.depth=0 and c.delDate is null")
    Optional<Page<Comment>> findAllByBoardAndDelDateIsNull(Board board, Pageable pageable);

    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.user WHERE c.board=:board and c.depth=0 and c.delDate is null ORDER BY c.likeCount desc, c.regDate desc")
    Optional<List<Comment>> findAllByBoardOrderByLikeCountDesc(Board board, Pageable pageable);

    // 부모댓글에 따른 대댓글 전체 select
    @Query(value = "SELECT c.comment_id AS commentId," +
            "            c.content," +
            "            c.depth," +
            "            c.parent_id AS parentId," +
            "            u.user_id AS userId," +
            "            u.name," +
            "            u.nick_name AS nickName," +
            "            u.role," +
            "            u.mbti," +
            "            u.age," +
            "            u.gender," +
            "            u.profile_image AS profileImage," +
            "            c.reg_date AS regDate," +
            "            c.mod_date AS modDate," +
            "            cl.clc" +
            "    FROM comment c LEFT JOIN user u ON c.user_id = u.user_id" +
            "                   LEFT JOIN (SELECT comment_id, COUNT(*) as clc FROM comment_like group by comment_id) as cl on c.comment_id = cl.comment_id" +
            "            WHERE c.depth = 1 AND c.delDate is null AND c.parent_id in :commentList",
            nativeQuery = true)
    List<CommentDto.DtoInterface> findAllChildComment(List<Long> commentList);

    // TODO: 런칭 이후진행
    // 초기 로딩시 로딩될 대댓글 갯수 제한 + 더보기 api (pagenation)
    @Query(value = "SELECT" +
            "*" +
            "FROM" +
            "(SELECT c.comment_id AS commentId," +
            "        c.content," +
            "        c.depth," +
            "        c.parent_id AS parentId," +
            "        u.user_id AS userId," +
            "        u.name," +
            "        u.nick_name AS nickName," +
            "        u.role," +
            "        u.mbti," +
            "        u.age," +
            "        u.gender," +
            "        cl.clc AS likeCount," +
            "        RANK() OVER (PARTITION BY c.parent_id ORDER BY c.reg_date desc) AS a" +
            "    FROM comment c LEFT JOIN user u ON c.user_id = u.user_id" +
            "                   LEFT JOIN (SELECT comment_id, COUNT(*) as clc FROM comment_like group by comment_id) as cl on c.comment_id = cl.comment_id" +
            "            WHERE c.depth = 1 AND c.parent_id in :commentList ) as rankrow" +
            "            WHERE rankrow.a <= 5",
            nativeQuery = true)
    List<CommentDto.DtoInterface> findChildComment(List<Long> commentList);

    Optional<Long> countByUserAndRegDateBetween(User user, LocalDateTime today, LocalDateTime tomorrow);

    Optional<Comment> findByIdAndDelDateIsNull(Long id);
}

