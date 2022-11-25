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
}
