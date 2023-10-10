package com.example.first.service;

import com.example.first.dto.ArticleForm;
import com.example.first.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;
    @Test
    void index() {
        Article a = new Article(1L, "aaaa", "1111"); // 예상 데이터
        Article b = new Article(2L, "bbbb", "2222");
        Article c = new Article(3L, "cccc", "3333");

        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c)); // a, b, c 합치기

        List<Article> articles = articleService.index();

        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        Long id = 1L;
        Article expected = new Article(id, "aaaa", "1111"); // 예상 데이터 저장
        Article article = articleService.show(id); // 실제 데이터 저장
        assertEquals(expected.toString(), article.toString()); // 비교
    }

    @Test
    void show_실패_존재하지_않는_id_입력() {
        Long id = -1L;
        Article expected = null; // 예상 데이터 저장
        Article article = articleService.show(id); // 실제 데이터 저장
        assertEquals(expected, article); // 비교
    }

    @Transactional
    @Test
    void create_성공_title과_content만_있는_dto_입력() {
        String title = "dddd"; // 값 임의배정
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content); // dto 생성
        Article excepted = new Article(4L, title, content); // 예상 데이터 저장
        Article article = articleService.create(dto); // 실제 데이터 저장
        assertEquals(excepted.toString(), article.toString()); // 비교
    }

    @Transactional
    @Test
    void create_실패_id가_포함된_dto_입력() {
        Long id = 4L; // 값 임의배정
        String title = "dddd";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content); // dto 생성
        Article excepted = null;
        Article article = articleService.create(dto); // 실제 데이터 저장
        assertEquals(excepted, article); // 비교
    }
}