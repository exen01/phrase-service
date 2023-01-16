package com.exen.example.dao.communication;

public interface ReactionDao {

    /**
     * Likes phrase
     *
     * @param userId   user id
     * @param phraseId phrase id
     */
    void likePhrase(long userId, long phraseId);

    /**
     * Remove like from phrase
     *
     * @param userId   user id
     * @param phraseId phrase id
     */
    void deleteLikePhrase(long userId, long phraseId);
}
