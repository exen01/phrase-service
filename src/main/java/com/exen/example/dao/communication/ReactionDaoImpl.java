package com.exen.example.dao.communication;

import com.exen.example.domain.api.communication.comment.CommentPhraseReq;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.dto.WhoseComment;
import com.exen.example.domain.dto.WhoseCommentRowMapper;
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

    /**
     * Gets comment author id and phrase author id
     *
     * @param commentId comment id
     * @return dto with ids
     */
    @Override
    public WhoseComment whoseComment(long commentId) {
        try {
            return jdbcTemplate.queryForObject("SELECT comment.user_id AS comment_user_id, p.user_id AS phrase_user_id " +
                    "FROM comment JOIN phrase p ON p.id = comment.phrase_id " +
                    "WHERE comment.id = ?;", new WhoseCommentRowMapper(), commentId);
        } catch (EmptyResultDataAccessException exception) {
            throw CommonException.builder().code(Code.COMMENT_NOT_FOUND).userMessage("Комментарий не найден.").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Remove comment
     *
     * @param commentId comment id
     */
    @Override
    public void deleteCommentPhrase(long commentId) {
        jdbcTemplate.update("DELETE FROM comment WHERE id = ?;", commentId);
    }
}
