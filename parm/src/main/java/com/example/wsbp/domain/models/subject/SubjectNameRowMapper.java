package com.example.wsbp.domain.models.subject;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectNameRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNow) throws SQLException {
        return rs.getString("name");
    }
}
