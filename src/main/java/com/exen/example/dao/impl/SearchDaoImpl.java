package com.exen.example.dao.impl;

import com.exen.example.dao.SearchDao;
import com.exen.example.domain.api.search.searchTags.TagResp;
import com.exen.example.domain.api.search.searchTags.TagRespRowMapper;
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
}
