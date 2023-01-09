package com.exen.example.service;

import com.exen.example.domain.api.search.searchPhrasesByPartWord.SearchPhrasesByPartWordReq;
import com.exen.example.domain.api.search.searchPhrasesByTag.SearchPhrasesByTagReq;
import com.exen.example.domain.api.search.searchTags.SearchTagsReq;
import com.exen.example.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface SearchService {
    /**
     * Searches tags by title
     *
     * @param req         part or full title of tag
     * @param accessToken user access token
     * @return list of tags
     */
    ResponseEntity<Response> searchTags(SearchTagsReq req, String accessToken);

    /**
     * Searches phrases by tag id
     *
     * @param req         tag id, sort method
     * @param accessToken user access token
     * @return list of phrases
     */
    ResponseEntity<Response> searchPhrasesByTag(SearchPhrasesByTagReq req, String accessToken);

    /**
     * Searches phrases by part or full word
     *
     * @param req         part or full word, sort method
     * @param accessToken user access token
     * @return list of phrases
     */
    ResponseEntity<Response> searchPhrasesByPartWord(SearchPhrasesByPartWordReq req, String accessToken);
}
