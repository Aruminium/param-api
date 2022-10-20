package com.example.wsbp.infrastructure.repository.subject;

import com.example.wsbp.domain.models.subject.ISubjectRepository;
import com.example.wsbp.domain.models.subject.Subject;
import com.example.wsbp.domain.models.subject.SubjectNameRowMapper;
import com.example.wsbp.domain.models.subject.SubjectRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubjectRepository implements ISubjectRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public SubjectRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<String> find() {
        String sql = "select name from subjects_schema.subjects order by id";
        return jdbc.query(sql, new SubjectNameRowMapper());
    }

    @Override
    public Subject findForName(final String name) {
        String sql = "select id, name, teacher_name from subjects_schema.subjects where name = ?";
        List<Subject> subjects = jdbc.query(sql, new SubjectRowMapper(), name);
        return subjects.get(0);
    }
}
