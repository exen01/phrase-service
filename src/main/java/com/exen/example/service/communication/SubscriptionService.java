package com.exen.example.service.communication;

import com.exen.example.domain.api.communication.subscribe.subscription.SubscriptionReq;
import com.exen.example.domain.api.communication.subscribe.unsubscription.UnsubscriptionReq;
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

    /**
     * Gets user subscribers
     *
     * @param accessToken user access token
     * @return list of subscribers
     */
    ResponseEntity<Response> getMySubscribers(String accessToken);

    /**
     * Gets user publishers
     *
     * @param accessToken user access token
     * @return list of publishers
     */
    ResponseEntity<Response> getMyPublishers(String accessToken);

    /**
     * Gets user's publishers phrases
     *
     * @param accessToken user access token
     * @param from        first phrase in the list
     * @param limit       number of phrases in the list
     * @return list of publishers phrases
     */
    ResponseEntity<Response> getMyPublishersPhrases(String accessToken, int from, int limit);
}
