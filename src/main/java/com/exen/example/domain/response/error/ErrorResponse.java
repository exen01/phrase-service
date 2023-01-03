package com.exen.example.domain.response.error;

import com.exen.example.domain.response.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse implements Response {
    private Error error;
}
