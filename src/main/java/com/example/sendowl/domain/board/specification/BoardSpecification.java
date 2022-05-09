package com.example.sendowl.domain.board.specification;

import com.example.sendowl.domain.board.entity.Board;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BoardSpecification {
    public static Specification<Board> likeTitle(String title){
        return new Specification<Board>() {
            @Override
            public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 1) like
                return criteriaBuilder.like(root.get("title"), "%"+title+"%");
            }
        };
    }
    public static Specification<Board> likeContent(String content){
        return new Specification<Board>() {
            @Override //
            public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 1) like
                return criteriaBuilder.like(root.get("content"), "%"+content+"%");
            }
        };
    }
    public static Specification<Board> likeUserNickName(String nickName){
        return new Specification<Board>() {
            @Override //
            public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 1) like
                return criteriaBuilder.like(root.get("user").get("nickName"), "%"+nickName+"%");
            }
        };
    }
    public static Specification<Board> equalActive(Boolean isDelete){
        return new Specification<Board>() {
            @Override
            public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 1) like
                return criteriaBuilder.equal(root.get("isDelete"), isDelete);
            }
        };
    }
}
