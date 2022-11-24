package com.exen.example.service;

import com.exen.example.domen.response.Response;
import org.springframework.http.ResponseEntity;

public interface PhraseService {
    ResponseEntity<Response> test();
}
