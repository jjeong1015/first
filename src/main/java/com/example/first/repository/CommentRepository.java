package com.example.first.repository;

import com.example.first.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> { // <대상 엔티티, 대표키 값의 타입>
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true) // value 속성에 실행하려는 쿼리 작성
    List<Comment> findByArticleId(Long articleId); // 특정 게시글의 모든 댓글을 조회

    List<Comment> findByNickname(String nickname); // 특정 닉네임의 모든 댓글 조회


}
