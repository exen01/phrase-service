package com.exen.example.controller.communication;

import com.exen.example.domain.api.communication.subscribe.subscription.SubscriptionReq;
import com.exen.example.domain.api.communication.subscribe.unsubscription.UnsubscriptionReq;
import com.exen.example.domain.response.Response;
import com.exen.example.service.communication.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("phrase-service-public/communication/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/subscribers")
    public ResponseEntity<Response> getMySubscribers(@RequestHeader String accessToken) {
        log.info("Start endpoint getMySubscribers, accessToken: {}", accessToken);
        ResponseEntity<Response> response = subscriptionService.getMySubscribers(accessToken);
        log.info("End endpoint getMySubscribers, response: {}", response);
        return response;
    }

    @GetMapping("/publishers")
    public ResponseEntity<Response> getMyPublishers(@RequestHeader String accessToken) {
        log.info("Start endpoint getMyPublishers, accessToken: {}", accessToken);
        ResponseEntity<Response> response = subscriptionService.getMyPublishers(accessToken);
        log.info("End endpoint getMyPublishers, response: {}", response);
        return response;
    }

    @PostMapping("/subscription")
    public ResponseEntity<Response> subscription(@RequestHeader String accessToken, @RequestBody final SubscriptionReq req) {
        log.info("Start endpoint subscription, accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = subscriptionService.subscription(req, accessToken);
        log.info("End endpoint subscription, response: {}", response);
        return response;
    }

    @PostMapping("/unsubscription")
    public ResponseEntity<Response> unsubscription(@RequestHeader String accessToken, @RequestBody final UnsubscriptionReq req) {
        log.info("Start endpoint unsubscription, accessToken: {}, request: {}", accessToken, req);
        ResponseEntity<Response> response = subscriptionService.unsubscription(req, accessToken);
        log.info("End endpoint unsubscription, response: {}", response);
        return response;
    }

    @GetMapping("/publishers-phrases/{from}/{limit}")
    public ResponseEntity<Response> getMyPublishersPhrases(@RequestHeader String accessToken, @PathVariable int from, @PathVariable int limit) {
        log.info("Start endpoint getMyPublishersPhrases, accessToken: {}, from: {}, limit: {}", accessToken, from, limit);
        ResponseEntity<Response> response = subscriptionService.getMyPublishersPhrases(accessToken, from, limit);
        log.info("End endpoint getMyPublishersPhrases, response: {}", response);
        return response;
    }


}
