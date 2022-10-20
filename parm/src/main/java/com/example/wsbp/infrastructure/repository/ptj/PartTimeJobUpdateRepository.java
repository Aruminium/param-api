package com.example.wsbp.infrastructure.repository.ptj;

import com.example.wsbp.domain.models.ptj.IPartTimeJobUpdateRepository;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public class PartTimeJobUpdateRepository implements IPartTimeJobUpdateRepository {
    // SpringJDBCのデータベース制御用インスタンス
    private final JdbcTemplate jdbc;

    // jdbc の di/ioc 設定（Wicketとやり方が異なるので注意）
    @Autowired
    public PartTimeJobUpdateRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insert(final String userStudentNumber, final int subjectId, final Date ptjDate, final Time startTime, final Time finishTime, final int breakTimeMinutes, final float officeHours, final String duties) {
        final String sql = "insert into param_schema.ptj_requests (user_student_number, subject_id, ptj_date, start_time, finish_time, break_time_minutes, office_hours, duties) values (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(sql, userStudentNumber, subjectId, ptjDate, startTime, finishTime, breakTimeMinutes, officeHours, duties);
    }

    @Override
    public int update(final PartTimeJob newPtjRequest) {
//      studentNumberとidが正しければupdateできるようにする
        final String sql = "update param_schema.ptj_requests set subject_id = ?, start_time = ?, finish_time = ?, break_time_minutes = ?, office_hours = ?, duties = ? where ptj_id = ? and user_student_number = ? and ptj_id = ?";
        return jdbc.update(sql, newPtjRequest.getSubjectId(), newPtjRequest.getStartTime().getTime(), newPtjRequest.getFinishTime().getTime(), newPtjRequest.getBreakTimeMinutes(), newPtjRequest.getOfficeHours(), newPtjRequest.getDuties(), newPtjRequest.getId(), newPtjRequest.getStudentNumber(), newPtjRequest.getId());
    }

    @Override
    public int delete(PartTimeJob ptjRequest) {
        String sql = "delete from param_schema.ptj_requests where ptj_id = ? and user_student_number = ? and subject_id = ?";
        return jdbc.update(sql, ptjRequest.getId(), ptjRequest.getStudentNumber(), ptjRequest.getSubjectId());
    }

    @Override
    public void deletes(List<PartTimeJob> ptjRequests) {
        for (PartTimeJob ptjRequest : ptjRequests) {
            delete(ptjRequest);
        }
    }
}
