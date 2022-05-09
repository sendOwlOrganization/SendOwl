package com.example.sendowl.api.service;

import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.domain.board.dto.BoardDto;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.comment.dto.CommentDto;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.comment.exception.CommentNotFoundException;
import com.example.sendowl.domain.comment.exception.enums.CommentErrorCode;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.comment.repository.CommentRepository;
import com.example.sendowl.domain.user.exception.UserException.*;
import com.example.sendowl.domain.user.exception.enums.UserErrorCode;
import com.example.sendowl.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sendowl.domain.comment.dto.CommentDto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Optional<Comment> insertComment(CommentReq vo) {

        Board board = boardRepository.findById(vo.getBoardId())
                .orElseThrow(()->new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

        User user = userRepository.findByEmail(vo.getEmail())
                .orElseThrow(()->new UserNotFoundException(UserErrorCode.NOT_FOUND));

        Comment comment;

        if(vo.getParentId() != null){
            Comment parentComment = commentRepository.findById(vo.getParentId())
                    .orElseThrow(()->new CommentNotFoundException(CommentErrorCode.NOT_FOUND_PARENT));

            comment = Comment.builder()
                    .board(board)
                    .user(user)
                    .content(vo.getContent())
                    .parent(parentComment)
                    .depth(1L)
                    .build();
        } else {
            comment = Comment.builder()
                    .board(board)
                    .user(user)
                    .content(vo.getContent())
                    .depth(0L)
                    .build();
        }


        Optional<Comment> savedComment = Optional.ofNullable(this.commentRepository.save(comment));

        return savedComment;
    }

    public List<CommentRes> selectCommentList(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));
        List<Comment> comments = commentRepository.findAllByBoard(board).orElseThrow(
                ()-> new BoardNotFoundException(CommentErrorCode.NOT_FOUND));

        List<CommentRes> commentList = new ArrayList<>();

        for(Comment crs : comments) {
            Long childCnt = commentRepository.countByParentIdAndIsDelete(crs.getId(), true);

            CommentRes temp = new CommentRes(crs);

            temp.setChildCnt(childCnt);
            commentList.add(temp);
        }

        return commentList;
    }

    @Transactional
    public Optional<Comment> updateComment(updCommentReq crq) {

        Optional<Comment> updComment = Optional.ofNullable(this.commentRepository.findById(crq.getCommentId()).orElseThrow(
                () -> new CommentNotFoundException(CommentErrorCode.NOT_FOUND)));

        updComment.ifPresent(c -> {
            c.updateContent(crq.getContent());

            this.commentRepository.save(c);

        });

        return updComment;
    }

    @Transactional
    public Optional<Comment> deleteComment(Long commentId) {

        Optional<Comment> delComment = Optional.ofNullable(this.commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException(CommentErrorCode.NOT_FOUND)));

        delComment.ifPresent(c -> {
            c.deactivate();

            this.commentRepository.save(c);

        });

        return delComment;
    }
}
