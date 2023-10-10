package com.example.first.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hi") // localhost:8080/hi로 접속하면 greetings.mustache 파일을 찾아 반환
    public String niceToMeetYou(Model model) { // model 객체 가져오기
        model.addAttribute("username", "jjeong"); // model 변수 등록 -> model 객체가 "jjeong(변숫값)" 값을 "username(변수명)"에 연결해 웹 브라우저로 보냄
        return "greetings"; // greetings.mustache을 찾아 웹 브라우저로 전송 -> 뷰 템플릿 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "쩡");
        return "goodbye";
    }
}
