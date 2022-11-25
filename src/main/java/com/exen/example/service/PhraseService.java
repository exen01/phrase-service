package com.exen.example.service;

import com.exen.example.domen.api.RegistrationReq;
import com.exen.example.domen.response.Response;
import org.springframework.http.ResponseEntity;

public interface PhraseService {
    /**
     * Method for testing response
     *
     * @return response
     */
    ResponseEntity<Response> test();

    /**
     * Registration new user
     *
     * @param req request
     * @return response
     */
    ResponseEntity<Response> registration(RegistrationReq req);
}
