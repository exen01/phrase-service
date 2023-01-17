package com.exen.example.service.communication;

import com.exen.example.dao.common.CommonDao;
import com.exen.example.dao.communication.ReactionDao;
import com.exen.example.domain.api.communication.comment.CommentPhraseReq;
import com.exen.example.domain.response.Response;
import com.exen.example.domain.response.SuccessResponse;
import com.exen.example.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ValidationUtils validationUtils;
    private final CommonDao commonDao;
    private final ReactionDao reactionDao;

    /**
     * Likes phrase
     *
     * @param accessToken user access token
     * @param phraseId    phrase id
     * @return response
     */
    @Override
    public ResponseEntity<Response> likePhrase(String accessToken, long phraseId) {
        validationUtils.validationDecimalMin("phraseId", phraseId, 1);
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        reactionDao.likePhrase(userId, phraseId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Remove like from phrase
     *
     * @param accessToken user access token
     * @param phraseId    phrase id
     * @return response
     */
    @Override
    public ResponseEntity<Response> deleteLikePhrase(String accessToken, long phraseId) {
        validationUtils.validationDecimalMin("phraseId", phraseId, 1);
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        reactionDao.deleteLikePhrase(userId, phraseId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Comments phrase
     *
     * @param accessToken user access token
     * @param req         phrase id and comment text
     * @return response
     */
    @Override
    public ResponseEntity<Response> commentPhrase(String accessToken, CommentPhraseReq req) {
        validationUtils.validationRequest(req);
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        reactionDao.commentPhrase(userId, req);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }
}
