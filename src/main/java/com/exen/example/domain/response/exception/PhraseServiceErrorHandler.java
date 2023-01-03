package com.exen.example.domain.response.exception;

import com.exen.example.domain.constant.Code;
import com.exen.example.domain.response.error.Error;
import com.exen.example.domain.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class PhraseServiceErrorHandler {

    /**
     * Handle common exceptions
     *
     * @param exception common exception
     * @return error response
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException exception) {
        log.error("Common error: {}", exception.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code(exception.getCode()).userMessage(exception.getMessage()).build()).build(), exception.getHttpStatus());
    }

    /**
     * Handle all unexpected exceptions
     *
     * @param exception any exception
     * @return error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrorException(Exception exception) {
        exception.printStackTrace();
        log.error("Internal server error: {}", exception.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code(Code.INTERNAL_SERVER_ERROR).userMessage("Внутренняя ошибка сервера").build()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        log.error("MissingRequestHeaderException: {}", exception.toString());
        return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code(Code.MISSING_REQUEST_HEADER).techMessage(exception.getMessage()).build()).build(), HttpStatus.BAD_REQUEST);
    }
}
