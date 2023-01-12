package com.exen.example.dao;

import com.exen.example.domain.api.common.TagResp;

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
}
