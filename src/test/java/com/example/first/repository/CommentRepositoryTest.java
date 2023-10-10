package com.example.first.repository;

import com.example.first.entity.Article;
import com.example.first.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // 해당 클래스를 JPA와 연동해 테스팅
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        { // 4번 게시글의 모든 댓글 조회
            Long articleId = 4L; // 입력 데이터

            List<Comment> comments = commentRepository.findByArticleId(articleId); // 실제 데이터

            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고"); // 부모 게시글 객체 생성
            Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅"); // 댓글 객체 생성
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");
            List<Comment> expected = Arrays.asList(a, b, c); // 댓글 객체 합치기

            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!"); // 비교 및 검증
        }
        { // 1번 게시글의 모든 댓글 조회
            Long articleId = 1L; // 입력 데이터

            List<Comment> comments = commentRepository.findByArticleId(articleId); // 실제 데이터

            Article article = new Article(1L, "가가가가", "1111"); // 부모 게시글 객체 생성
            List<Comment> expected = Arrays.asList();

            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음"); // 비교 및 검증
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        { // "Park"의 모든 댓글 조회
            String nickname = "Park";

            List<Comment> comments = commentRepository.findByNickname(nickname);

            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 고"), nickname, "굿 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"), nickname, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickname, "조깅");

            List<Comment> expected = Arrays.asList(a, b, c);

            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }

    }
}