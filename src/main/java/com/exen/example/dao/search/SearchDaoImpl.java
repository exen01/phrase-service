package com.exen.example.dao.search;

import com.exen.example.dao.search.SearchDao;
import com.exen.example.domain.api.common.UserResp;
import com.exen.example.domain.api.common.UserRespRowMapper;
import com.exen.example.domain.api.search.searchPhrasesByPartWord.SearchPhrasesByPartWordReq;
import com.exen.example.domain.api.common.PhraseRespRowMapper;
import com.exen.example.domain.api.search.searchPhrasesByTag.SearchPhrasesByTagReq;
import com.exen.example.domain.api.common.TagResp;
import com.exen.example.domain.api.common.TagRespRowMapper;
import com.exen.example.domain.api.common.PhraseResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SearchDaoImpl extends JdbcDaoSupport implements SearchDao {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    /**
     * Searches tags by title
     *
     * @param partTag part or full title of tag
     * @return list of tags
     */
    @Override
    public List<TagResp> searchTags(String partTag) {
        return jdbcTemplate.query("SELECT id, text " +
                        "FROM (" +
                        "           SELECT tag.id, text, count(tag.id) AS c " +
                        "           FROM tag " +
                        "                   JOIN phrase_tag pt ON tag.id = pt.tag_id " +
                        "           WHERE text LIKE CONCAT(LOWER(?), '%') " +
                        "           GROUP BY tag.id " +
                        "           ORDER BY count(tag.id) DESC) t1 " +
                        "UNION " +
                        "SELECT id, text " +
                        "FROM (" +
                        "           SELECT tag.id, text, count(tag.id) AS c " +
                        "           FROM tag " +
                        "                   JOIN phrase_tag pt ON tag.id = pt.tag_id " +
                        "           WHERE text LIKE CONCAT('%_', LOWER(?), '%') " +
                        "           GROUP BY tag.id " +
                        "           ORDER BY count(tag.id) DESC) t2;"
                , new TagRespRowMapper(), partTag, partTag);
    }

    /**
     * Searches phrases by tag id
     *
     * @param req tag id, sort method
     * @return list of phrases
     */
    @Override
    public List<PhraseResp> searchPhrasesByTag(SearchPhrasesByTagReq req) {
        return jdbcTemplate.query("SELECT phrase.id AS phrase_id, u.id AS user_id, u.nickname, phrase.text, phrase.time_insert " +
                "FROM phrase " +
                "         JOIN user u on phrase.user_id = u.id " +
                "WHERE phrase.id IN (SELECT phrase_id FROM phrase_tag WHERE tag_id = ?) " +
                "ORDER BY " + req.getSort().getValue() + ";", new PhraseRespRowMapper(), req.getTagId());
    }

    /**
     * Searches phrases by part or full word
     *
     * @param req part or full word, sort method
     * @return list of phrases
     */
    @Override
    public List<PhraseResp> searchPhrasesByPartWord(SearchPhrasesByPartWordReq req) {
        return jdbcTemplate.query("SELECT phrase.id AS phrase_id, u.id AS user_id, u.nickname, phrase.text, phrase.time_insert " +
                "FROM phrase " +
                "         JOIN user u on phrase.user_id = u.id " +
                "WHERE LOWER(phrase.text) LIKE CONCAT('%',LOWER(?),'%') " +
                "ORDER BY " + req.getSort().getValue() + ";", new PhraseRespRowMapper(), req.getPartWord());
    }

    /**
     * Searches users by part or full nickname
     *
     * @param partNickname part or full nickname
     * @return list of users
     */
    @Override
    public List<UserResp> searchUsersByPartNickname(String partNickname) {
        return jdbcTemplate.query("SELECT id, nickname " +
                        "FROM(" +
                        "           SELECT id, nickname " +
                        "           FROM user " +
                        "           WHERE LOWER(nickname) LIKE CONCAT(LOWER(?), '%')) t1 " +
                        "UNION " +
                        "SELECT id, nickname " +
                        "FROM(" +
                        "           SELECT id, nickname " +
                        "           FROM user " +
                        "           WHERE LOWER(nickname) LIKE CONCAT('%', LOWER(?), '%')) t2;",
                new UserRespRowMapper(), partNickname, partNickname);
    }
}
