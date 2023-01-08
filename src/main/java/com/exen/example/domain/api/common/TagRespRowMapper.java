package com.exen.example.domain.api.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRespRowMapper implements RowMapper<TagResp> {
    /**
     * Implementations must implement this method to map each row of data
     * in the ResultSet. This method should not call {@code next()} on
     * the ResultSet; it is only supposed to map values of the current row.
     *
     * @param row    the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row (maybe {@code null})
     * @throws SQLException if an SQLException is encountered getting
     *                      column values (that is, there's no need to catch SQLException)
     */
    @Override
    public TagResp mapRow(ResultSet row, int rowNum) throws SQLException {
        return TagResp.builder()
                .tagId(row.getLong("id"))
                .text(row.getString("text"))
                .build();
    }
}
