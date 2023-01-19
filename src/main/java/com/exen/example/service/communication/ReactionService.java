package com.exen.example.service.communication;

import com.exen.example.domain.api.communication.comment.CommentPhraseReq;
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

    /**
     * Comments phrase
     *
     * @param accessToken user access token
     * @param req         phrase id and comment text
     * @return response
     */
    ResponseEntity<Response> commentPhrase(String accessToken, CommentPhraseReq req);

    /**
     * Delete user comment or comment under user phrase
     *
     * @param accessToken user access token
     * @param commentId   comment id
     * @return response
     */
    ResponseEntity<Response> deleteCommentPhrase(String accessToken, long commentId);
}
