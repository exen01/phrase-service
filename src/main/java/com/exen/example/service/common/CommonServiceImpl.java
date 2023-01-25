package com.exen.example.service.common;

import com.exen.example.dao.common.CommonDao;
import com.exen.example.domain.api.common.PhraseResp;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.response.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    /**
     * Checks user blocking by id
     *
     * @param userId           subscriber user id
     * @param checkBlockUserId publisher user id
     */
    @Override
    public void checkBlockByUserId(long userId, long checkBlockUserId) {
        checkBlock(userId, checkBlockUserId);
    }

    /**
     * Checks user blocking by phrase id
     *
     * @param userId   current user id
     * @param phraseId phrase id
     */
    @Override
    public void checkBlockByPhraseId(long userId, long phraseId) {
        long checkBlockUserId = commonDao.getUserIdByPhraseId(phraseId);
        checkBlock(userId, checkBlockUserId);
    }

    private void checkBlock(long userId, long checkBlockUserId) {
        if (commonDao.isBlocked(userId, checkBlockUserId)) {
            throw CommonException.builder()
                    .code(Code.BLOCKED)
                    .userMessage("Вы заблокированы этим пользователем.")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
