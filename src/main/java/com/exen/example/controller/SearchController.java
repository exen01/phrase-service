package com.exen.example.controller;

import com.exen.example.domain.api.search.searchTags.SearchTagsReq;
import com.exen.example.domain.response.Response;
import com.exen.example.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("phrase-service-public/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/search-tags")
    public ResponseEntity<Response> searchTags(@RequestHeader String accessToken, @RequestBody final SearchTagsReq req) {
        log.info("Start endpoint searchTags, accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = searchService.searchTags(req, accessToken);
        log.info("End endpoint searchTags, response: {}", response);
        return response;
    }

}
