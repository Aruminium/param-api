package com.example.wsbp.infrastructure.repository.ptj;

import com.example.wsbp.domain.models.ptj.IPartTimeJobRepository;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.domain.models.ptj.PtjRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * アルバイト内容をデータベースに記録するためのクラス（レポジトリー）
 */

@Repository
public class PartTimeJobRepository implements IPartTimeJobRepository {

    // SpringJDBCのデータベース制御用インスタンス
    private final JdbcTemplate jdbc;

    // jdbc の di/ioc 設定（Wicketとやり方が異なるので注意）
    @Autowired
    public PartTimeJobRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public List<PartTimeJob> find(final String studentNumber, final Date date) {
        String sql = "select * from param_schema.ptj_requests where user_student_number = ? and ptj_date = ? order by ptj_date, start_time, finish_time";

        return jdbc.query(sql, new PtjRowMapper(), studentNumber, date);
    }

    @Override
    public PartTimeJob find(final int id, final String studentNumber, final Date date) {
        String sql = "select * from param_schema.ptj_requests where ptj_id = ? and user_student_number = ? and ptj_date = ?";

        List<PartTimeJob> ptjRequests = jdbc.query(sql, new PtjRowMapper(), id, studentNumber, date);
        return ptjRequests.get(0);
    }

    @Override
    public List<PartTimeJob> find(final String studentNumber, final Date date, final String subjectName) {
        String sql = "select ptj_id, user_student_number, subject_id, ptj_date, start_time, finish_time, break_time_minutes, office_hours, duties from param_schema.ptj_requests inner join subjects_schema.subjects s on s.id = ptj_requests.subject_id where user_student_number = ? and to_char(ptj_date, 'yyyy-MM') = ? and s.name = ? order by ptj_date, start_time, finish_time";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return jdbc.query(sql, new PtjRowMapper(), studentNumber, sdf.format(date), subjectName);
    }
}
