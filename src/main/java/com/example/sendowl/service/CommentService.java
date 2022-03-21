package com.example.sendowl.service;

import com.example.sendowl.dto.CommentRequest;
import com.example.sendowl.entity.Board;
import com.example.sendowl.entity.Comment;
import com.example.sendowl.entity.Member;
import com.example.sendowl.repository.BoardRepository;
import com.example.sendowl.repository.CommentRepository;
import com.example.sendowl.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

//    public List<Board> getBoardList() {
//        String active = "Y";
//
//        return boardRepository.findByActive(active);
//    }

    public void insertComment(CommentRequest vo) {
        // 보드 객체 찾기
        Board board = boardRepository.findById(vo.getBoardId()).get();
        // 멤버 객체 찾기
        Member member = memberRepository.findById(vo.getMemberId()).get();

        System.out.println(member.toString());
        System.out.println(board.toString());
        Comment comment = new Comment().builder().board(board)
                .member(member)
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

//    public Board getBoard(long id) {
//
//        return boardRepository.getById(id);
//    }
}
