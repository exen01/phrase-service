package com.exen.example.domen.response.exception;

import com.exen.example.domen.constant.Code;
import com.exen.example.domen.response.error.Error;
import com.exen.example.domen.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class PhraseServiceErrorHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException exception) {
        log.error("Common error: {}", exception.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code(exception.getCode()).message(exception.getMessage()).build()).build(), exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrorException(Exception exception) {
        exception.printStackTrace();
        log.error("Internal server error: {}", exception.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code(Code.INTERNAL_SERVER_ERROR).message("Внутренняя ошибка сервера").build()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
