package com.exen.example.controller;

import com.exen.example.domain.api.LoginReq;
import com.exen.example.domain.api.PublishPhraseReq;
import com.exen.example.domain.api.RegistrationReq;
import com.exen.example.domain.response.Response;
import com.exen.example.service.PhraseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("phrase-service-public")
public class Controller {
    private final PhraseService phraseService;

    @GetMapping("/hello")
    public String hello() {
        String hello = "Hello, phrase-service! Version: 0.0.1";
        log.info(hello);
        return hello;
    }

    @PostMapping("/test")
    public ResponseEntity<Response> test() {
        log.info("Start endpoint test");
        ResponseEntity<Response> response = phraseService.test();
        log.info("End endpoint test, response: {}", response);
        return response;
    }

    @PostMapping("/registration")
    public ResponseEntity<Response> registration(@RequestBody final RegistrationReq req) {
        log.info("Start endpoint registration, request: {}", req);
        ResponseEntity<Response> response = phraseService.registration(req);
        log.info("End endpoint registration, response: {}", response);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody final LoginReq req) {
        log.info("Start endpoint login, request: {}", req);
        ResponseEntity<Response> response = phraseService.login(req);
        log.info("End endpoint login, response: {}", response);
        return response;
    }

    @PostMapping("/publish-phrase")
    public ResponseEntity<Response> publishPhrase(@RequestHeader final String accessToken, @RequestBody final PublishPhraseReq req) {
        log.info("Start endpoint publishPhrase, accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = phraseService.publishPhrase(req, accessToken);
        log.info("End endpoint publishPhrase, response: {}", response);
        return response;
    }

    @GetMapping("/my-phrases")
    public ResponseEntity<Response> getMyPhrases(@RequestHeader final String accessToken) {
        log.info("Start endpoint getMyPhrases, accessToken: {}", accessToken);
        ResponseEntity<Response> response = phraseService.getMyPhrases(accessToken);
        log.info("End endpoint getMyPhrases, response: {}", response);
        return response;
    }
}
