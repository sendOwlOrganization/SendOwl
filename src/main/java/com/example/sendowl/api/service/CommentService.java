package com.example.sendowl.api.service;

import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.comment.exception.CommentNoPermission;
import com.example.sendowl.domain.comment.exception.CommentNotFoundException;
import com.example.sendowl.domain.comment.exception.enums.CommentErrorCode;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sendowl.domain.comment.dto.CommentDto.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentRes insertComment(CommentReq vo, User user) {

        Board board = boardRepository.findById(vo.getBoardId())
                .orElseThrow(()->new BoardNotFoundException(BoardErrorCode.NOT_FOUND));

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
                    .parent(null)
                    .depth(0L)
                    .build();
        }

        Comment savedComment = commentRepository.save(comment);
        CommentRes commentRes = new CommentRes(savedComment);


        return commentRes;
    }

    public List<CommentRes> selectCommentList(Long boardId){
        // 게시글 존재여부 확인
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new BoardNotFoundException(BoardErrorCode.NOT_FOUND));
        // 댓글 select
        List<Comment> comments = commentRepository.findAllByBoard(board).orElseThrow(
                ()-> new BoardNotFoundException(CommentErrorCode.NOT_FOUND));

        List<CommentRes> commentList = new ArrayList<>();

        for(Comment crs : comments) {
            CommentRes temp = new CommentRes(crs);
            commentList.add(temp);
        }

        return commentList;
    }

    @Transactional
    public CommentRes updateComment(UpdateReq crq, User user) {

        Comment updComment = commentRepository.findById(crq.getCommentId()).orElseThrow(
                () -> new CommentNotFoundException(CommentErrorCode.NOT_FOUND));

        // 글쓴 유저가 맞는지 확인하기
        if(updComment.getUser().getId() == user.getId()){
            updComment.updateContent(crq.getContent());
            commentRepository.save(updComment);
            CommentRes commentRes = new CommentRes(updComment);
            return commentRes;
        }
        else{
            throw new CommentNoPermission(CommentErrorCode.NO_PERMISSON);
        }
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment delComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException(CommentErrorCode.NOT_FOUND));

        if(delComment.getUser().getId() == user.getId()){
            delComment.delete();
            commentRepository.save(delComment);
        }
        else{
            throw new CommentNoPermission(CommentErrorCode.NO_PERMISSON);
        }
    }
}
