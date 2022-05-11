package com.example.sendowl.repository;

import com.example.sendowl.api.service.BoardService;
import com.example.sendowl.api.service.CommentService;
import com.example.sendowl.domain.board.dto.BoardDto;
import com.example.sendowl.domain.comment.dto.CommentDto;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.comment.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MockMvc mockMvc;

    // 로그 관련
    Logger log = (Logger) LoggerFactory.getLogger(CommentRepositoryTest.class);


    @Test
    public void 코멘트인서트() {
        log.info("코멘트인서트");
        CommentDto.CommentReq crq = CommentDto.CommentReq.builder()
                .boardId(1L)
                .email("a1@naver.com")
                .content("NEST comment")
                .build();
        log.error(commentService.insertComment(crq).toString());

    }

    @Test
    public void 코멘트리스트() {
        Long bid = 1l;
        List<CommentDto.CommentRes> Clist = commentService.selectCommentList(bid);

        log.error(String.valueOf(Clist.size()));
    }


    @Test
    public void 코멘트소프트딜리트() {
        Long cid = 1l;
        commentService.deleteComment(cid);
    }

    @Test
    public void 코멘트내용수정() {
        Long cid = 1l;
        CommentDto.UpdateReq req = CommentDto.UpdateReq.builder()
                .commentId(cid)
                .content("test suc!!!!")
                .build();
        commentService.updateComment(req);
    }
}
