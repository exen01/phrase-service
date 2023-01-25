package com.exen.example.service.common;

import com.exen.example.domain.api.common.PhraseResp;

import java.util.List;

public interface CommonService {

    /**
     * Sets tags and likes count into phrases
     *
     * @param phrases list of phrases
     */
    void phraseEnrichment(List<PhraseResp> phrases);

    /**
     * Checks user blocking by id
     *
     * @param userId           subscriber user id
     * @param checkBlockUserId publisher user id
     */
    void checkBlockByUserId(long userId, long checkBlockUserId);

    /**
     * Checks user blocking by phrase id
     * @param userId current user id
     * @param phraseId phrase id
     */
    void checkBlockByPhraseId(long userId, long phraseId);
}
