package com.exen.example.service.impl;

import com.exen.example.dao.Dao;
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

    @Override
    public ResponseEntity<Response> test() {
        throw CommonException.builder().code(Code.TEST).message("test").httpStatus(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Response> registration(RegistrationReq req) {
        validationUtils.validationRequest(req);

        if (dao.isExistsNickname(req.getNickname())) {
            throw CommonException.builder().code(Code.NICKNAME_TAKEN).message("Этот ник уже занят, придумайте другой.").httpStatus(HttpStatus.BAD_REQUEST).build();
        }

        String accessToken = UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
        String encryptPassword = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());
        dao.insertNewUser(User.builder().nickname(req.getNickname()).encryptPassword(encryptPassword).accessToken(accessToken).build());

        return new ResponseEntity<Response>(SuccessResponse.builder().data(RegistrationResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);
    }
}
