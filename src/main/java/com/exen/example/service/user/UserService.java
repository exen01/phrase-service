package com.exen.example.service.user;

import com.exen.example.domain.api.user.login.LoginReq;
import com.exen.example.domain.api.user.publishPhrase.PublishPhraseReq;
import com.exen.example.domain.api.user.registration.RegistrationReq;
import com.exen.example.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface UserService {
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

    /**
     * Login user
     *
     * @param req request
     * @return response
     */
    ResponseEntity<Response> login(LoginReq req);

    /**
     * Publish phrase
     *
     * @param req         request
     * @param accessToken access token
     * @return response
     */
    ResponseEntity<Response> publishPhrase(PublishPhraseReq req, String accessToken);

    /**
     * Gets user phrases
     *
     * @param accessToken user access token
     * @return response
     */
    ResponseEntity<Response> getMyPhrases(String accessToken);
}
