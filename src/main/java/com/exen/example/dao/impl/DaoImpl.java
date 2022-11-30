package com.exen.example.dao.impl;

import com.exen.example.dao.Dao;
import com.exen.example.domen.constant.Code;
import com.exen.example.domen.dto.User;
import com.exen.example.domen.response.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Slf4j
@Repository
@Transactional
public class DaoImpl extends JdbcDaoSupport implements Dao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    /**
     * Check existing nickname
     *
     * @param nickname nickname
     * @return true if nickname is exist, false otherwise
     */
    @Override
    public boolean isExistsNickname(String nickname) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT * FROM user WHERE nickname = ?);", Integer.class, nickname) != 0;
    }

    /**
     * Insert user data into database
     *
     * @param user user data object
     */
    @Override
    public void insertNewUser(User user) {
        jdbcTemplate.update(
                "INSERT INTO user(nickname, password, access_token) VALUES(?,?,?);",
                user.getNickname(),
                user.getEncryptPassword(),
                user.getAccessToken()
        );
    }

    /**
     * Find access token of user
     *
     * @param user user
     * @return access token
     */
    @Override
    public String getAccessToken(User user) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT access_token FROM user WHERE nickname = ? AND password = ?;",
                    String.class,
                    user.getNickname(),
                    user.getEncryptPassword()
            );
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.toString());
            throw CommonException.builder().code(Code.USER_NOT_FOUND).message("Пользователь не найден.").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }


}
