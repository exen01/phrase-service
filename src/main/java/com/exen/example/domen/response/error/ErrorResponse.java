package com.exen.example.domen.response.error;

import com.exen.example.domen.response.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse implements Response {
    private Error error;
}
