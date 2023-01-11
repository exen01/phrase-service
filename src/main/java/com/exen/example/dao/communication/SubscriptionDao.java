package com.exen.example.dao.communication;

public interface SubscriptionDao {
    /**
     * Subscribes the user with subUserId to the user with pubUserId
     *
     * @param subUserId user subscriber id
     * @param pubUserId user publisher id
     */
    void subscription(long subUserId, long pubUserId);
}
