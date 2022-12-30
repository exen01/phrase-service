package com.exen.example.service.impl;

import com.exen.example.dao.Dao;
import com.exen.example.domen.api.LoginReq;
import com.exen.example.domen.api.LoginResp;
import com.exen.example.domen.api.RegistrationReq;
import com.exen.example.domen.api.RegistrationResp;
import com.exen.example.domen.constant.Code;
import com.exen.example.domen.dto.User;
import com.exen.example.domen.response.Response;
import com.exen.example.domen.response.SuccessResponse;
import com.exen.example.domen.response.exception.CommonException;
import com.exen.example.service.PhraseService;
import com.exen.example.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhraseServiceImpl implements PhraseService {
    private final ValidationUtils validationUtils;
    private final Dao dao;

    /**
     * Method for testing response
     *
     * @return response
     */
    @Override
    public ResponseEntity<Response> test() {
        throw CommonException.builder().code(Code.TEST).message("test").httpStatus(HttpStatus.BAD_REQUEST).build();
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

        if (dao.isExistsNickname(req.getNickname())) {
            throw CommonException.builder().code(Code.NICKNAME_TAKEN).message("Этот ник уже занят, придумайте другой.").httpStatus(HttpStatus.BAD_REQUEST).build();
        }

        String accessToken = UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
        String encryptPassword = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());
        dao.insertNewUser(User.builder().nickname(req.getNickname()).encryptPassword(encryptPassword).accessToken(accessToken).build());

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

        String encryptPassword = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());
        String accessToken = dao.getAccessToken(User.builder().nickname(req.getNickname()).encryptPassword(encryptPassword).build());
        return new ResponseEntity<>(SuccessResponse.builder().data(LoginResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);
    }


}
