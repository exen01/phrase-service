package com.exen.example.domain.api.search.searchPhrasesByTag;

import com.exen.example.domain.api.user.myPhrases.PhraseResp;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhraseRespRowMapper implements RowMapper<PhraseResp> {

    /**
     * Implementations must implement this method to map each row of data
     * in the ResultSet. This method should not call {@code next()} on
     * the ResultSet; it is only supposed to map values of the current row.
     *
     * @param row    the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row (may be {@code null})
     * @throws SQLException if an SQLException is encountered getting
     *                      column values (that is, there's no need to catch SQLException)
     */
    @Override
    public PhraseResp mapRow(ResultSet row, int rowNum) throws SQLException {
        return PhraseResp.builder()
                .phraseId(row.getLong("phrase_id"))
                .userId(row.getLong("user_id"))
                .nickname(row.getString("nickname"))
                .text(row.getString("text"))
                .timeInsert(row.getString("time_insert"))
                .build();
    }
}
