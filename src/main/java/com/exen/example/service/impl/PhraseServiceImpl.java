package com.exen.example.service.impl;

import com.exen.example.dao.Dao;
import com.exen.example.domain.api.*;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.dto.User;
import com.exen.example.domain.entity.Phrase;
import com.exen.example.domain.response.Response;
import com.exen.example.domain.response.SuccessResponse;
import com.exen.example.domain.response.exception.CommonException;
import com.exen.example.service.PhraseService;
import com.exen.example.util.EncryptUtils;
import com.exen.example.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhraseServiceImpl implements PhraseService {
    private final ValidationUtils validationUtils;
    private final Dao dao;
    private final EncryptUtils encryptUtils;

    /**
     * Method for testing response
     *
     * @return response
     */
    @Override
    public ResponseEntity<Response> test() {
        throw CommonException.builder().code(Code.TEST).userMessage("test").httpStatus(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Registration new user
     *
     * @param req request
     * @return response
     */
    @Override
    public ResponseEntity<Response> registration(RegistrationReq req) {
        validationUtils.validationRequest(req);

        if (dao.isExistsNickname(req.getAuthorizationReq().getNickname())) {
            throw CommonException.builder().code(Code.NICKNAME_TAKEN).userMessage("Этот ник уже занят, придумайте другой.").httpStatus(HttpStatus.BAD_REQUEST).build();
        }

        String accessToken = UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
        String encryptPassword = encryptUtils.encryptPassword(req.getAuthorizationReq().getPassword());
        dao.insertNewUser(User.builder().nickname(req.getAuthorizationReq().getNickname()).encryptPassword(encryptPassword).accessToken(accessToken).build());

        return new ResponseEntity<>(SuccessResponse.builder().data(RegistrationResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);
    }

    /**
     * Login user
     *
     * @param req request
     * @return response
     */
    @Override
    public ResponseEntity<Response> login(LoginReq req) {
        validationUtils.validationRequest(req);

        String encryptPassword = encryptUtils.encryptPassword(req.getAuthorizationReq().getPassword());
        String accessToken = dao.getAccessToken(User.builder().nickname(req.getAuthorizationReq().getNickname()).encryptPassword(encryptPassword).build());
        return new ResponseEntity<>(SuccessResponse.builder().data(LoginResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);
    }

    /**
     * Publish phrase
     *
     * @param req         request
     * @param accessToken access token
     * @return response
     */
    @Override
    public ResponseEntity<Response> publishPhrase(PublishPhraseReq req, String accessToken) {
        validationUtils.validationRequest(req);

        long userId = dao.getUserIdByAccessToken(accessToken);
        long phraseId = dao.addPhrase(userId, req.getText());
        log.info("userId: {}, phraseId: {}", userId, phraseId);

        for (String tag : req.getTags()) {
            dao.addTag(tag);
            dao.addPhraseTag(phraseId, tag);
        }

        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Gets user phrases
     *
     * @param accessToken user access token
     * @return response
     */
    @Override
    public ResponseEntity<Response> getMyPhrases(String accessToken) {
        long userId = dao.getUserIdByAccessToken(accessToken);
        List<Phrase> phraseList = dao.getPhrasesByUserId(userId);

        List<PhraseResp> phraseRespList = new ArrayList<>();
        for (Phrase phrase : phraseList) {
            List<String> tags = dao.getTagsByPhraseId(phrase.getId());
            phraseRespList.add(PhraseResp.builder()
                    .id(phrase.getId())
                    .text(phrase.getText())
                    .timeInsert(phrase.getTimeInsert())
                    .tags(tags).build());
        }

        return new ResponseEntity<>(SuccessResponse.builder().data(MyPhrasesResp.builder().phrases(phraseRespList).build()).build(), HttpStatus.OK);
    }
}
