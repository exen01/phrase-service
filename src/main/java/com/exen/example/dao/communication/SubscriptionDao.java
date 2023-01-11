package com.exen.example.dao.communication;

import com.exen.example.domain.api.common.UserResp;

import java.util.List;

public interface SubscriptionDao {
    /**
     * Subscribes the user with subUserId to the user with pubUserId
     *
     * @param subUserId user subscriber id
     * @param pubUserId user publisher id
     */
    void subscription(long subUserId, long pubUserId);

    /**
     * Unsubscribes the user with subUserId from the user with pubUserId
     *
     * @param subUserId user subscriber id
     * @param pubUserId user publisher id
     */
    void unsubscription(long subUserId, long pubUserId);

    /**
     * Gets user subscribers
     *
     * @param userId user id
     * @return list of subscribers
     */
    List<UserResp> getMySubscribers(long userId);
}
