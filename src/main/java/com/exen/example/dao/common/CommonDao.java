package com.exen.example.dao.common;

import com.exen.example.domain.api.common.TagResp;
import com.exen.example.domain.api.common.CommentResp;

import java.util.List;

public interface CommonDao {
    /**
     * Gets user id by access token
     *
     * @param accessToken access token
     * @return object id
     */
    long getUserIdByAccessToken(String accessToken);

    /**
     * Gets tags by phrase id
     *
     * @param phraseId phrase id
     * @return list of tags
     */
    List<TagResp> getTagsByPhraseId(long phraseId);

    /**
     * Test
     *
     * @param instanceName test value
     */
    void testSchedulerLock(String instanceName);

    /**
     * Gets count phrase likes
     *
     * @param phraseId phrase id
     * @return count of likes
     */
    long getCountLikes(long phraseId);

    /**
     * Gets phrase comments
     *
     * @param phraseId phrase id
     * @return list of comments
     */
    List<CommentResp> getCommentsByPhraseId(long phraseId);

    /**
     * Check user blocking
     *
     * @param userId           current user id
     * @param checkBlockUserId blocked user id
     * @return true if user with userId blocked user with checkBlockUserId,
     */
    boolean isBlocked(long userId, long checkBlockUserId);

    /**
     * Gets user id by phrase id
     *
     * @param phraseId phrase id
     * @return author user id
     */
    long getUserIdByPhraseId(long phraseId);
}
