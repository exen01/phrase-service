package com.exen.example.dao.impl.communication;

import com.exen.example.dao.communication.SubscriptionDao;
import com.exen.example.domain.api.common.UserResp;
import com.exen.example.domain.api.common.UserRespRowMapper;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.response.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
public class SubscriptionDaoImpl extends JdbcDaoSupport implements SubscriptionDao {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    /**
     * Subscribes the user with subUserId to the user with pubUserId
     *
     * @param subUserId user subscriber id
     * @param pubUserId user publisher id
     */
    @Override
    public void subscription(long subUserId, long pubUserId) {
        try {
            jdbcTemplate.update("INSERT INTO subscription(sub_user_id, pub_user_id) VALUES (?, ?);", subUserId, pubUserId);
        } catch (DuplicateKeyException exception) {
            log.error(exception.toString());
            throw CommonException.builder().code(Code.ALREADY_SUBSCRIBED).userMessage("Вы уже подписаны на этого пользователя.").httpStatus(HttpStatus.BAD_REQUEST).build();
        } catch (DataIntegrityViolationException exception) {
            log.error(exception.toString());
            throw CommonException.builder().code(Code.PUBLISHER_NOT_FOUND).userMessage("Не найден пользователь для подписки.").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Unsubscribes the user with subUserId from the user with pubUserId
     *
     * @param subUserId user subscriber id
     * @param pubUserId user publisher id
     */
    @Override
    public void unsubscription(long subUserId, long pubUserId) {
        jdbcTemplate.update("DELETE FROM subscription WHERE sub_user_id = ? AND pub_user_id = ?;", subUserId, pubUserId);
    }

    /**
     * Gets user subscribers
     *
     * @param userId user id
     * @return list of subscribers
     */
    @Override
    public List<UserResp> getMySubscribers(long userId) {
        return jdbcTemplate.query("SELECT id, nickname FROM user WHERE id IN (SELECT sub_user_id FROM subscription WHERE pub_user_id = ?);", new UserRespRowMapper(), userId);
    }

    /**
     * Gets user publishers
     *
     * @param userId user id
     * @return list of publishers
     */
    @Override
    public List<UserResp> getMyPublishers(long userId) {
        return jdbcTemplate.query("SELECT id, nickname FROM user WHERE id IN (SELECT pub_user_id FROM subscription WHERE sub_user_id = ?);", new UserRespRowMapper(), userId);
    }
}
