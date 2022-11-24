package com.exen.example.domen.response.exception;

import com.exen.example.domen.constant.Code;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class CommonException extends RuntimeException {
    private final Code code;
    private final String message;
    private final HttpStatus httpStatus;
}
