package com.exen.example.util;

import com.exen.example.domain.constant.Code;
import com.exen.example.domain.response.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationUtils {
    private final Validator validator;

    /**
     * Validate request
     *
     * @param req request
     * @param <T> type of data
     */
    public <T> void validationRequest(T req) {
        if (req != null) {
            Set<ConstraintViolation<T>> result = validator.validate(req);
            if (!result.isEmpty()) {
                String resultValidations = result.stream()
                        .map(ConstraintViolation::getMessage)
                        .reduce((s1, s2) -> s1 + " " + s2).orElse("");
                log.error("Переданный в запросе json невалиден, ошибки валидации: {}", resultValidations);
                throw CommonException.builder().code(Code.REQUEST_VALIDATION_ERROR)
                        .techMessage(resultValidations)
                        .httpStatus(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    /**
     * Validate field value
     *
     * @param fieldName  field name
     * @param fieldValue field value
     * @param constraint constraint of field
     */
    public void validationDecimalMin(String fieldName, int fieldValue, int constraint) {
        if (fieldValue < constraint) {
            throw CommonException.builder().code(Code.REQUEST_VALIDATION_ERROR)
                    .techMessage(fieldName + " должно быть больше или равно " + constraint)
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Validate field value
     *
     * @param fieldName  field name
     * @param fieldValue field value
     * @param constraint constraint of field
     */
    public void validationDecimalMin(String fieldName, long fieldValue, long constraint) {
        if (fieldValue < constraint) {
            throw CommonException.builder().code(Code.REQUEST_VALIDATION_ERROR)
                    .techMessage(fieldName + " должно быть больше или равно " + constraint)
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
