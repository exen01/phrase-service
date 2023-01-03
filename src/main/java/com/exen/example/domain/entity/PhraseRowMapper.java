package com.exen.example.domain.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhraseRowMapper implements RowMapper<Phrase> {

    /**
     * Allocates data from DB row to fields of object
     *
     * @param row    data row from DB
     * @param rowNum data row number
     * @return phrase object
     * @throws SQLException SQL query exception
     */
    @Override
    public Phrase mapRow(ResultSet row, int rowNum) throws SQLException {
        return Phrase.builder()
                .id(row.getLong("id"))
                .userId(row.getLong("user_id"))
                .text(row.getString("text"))
                .timeInsert(row.getString("time_insert"))
                .build();
    }
}
