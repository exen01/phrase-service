package com.exen.example.controller;

import com.exen.example.domen.response.Response;
import com.exen.example.service.PhraseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("phrase-service-public")
public class Controller {
    private final PhraseService phraseService;

    @GetMapping("/hello")
    public String hello(){
        String hello = "Hello, phrase-service! Version: 0.0.1";
        log.info(hello);
        return hello;
    }

    @PostMapping("/test")
    public ResponseEntity<Response> test(){
        log.info("Start endpoint test");
        ResponseEntity<Response> response = phraseService.test();
        log.info("End endpoint test, response: {}", response);
        return response;
    }
}
