package com.example.first.controller;

import com.example.first.dto.CommentDto;
import com.example.first.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/api/articles/{articleId}/comments") // 댓글 조회 요청 접수
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        List<CommentDto> dtos = commentService.comments(articleId); // 서비스에 댓글 조회 위임
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        CommentDto createdDto = commentService.create(articleId, dto); // 서비스에 댓글 생성 위임
        return ResponseEntity.status(HttpStatus.OK).body(createdDto); // 결과 응답
    }

    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        CommentDto updatedDto = commentService.update(id, dto); // 서비스에 댓글 수정 위임
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        CommentDto deletedDto = commentService.delete(id); // 서비스에 댓글 삭제 위임
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto); // 댓글을 성공적으로 삭제할 경우 응답
    }
}
