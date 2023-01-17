package com.exen.example.dao.communication;

import com.exen.example.domain.api.communication.comment.CommentPhraseReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Slf4j
@Repository
@Transactional
public class ReactionDaoImpl extends JdbcDaoSupport implements ReactionDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    /**
     * Likes phrase
     *
     * @param userId   user id
     * @param phraseId phrase id
     */
    @Override
    public void likePhrase(long userId, long phraseId) {
        jdbcTemplate.update("INSERT IGNORE INTO like_phrase(phrase_id, user_id) VALUES (?, ?);", phraseId, userId);
    }

    /**
     * Remove like from phrase
     *
     * @param userId   user id
     * @param phraseId phrase id
     */
    @Override
    public void deleteLikePhrase(long userId, long phraseId) {
        jdbcTemplate.update("DELETE FROM like_phrase WHERE phrase_id = ? AND user_id = ?;", phraseId, userId);
    }

    /**
     * Comments phrase
     *
     * @param userId user id
     * @param req    phrase id and comment text
     */
    @Override
    public void commentPhrase(long userId, CommentPhraseReq req) {
        jdbcTemplate.update("INSERT IGNORE INTO comment(user_id, phrase_id, text) VALUES (?, ?, ?);", userId, req.getPhraseId(), req.getText());
    }
}
