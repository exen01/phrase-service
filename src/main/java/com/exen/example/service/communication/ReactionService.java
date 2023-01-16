package com.exen.example.service.communication;

import com.exen.example.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface ReactionService {

    /**
     * Likes phrase
     *
     * @param accessToken user access token
     * @param phraseId    phrase id
     * @return response
     */
    ResponseEntity<Response> likePhrase(String accessToken, long phraseId);

    /**
     * Remove like from phrase
     *
     * @param accessToken user access token
     * @param phraseId    phrase id
     * @return response
     */
    ResponseEntity<Response> deleteLikePhrase(String accessToken, long phraseId);
}
