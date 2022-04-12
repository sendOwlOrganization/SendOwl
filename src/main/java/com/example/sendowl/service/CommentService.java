package com.example.sendowl.service;

import com.example.sendowl.dto.CommentRequest;
import com.example.sendowl.entity.board.Board;
import com.example.sendowl.entity.comment.Comment;
import com.example.sendowl.entity.user.User;
import com.example.sendowl.excption.BoardNotFoundException;
import com.example.sendowl.excption.CommentNotFoundException;
import com.example.sendowl.excption.MemberNotFoundException;
import com.example.sendowl.repository.BoardRepository;
import com.example.sendowl.repository.CommentRepository;
import com.example.sendowl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

//    public List<Board> getBoardList() {
//        String active = "Y";
//
//        return boardRepository.findByActive(active);
//    }

    public void insertComment(CommentRequest vo) {
        // 보드 객체 찾기
        Board board = boardRepository.findById(vo.getBoardId())
                .orElseThrow(()->new BoardNotFoundException("등록되지 않은 보드입니다.."));
        // 멤버 객체 찾기
        User user = userRepository.findById(vo.getMemberId())
                .orElseThrow(()->new MemberNotFoundException("등록되지 않은 멤버입니다."));

        System.out.println(user.toString());
        System.out.println(board.toString());
        Comment comment = new Comment().builder().board(board)
                .user(user)
                .content(vo.getContent())
                .regDate(LocalDateTime.now())
                .build();
        Comment savedComment = commentRepository.save(comment);
        savedComment.setParentId(savedComment.getId());// 자신의 값을 설정
        savedComment.setDepth(0L);
        savedComment.setOrd(0L);
        System.out.println("댓글 확인" + savedComment.getId().toString());
        commentRepository.flush();
    }
    public void insertNestedComment(CommentRequest vo) {
        // 보드 객체 찾기
        Board board = boardRepository.findById(vo.getBoardId())
                .orElseThrow(()->new BoardNotFoundException("등록되지 않은 보드입니다.."));
        // 멤버 객체 찾기
        User user = userRepository.findById(vo.getMemberId())
                .orElseThrow(()->new MemberNotFoundException("등록되지 않은 멤버입니다."));

        // 부모 댓글 찾기
        Comment parentComment = commentRepository.findById(vo.getParentId())
                .orElseThrow(()->new CommentNotFoundException("등록되지 않은 댓글입니다."));
        // 마지막 자식 찾기
        Comment lastNestedComent = commentRepository
                .findTopByParentIdOrderByOrdDesc(parentComment.getId()).orElse(
                        new Comment().builder()
                                .ord(0L)
                                .build());
        Comment comment = new Comment().builder()
                .board(board)
                .user(user)
                .content(vo.getContent())
                .regDate(LocalDateTime.now())
                .parentId(parentComment.getId())
                .depth(parentComment.getDepth()+1)
                .ord(lastNestedComent.getOrd()+1)
                .build();
        commentRepository.save(comment);
    }

    public List<Comment> selectCommentList(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new BoardNotFoundException("존재하지 않는 보드입니다."));
        List<Comment> comments = commentRepository.findAllByBoard(board).orElseThrow(
                ()-> new BoardNotFoundException("존재하지 않는 보드입니다."));
        return comments;
    }

//    public Board getBoard(long id) {
//
//        return boardRepository.getById(id);
//    }
}
