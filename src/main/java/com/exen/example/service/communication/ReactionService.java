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

    /**
     * Blocked user with blockUserId
     *
     * @param accessToken user access token
     * @param blockUserId blocked user ID
     * @return response
     */
    ResponseEntity<Response> blockUser(String accessToken, long blockUserId);

    /**
     * Gets blocked users
     *
     * @param accessToken user access token
     * @return list of blocked users
     */
    ResponseEntity<Response> getBlocUsers(String accessToken);

    /**
     * Unblocked user with blockUserId
     *
     * @param accessToken user access token
     * @param blockUserId unblocked user id
     * @return response
     */
    ResponseEntity<Response> unblockUser(String accessToken, long blockUserId);
}
