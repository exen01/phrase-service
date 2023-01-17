package com.exen.example.dao.communication;

import com.exen.example.domain.api.communication.comment.CommentPhraseReq;

public interface ReactionDao {

    /**
     * Likes phrase
     *
     * @param userId   user id
     * @param phraseId phrase id
     */
    void likePhrase(long userId, long phraseId);

    /**
     * Remove like from phrase
     *
     * @param userId   user id
     * @param phraseId phrase id
     */
    void deleteLikePhrase(long userId, long phraseId);

    /**
     * Comments phrase
     *
     * @param userId user id
     * @param req    phrase id and comment text
     */
    void commentPhrase(long userId, CommentPhraseReq req);
}
