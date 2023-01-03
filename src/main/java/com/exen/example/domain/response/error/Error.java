package com.exen.example.domain.response.error;

import com.exen.example.domain.constant.Code;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private Code code;
    private String userMessage;
    private String techMessage;

}
