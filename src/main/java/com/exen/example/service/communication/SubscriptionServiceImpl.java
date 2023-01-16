package com.exen.example.service.communication;

import com.exen.example.dao.common.CommonDao;
import com.exen.example.dao.communication.SubscriptionDao;
import com.exen.example.domain.api.common.CommonPhrasesResp;
import com.exen.example.domain.api.common.PhraseResp;
import com.exen.example.domain.api.communication.getMyPublishers.GetMyPublishersResp;
import com.exen.example.domain.api.communication.getMySubscribers.GetMySubscribersResp;
import com.exen.example.domain.api.communication.subscription.SubscriptionReq;
import com.exen.example.domain.api.communication.unsubscription.UnsubscriptionReq;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.response.Response;
import com.exen.example.domain.response.SuccessResponse;
import com.exen.example.domain.response.exception.CommonException;
import com.exen.example.service.common.CommonService;
import com.exen.example.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final ValidationUtils validationUtils;
    private final CommonDao commonDao;
    private final SubscriptionDao subscriptionDao;
    private final CommonService commonService;

    /**
     * Matches publisher id and user id, subscribes if not equal
     *
     * @param req         publisher id
     * @param accessToken user access token
     * @return response
     */
    @Override
    public ResponseEntity<Response> subscription(SubscriptionReq req, String accessToken) {
        validationUtils.validationRequest(req);
        long subUserId = commonDao.getUserIdByAccessToken(accessToken);

        long pubUserId = req.getPubUserId();
        log.info("subUserId: {}, pubUserId: {}", subUserId, pubUserId);

        if (subUserId == pubUserId) {
            throw CommonException.builder().code(Code.SUBSCRIPTION_LOGIC_ERROR).userMessage("Вы не можете подписаться на себя").httpStatus(HttpStatus.BAD_REQUEST).build();
        }

        subscriptionDao.subscription(subUserId, pubUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Unsubscribe user from publisher
     *
     * @param req         publisher id
     * @param accessToken user access token
     * @return response
     */
    @Override
    public ResponseEntity<Response> unsubscription(UnsubscriptionReq req, String accessToken) {
        validationUtils.validationRequest(req);
        long subUserId = commonDao.getUserIdByAccessToken(accessToken);

        long pubUserId = req.getPubUserId();
        log.info("subUserId: {}, pubUserId: {}", subUserId, pubUserId);

        subscriptionDao.unsubscription(subUserId, pubUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Gets user subscribers
     *
     * @param accessToken user access token
     * @return list of subscribers
     */
    @Override
    public ResponseEntity<Response> getMySubscribers(String accessToken) {
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        log.info("userId: {}", userId);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(GetMySubscribersResp.builder()
                                .subscribers(subscriptionDao.getMySubscribers(userId)).build()
                        ).build(), HttpStatus.OK);
    }

    /**
     * Gets user publishers
     *
     * @param accessToken user access token
     * @return list of publishers
     */
    @Override
    public ResponseEntity<Response> getMyPublishers(String accessToken) {
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        log.info("userId: {}", userId);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(GetMyPublishersResp.builder()
                                .publishers(subscriptionDao.getMyPublishers(userId)).build()
                        ).build(), HttpStatus.OK);
    }

    /**
     * Gets user's publishers phrases
     *
     * @param accessToken user access token
     * @param from        first phrase in the list
     * @param limit       number of phrases in the list
     * @return list of publishers phrases
     */
    @Override
    public ResponseEntity<Response> getMyPublishersPhrases(String accessToken, int from, int limit) {
        validationUtils.validationDecimalMin("from", from, 0);
        validationUtils.validationDecimalMin("limit", limit, 1);

        long userId = commonDao.getUserIdByAccessToken(accessToken);
        log.info("userId: {}", userId);

        List<PhraseResp> phraseRespList = subscriptionDao.getMyPublishersPhrases(userId, from, limit);
        commonService.phraseEnrichment(phraseRespList);

        return new ResponseEntity<>(SuccessResponse.builder().data(
                CommonPhrasesResp.builder().phrases(phraseRespList).build()).build(), HttpStatus.OK);
    }
}
