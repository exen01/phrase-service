package com.exen.example.dao.impl;

import com.exen.example.dao.UserDao;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.dto.User;
import com.exen.example.domain.entity.Phrase;
import com.exen.example.domain.entity.PhraseRowMapper;
import com.exen.example.domain.response.exception.CommonException;
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
import java.util.List;

@Slf4j
@Repository
@Transactional
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
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
            throw CommonException.builder().code(Code.USER_NOT_FOUND).userMessage("Пользователь не найден.").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Adds new phrase to DB
     *
     * @param userId user id
     * @param text   phrase text
     * @return new phrase id
     */
    @Override
    public long addPhrase(long userId, String text) {
        jdbcTemplate.update("INSERT INTO phrase(user_id,text) VALUES (?,?);", userId, text);
        return jdbcTemplate.queryForObject("SELECT id FROM phrase WHERE id = LAST_INSERT_ID();", Long.class);
    }

    /**
     * Adds new tag to DB
     * You need to have at least 1 tag in the table for this query to work correctly
     *
     * @param tag new tag
     */
    @Override
    public void addTag(String tag) {
        jdbcTemplate.update("INSERT INTO tag(text) SELECT DISTINCT LOWER(?) FROM tag WHERE NOT EXISTS (SELECT text FROM tag WHERE text = LOWER(?));", tag, tag);
    }

    /**
     * Adds tag to phrase
     *
     * @param phraseId phrase id
     * @param tag      tag
     */
    @Override
    public void addPhraseTag(long phraseId, String tag) {
        jdbcTemplate.update("INSERT IGNORE INTO phrase_tag(phrase_id,tag_id) VALUES (?, (SELECT id FROM tag WHERE text = LOWER(?)));", phraseId, tag);
    }

    /**
     * Gets phrases by user id
     *
     * @param userId user id
     * @return list of phrases
     */
    @Override
    public List<Phrase> getPhrasesByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM phrase WHERE user_id = ? ORDER BY time_insert DESC;", new PhraseRowMapper(), userId);
    }
}
