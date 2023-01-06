package com.exen.example.service;

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
}
