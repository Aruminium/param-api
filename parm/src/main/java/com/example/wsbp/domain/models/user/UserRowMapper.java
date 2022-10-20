package com.example.wsbp.domain.models.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String studentNumber = rs.getString("student_number");
        String name = rs.getString("name");
        String pass = rs.getString("password");
        return new User(studentNumber, name, pass);
    }
}
