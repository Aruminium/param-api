package com.example.wsbp.domain.models.ptj;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class PtjRowMapper implements RowMapper<PartTimeJob> {
    @Override
    public PartTimeJob mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("ptj_id");
        String studentNumber = rs.getString("user_student_number");
        int subjectId = rs.getInt("subject_id");
        Date ptjDate = rs.getDate("ptj_date");
        Time startTime = rs.getTime("start_time");
        Time finishTime = rs.getTime("finish_time");
        int breakTimeMinutes = rs.getInt("break_time_minutes");
        float officeHours = rs.getFloat("office_hours");
        String duties = rs.getString("duties");
        return new PartTimeJob(id, studentNumber, subjectId, ptjDate, startTime, finishTime, breakTimeMinutes, officeHours, duties);
    }
}
