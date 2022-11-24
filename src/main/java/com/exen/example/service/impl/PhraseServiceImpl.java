package com.exen.example.service.impl;

import com.exen.example.domen.api.RegistrationReq;
import com.exen.example.domen.constant.Code;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class PhraseServiceImpl implements PhraseService {
    private final ValidationUtils validationUtils;

    @Override
    public ResponseEntity<Response> test() {
        throw CommonException.builder().code(Code.TEST).message("test").httpStatus(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Response> registration(RegistrationReq req) {
        validationUtils.validationRequest(req);
        return new ResponseEntity<Response>(SuccessResponse.builder().data("everything is fine").build(), HttpStatus.OK);
    }
}
