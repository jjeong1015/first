package com.example.first.entity;

import com.example.first.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 대표키
    @ManyToOne // 댓글과 게시글의 관계 N:1
    @JoinColumn(name="article_id") // 외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article; // 해당 댓글의 부모 게시글
    @Column
    private String nickname; // 댓글 작성자
    @Column
    private String body; // 댓글 본문

    public static Comment createComment(CommentDto dto, Article article) { // 엔티티 생성 메서드
        if (dto.getId() != null) // 댓글을 생성하기 전부터 id값이 있을 수 없음
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId()) // dto에서 가져온 부모 게시글과 엔티티에서 가져온 부모 게시글이 다를 경우 JSON 데이터와 URL 요청 정보가 다름
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");

        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody()); // 댓글 본문
    }

    public void patch(CommentDto dto) {
        if (this.id != dto.getId()) // 댓글 수정 요청 시 URL에 있는 id와 JSON 데이터의 id가 다른 경우
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");

        if (dto.getNickname() != null) // 수정할 닉네임 데이터가 있을 경우
            this.nickname = dto.getNickname(); // 내용 반영
        if (dto.getBody() != null) // 수정할 본문 데이터가 있을 경우
            this.body = dto.getBody();
    }
}
