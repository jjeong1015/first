package com.example.first.controller;

import com.example.first.dto.ArticleForm;
import com.example.first.dto.CommentDto;
import com.example.first.entity.Article;
import com.example.first.repository.ArticleRepository;
import com.example.first.service.CommentService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new"; // 입력 페이지
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) { // 폼에서 전송한 데이터를 DTO로 받기
        // System.out.println(form.toString()); // DTO에 폼 데이터가 잘 담겼는지 확인
        log.info(form.toString());
        Article article = form.toEntity();
        // System.out.println(article.toString());
        log.info(article.toString());
        Article saved = articleRepository.save(article);
        // System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId(); // 상세 페이지
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        Article articleEntity = articleRepository.findById(id).orElse(null); // id 값으로 데이터를 찾을 때 해당 id 값이 없으면 null 반환, findById()는 Optional 타입으로 반환
        List<CommentDto> commentsDtos = commentService.comments(id); // 조회한 댓글 목록을 저장
        model.addAttribute("article", articleEntity); // 모델에 데이터 등록
        model.addAttribute("commentDtos", commentsDtos); // 댓글 목록 모델에 등록
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        ArrayList<Article> articleEntityList = articleRepository.findAll(); // 모든 데이터 가져오기
        model.addAttribute("articleList", articleEntityList); // 모델에 데이터 등록
        return "articles/index"; // 목록 페이지
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null); // DB에서 수정할 데이터 가져오기
        model.addAttribute("article", articleEntity); // 모델에 데이터 등록
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) { // 매개변수로 DTO 받아오기 -> 수정 폼에서 전송한 데이터는 DTO로 받음
        log.info(form.toString());
        Article articleEntity = form.toEntity(); // DTO(form)를 엔티티(articleEntity)로 변환 -> toEntity() : DTO를 엔티티로 변환
        log.info(articleEntity.toString());
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null); // DB에서 기존 데이터 가져오기
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티를 DB에 저장(갱신)
        }
        return "redirect:/articles/" + articleEntity.getId(); // 수정 결과 페이지로 리다이렉트
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        if (target != null) { // 삭제할 대상이 있는지 확인
            articleRepository.delete(target); // delete() 메서드로 대상 삭제
            rttr.addFlashAttribute("msg", "삭제됐습니다!"); // (넘겨주려는 키 문자열, 넘겨주려는 값 객체)
        }
        return "redirect:/articles";
    }

}
