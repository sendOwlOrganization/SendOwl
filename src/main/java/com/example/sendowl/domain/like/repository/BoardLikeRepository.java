package com.example.sendowl.domain.like.repository;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>, JpaSpecificationExecutor {

    Optional<BoardLike> findByUserAndBoard(User user, Board board);

}
