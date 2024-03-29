package com.exen.example.domain.api.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRespRowMapper implements RowMapper<UserResp> {

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
    public UserResp mapRow(ResultSet row, int rowNum) throws SQLException {
        return UserResp.builder()
                .userId(row.getLong("id"))
                .nickname(row.getString("nickname"))
                .build();
    }
}
