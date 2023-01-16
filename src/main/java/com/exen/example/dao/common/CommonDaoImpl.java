package com.exen.example.dao.common;

import com.exen.example.dao.common.CommonDao;
import com.exen.example.domain.api.common.TagResp;
import com.exen.example.domain.api.common.TagRespRowMapper;
import com.exen.example.domain.constant.Code;
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
public class CommonDaoImpl extends JdbcDaoSupport implements CommonDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    /**
     * Gets user id by access token
     *
     * @param accessToken access token
     * @return object id
     */
    @Override
    public long getUserIdByAccessToken(String accessToken) {
        try {
            return jdbcTemplate.queryForObject("SELECT id FROM user WHERE access_token=?;", Long.class, accessToken);
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.toString());
            throw CommonException.builder().code(Code.AUTHORIZATION_ERROR).userMessage("Ошибка авторизации").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Gets tags by phrase id
     *
     * @param phraseId phrase id
     * @return list of tags
     */
    @Override
    public List<TagResp> getTagsByPhraseId(long phraseId) {
        return jdbcTemplate.query("SELECT text, id FROM tag WHERE id IN (SELECT tag_id FROM phrase_tag WHERE phrase_id = ?);", new TagRespRowMapper(), phraseId);
    }

    /**
     * Test
     *
     * @param s test value
     */
    @Override
    public void testSchedulerLock(String instanceName) {
        jdbcTemplate.update("INSERT INTO test_scheduler_lock(instance_name) VALUES(?);", instanceName);
    }
}
