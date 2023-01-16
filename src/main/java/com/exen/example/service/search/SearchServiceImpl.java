package com.exen.example.service.search;

import com.exen.example.dao.common.CommonDao;
import com.exen.example.dao.search.SearchDao;
import com.exen.example.domain.api.search.searchPhrasesByPartWord.SearchPhrasesByPartWordReq;
import com.exen.example.domain.api.search.searchPhrasesByPartWord.SearchPhrasesByPartWordResp;
import com.exen.example.domain.api.search.searchPhrasesByTag.SearchPhrasesByTagReq;
import com.exen.example.domain.api.search.searchPhrasesByTag.SearchPhrasesByTagResp;
import com.exen.example.domain.api.search.searchTags.SearchTagsReq;
import com.exen.example.domain.api.search.searchTags.SearchTagsResp;
import com.exen.example.domain.api.common.TagResp;
import com.exen.example.domain.api.search.searchUsersByPartNickname.SearchUsersByPartNicknameReq;
import com.exen.example.domain.api.common.PhraseResp;
import com.exen.example.domain.response.Response;
import com.exen.example.domain.response.SuccessResponse;
import com.exen.example.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchDao searchDao;
    private final ValidationUtils validationUtils;
    private final CommonDao commonDao;

    /**
     * Searches tags by title
     *
     * @param req         part or full title of tag
     * @param accessToken user access token
     * @return list of tags
     */
    @Override
    public ResponseEntity<Response> searchTags(SearchTagsReq req, String accessToken) {
        validationUtils.validationRequest(req);
        commonDao.getUserIdByAccessToken(accessToken);

        List<TagResp> tagRespList = searchDao.searchTags(req.getPartTag());
        return new ResponseEntity<>(SuccessResponse.builder().data(SearchTagsResp.builder().tags(tagRespList).build()).build(), HttpStatus.OK);
    }

    /**
     * Searches phrases by tag id
     *
     * @param req         tag id
     * @param accessToken user access token
     * @return list of phrases
     */
    @Override
    public ResponseEntity<Response> searchPhrasesByTag(SearchPhrasesByTagReq req, String accessToken) {
        validationUtils.validationRequest(req);
        commonDao.getUserIdByAccessToken(accessToken);

        List<PhraseResp> phrases = searchDao.searchPhrasesByTag(req);
        for (PhraseResp phraseResp : phrases) {
            List<TagResp> tags = commonDao.getTagsByPhraseId(phraseResp.getPhraseId());
            phraseResp.setTags(tags);
        }

        return new ResponseEntity<>(SuccessResponse.builder().data(SearchPhrasesByTagResp.builder().phrases(phrases).build()).build(), HttpStatus.OK);
    }

    /**
     * Searches phrases by part or full word
     *
     * @param req         part or full word, sort method
     * @param accessToken user access token
     * @return list of phrases
     */
    @Override
    public ResponseEntity<Response> searchPhrasesByPartWord(SearchPhrasesByPartWordReq req, String accessToken) {
        validationUtils.validationRequest(req);
        commonDao.getUserIdByAccessToken(accessToken);

        List<PhraseResp> phraseRespList = searchDao.searchPhrasesByPartWord(req);
        for (PhraseResp phraseResp : phraseRespList) {
            List<TagResp> tags = commonDao.getTagsByPhraseId(phraseResp.getPhraseId());
            phraseResp.setTags(tags);
        }

        return new ResponseEntity<>(SuccessResponse.builder().data(SearchPhrasesByPartWordResp.builder().phrases(phraseRespList).build()).build(), HttpStatus.OK);
    }

    /**
     * Searches users by part or full nickname
     *
     * @param req         part or full nickname
     * @param accessToken user access token
     * @return list of users
     */
    @Override
    public ResponseEntity<Response> searchUsersByPartNickname(SearchUsersByPartNicknameReq req, String accessToken) {
        validationUtils.validationRequest(req);
        commonDao.getUserIdByAccessToken(accessToken);

        return new ResponseEntity<>(SuccessResponse.builder().data(searchDao.searchUsersByPartNickname(req.getPartNickname())).build(), HttpStatus.OK);
    }
}
