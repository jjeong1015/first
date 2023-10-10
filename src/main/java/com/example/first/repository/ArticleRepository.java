package com.example.first.repository;

import com.example.first.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> { // <클래스 타입, 대표값 타입>
    @Override
    ArrayList<Article> findAll(); // Iterable -> ArrayList 수정
}
