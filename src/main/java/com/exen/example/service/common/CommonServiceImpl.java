package com.exen.example.service.common;

import com.exen.example.dao.common.CommonDao;
import com.exen.example.domain.api.common.PhraseResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonDao commonDao;

    /**
     * Sets tags, likes count and comments into phrases
     *
     * @param phrases list of phrases
     */
    @Override
    public void phraseEnrichment(List<PhraseResp> phrases) {
        for (PhraseResp phraseResp : phrases) {
            phraseResp.setTags(commonDao.getTagsByPhraseId(phraseResp.getPhraseId()));
            phraseResp.setCountLikes(commonDao.getCountLikes(phraseResp.getPhraseId()));
            phraseResp.setComments(commonDao.getCommentsByPhraseId(phraseResp.getPhraseId()));
        }
    }
}
