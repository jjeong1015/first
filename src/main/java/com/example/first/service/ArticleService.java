package com.example.first.service;

import com.example.first.dto.ArticleForm;
import com.example.first.entity.Article;
import com.example.first.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) { // article 객체에 id가 존재한다면 null 반환 -> 존재하는 데이터가 있을 경우 POST 요청 시 데이터 변경 X
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity(); // DTO -> Entity 변환
        log.info("id: {}, article: {}", id, article.toString());

        Article target = articleRepository.findById(id).orElse(null); // 타깃 조회

        if (target == null || id != article.getId()) { // 잘못된 요청인지 판별
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null; // 응답은 컨트롤러가 하므로 여기서는 null 반환
        }

        target.patch(article); // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // article 엔티티 DB에 저장
        return updated; // 응답은 컨트롤러가 하므로 여기서는 수정 데이터만 반환
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null); // 대상 찾기

        if (target == null) { // 잘못된 요청 처리하기
            return null; // 응답은 컨트롤러가 하므로 여기서는 null 반환
        }

        articleRepository.delete(target); // 대상 삭제하기
        return target; // DB에서 삭제한 대상을 컨트롤러에 반환
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        List<Article> articleList = dtos.stream() // dto 묶음을 엔티티 묶음으로 변환
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        articleList.stream() // 엔티티 묶음을 DB에 저장
                .forEach(article -> articleRepository.save(article));

        articleRepository.findById(-1L) // id가 -1인 데이터 찾기
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!")); // 찾는 데이터가 없으면 예외 발생

        return articleList; // 결과 값 반환
    }
}
