package com.exen.example.service.communication;

import com.exen.example.domain.api.communication.subscription.SubscriptionReq;
import com.exen.example.domain.api.communication.unsubscription.UnsubscriptionReq;
import com.exen.example.domain.response.Response;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {

    /**
     * Matches publisher id and user id, subscribes if not equal
     *
     * @param req         publisher id
     * @param accessToken user access token
     * @return response
     */
    ResponseEntity<Response> subscription(SubscriptionReq req, String accessToken);

    /**
     * Unsubscribe user from publisher
     *
     * @param req         publisher id
     * @param accessToken user access token
     * @return response
     */
    ResponseEntity<Response> unsubscription(UnsubscriptionReq req, String accessToken);
}
