package com.exen.example.dao;

import com.exen.example.domen.dto.User;
import org.springframework.stereotype.Service;

@Service
public interface Dao {
    /**
     * Check existing nickname
     *
     * @param nickname nickname
     * @return true if nickname is exist, false otherwise
     */
    boolean isExistsNickname(String nickname);

    /**
     * Insert user data into database
     *
     * @param user user data object
     */
    void insertNewUser(User user);

    /**
     * Find access token of user
     *
     * @param user user
     * @return access token
     */
    String getAccessToken(User user);

    /**
     * Gets object id by access token
     *
     * @param accessToken access token
     * @return object id
     */
    long getIdByAccessToken(String accessToken);

    /**
     * Adds new phrase to DB
     *
     * @param userId user id
     * @param text   phrase text
     * @return new phrase id
     */
    long addPhrase(long userId, String text);

    /**
     * Adds new tag to DB
     *
     * @param tag new tag
     */
    void addTag(String tag);

    /**
     * Adds tag to phrase
     *
     * @param phraseId phrase id
     * @param tag      tag
     */
    void addPhraseTag(long phraseId, String tag);
}
