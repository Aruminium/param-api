package com.example.wsbp.domain.models.subject;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectRowMapper implements RowMapper<Subject> {
    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String teacher_name = rs.getString("teacher_name");
        return new Subject(id, name, teacher_name);
    }
}
