package com.exen.example.service.impl;

import com.exen.example.domen.constant.Code;
import com.exen.example.domen.response.Response;
import com.exen.example.domen.response.SuccessResponse;
import com.exen.example.domen.response.exception.CommonException;
import com.exen.example.service.PhraseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhraseServiceImpl implements PhraseService {
    @Override
    public ResponseEntity<Response> test() {
        //return new ResponseEntity<>(SuccessResponse.builder().data("SuccessResponse").build(), HttpStatus.OK);
        //return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code("VALIDATION_ERROR").message("Ошибка валидации").build()).build(), HttpStatus.BAD_REQUEST);
        //int x = 1/0;
        throw CommonException.builder().code(Code.TEST).message("test").httpStatus(HttpStatus.BAD_REQUEST).build();
    }
}
