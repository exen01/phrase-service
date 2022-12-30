package com.exen.example.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptUtils {
    /**
     * Encrypt password
     *
     * @param password password
     * @return encrypted password
     */
    public String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
