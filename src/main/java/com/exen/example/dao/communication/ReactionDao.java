package com.exen.example.dao.communication;

import com.exen.example.domain.api.common.UserResp;
import com.exen.example.domain.api.communication.reaction.comment.CommentPhraseReq;
import com.exen.example.domain.dto.WhoseComment;

import java.util.List;

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

    /**
     * Blocked user with blockUserId
     *
     * @param userId      current user id
     * @param blockUserId blocked user id
     */
    void blockUser(long userId, long blockUserId);

    /**
     * Gets blocked users
     *
     * @param userId user id
     * @return list of blocked users
     */
    List<UserResp> getBlockUsers(long userId);

    /**
     * Unblocked user with blockUserId
     *
     * @param userId      current user id
     * @param blockUserId unblocked user id
     */
    void unblockUser(long userId, long blockUserId);
}
