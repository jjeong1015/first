package com.example.first.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST API 구현 시 사용
public class FirstApiController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello world!";
    }
}
