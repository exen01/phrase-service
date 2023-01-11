package com.exen.example.dao.communication;

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
}
