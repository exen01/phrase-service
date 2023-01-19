package com.exen.example.dao.communication;

import com.exen.example.domain.api.communication.comment.CommentPhraseReq;
import com.exen.example.domain.dto.WhoseComment;

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

    /**
     * Gets comment author id and phrase author id
     *
     * @param commentId comment id
     * @return dto with ids
     */
    WhoseComment whoseComment(long commentId);

    /**
     * Remove comment
     *
     * @param commentId comment id
     */
    void deleteCommentPhrase(long commentId);
}
