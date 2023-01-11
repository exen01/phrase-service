package com.exen.example.service.impl.communication;

import com.exen.example.dao.CommonDao;
import com.exen.example.dao.communication.SubscriptionDao;
import com.exen.example.domain.api.communication.subscription.SubscriptionReq;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.response.Response;
import com.exen.example.domain.response.SuccessResponse;
import com.exen.example.domain.response.exception.CommonException;
import com.exen.example.service.communication.SubscriptionService;
import com.exen.example.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final ValidationUtils validationUtils;
    private final CommonDao commonDao;
    private final SubscriptionDao subscriptionDao;

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
}
