package com.exen.example.service.impl;

import com.exen.example.dao.CommonDao;
import com.exen.example.dao.UserDao;
import com.exen.example.domain.api.common.TagResp;
import com.exen.example.domain.api.user.login.LoginReq;
import com.exen.example.domain.api.user.login.LoginResp;
import com.exen.example.domain.api.user.myPhrases.MyPhrasesResp;
import com.exen.example.domain.api.user.myPhrases.PhraseResp;
import com.exen.example.domain.api.user.publishPhrase.PublishPhraseReq;
import com.exen.example.domain.api.user.registration.RegistrationReq;
import com.exen.example.domain.api.user.registration.RegistrationResp;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.dto.User;
import com.exen.example.domain.entity.Phrase;
import com.exen.example.domain.response.Response;
import com.exen.example.domain.response.SuccessResponse;
import com.exen.example.domain.response.exception.CommonException;
import com.exen.example.service.UserService;
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
public class UserServiceImpl implements UserService {
    private final ValidationUtils validationUtils;
    private final CommonDao commonDao;
    private final UserDao userDao;
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

        if (userDao.isExistsNickname(req.getAuthorization().getNickname())) {
            throw CommonException.builder().code(Code.NICKNAME_TAKEN).userMessage("Этот ник уже занят, придумайте другой.").httpStatus(HttpStatus.BAD_REQUEST).build();
        }

        String accessToken = UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
        String encryptPassword = encryptUtils.encryptPassword(req.getAuthorization().getPassword());
        userDao.insertNewUser(User.builder().nickname(req.getAuthorization().getNickname()).encryptPassword(encryptPassword).accessToken(accessToken).build());

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

        String encryptPassword = encryptUtils.encryptPassword(req.getAuthorization().getPassword());
        String accessToken = userDao.getAccessToken(User.builder().nickname(req.getAuthorization().getNickname()).encryptPassword(encryptPassword).build());
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

        long userId = commonDao.getUserIdByAccessToken(accessToken);
        long phraseId = userDao.addPhrase(userId, req.getText());
        log.info("userId: {}, phraseId: {}", userId, phraseId);

        for (String tag : req.getTags()) {
            userDao.addTag(tag);
            userDao.addPhraseTag(phraseId, tag);
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
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        List<Phrase> phraseList = userDao.getPhrasesByUserId(userId);

        List<PhraseResp> phraseRespList = new ArrayList<>();
        for (Phrase phrase : phraseList) {
            List<TagResp> tags = commonDao.getTagsByPhraseId(phrase.getId());
            phraseRespList.add(PhraseResp.builder()
                    .phraseId(phrase.getId())
                    .text(phrase.getText())
                    .timeInsert(phrase.getTimeInsert())
                    .tags(tags).build());
        }

        return new ResponseEntity<>(SuccessResponse.builder().data(MyPhrasesResp.builder().phrases(phraseRespList).build()).build(), HttpStatus.OK);
    }
}
