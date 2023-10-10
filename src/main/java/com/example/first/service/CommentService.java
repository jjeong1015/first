package com.example.first.service;

import com.example.first.dto.CommentDto;
import com.example.first.entity.Article;
import com.example.first.entity.Comment;
import com.example.first.repository.ArticleRepository;
import com.example.first.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository; // 댓글
    @Autowired
    private ArticleRepository articleRepository; // 게시글 -> 게시글이 있어야 댓글을 생성할 때 대상 게시글의 존재 여부 파악 가능

    public List<CommentDto> comments(Long articleId) { // 댓글 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId); // 댓글 조회
//
//        List<CommentDto> dtos = new ArrayList<CommentDto>(); // 엔티티 -> DTO 변환
//        for (int i=0; i<comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }

        return commentRepository.findByArticleId(articleId) // 댓글 엔티티 목록 조회
                .stream() // 댓글 엔티티 목록을 스트림으로 변환
                .map(comment -> CommentDto.createCommentDto(comment)) // 엔티티를 DTO로 매핑
                .collect(Collectors.toList()); // 스트림을 리스트로 변환
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        Article article = articleRepository.findById(articleId) // 부모 게시글을 article 변수에 받아옴
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! " + "대상 게시글이 없습니다.")); // 부모 게시글이 없다면 예외 발생 -> 종료
        Comment comment = Comment.createComment(dto, article); // 댓글 엔티티 생성
        Comment created = commentRepository.save(comment); // 댓글 엔티티를 DB에 저장
        return CommentDto.createCommentDto(created); // DTO로 변환해 반환
    }

    @Transactional // DB 내용을 변경하므로 필요
    public CommentDto update(Long id, CommentDto dto) {
        Comment target = commentRepository.findById(id) // 수정할 댓글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!" + "대상 댓글이 없습니다.")); // 없으면 에러 메시지 출력
        target.patch(dto); // 댓글 수정
        Comment updated = commentRepository.save(target); // DB로 갱신
        return CommentDto.createCommentDto(updated); // 댓글 엔티티를 DTO로 변환 및 반환
    }

    @Transactional
    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id) // 삭제할 댓글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패!" + "대상이 없습니다.")); // 없으면 에러 메시지 출력
        commentRepository.delete(target); // 댓글 삭제
        return CommentDto.createCommentDto(target); // 삭제 댓글을 DTO로 변환 및 반환
    }
}
