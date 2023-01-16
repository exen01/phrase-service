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
}
