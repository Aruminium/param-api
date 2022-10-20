package com.example.wsbp.infrastructure.repository.user;

import com.example.wsbp.domain.models.user.IUserRepository;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.domain.models.user.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {

    // SpringJDBCのデータベース制御用インスタンス
    private final JdbcTemplate jdbc;

    // jdbc の di/ioc 設定（Wicketとやり方が異なるので注意）
    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void registerUser(final User user) {
        final String sql = "insert into param_schema.users (student_number, name, password, type) values (?, ?, ?, ?::param_schema.user_type_enum)";
        jdbc.update(sql, user.getStudentNumber(), user.getName(), user.getPass(), user.getUserType());
    }

    @Override
    public void removeUser(final String studentNumber) {
        final String sql = "delete from param_schema.users where student_number = ?";
        jdbc.update(sql, studentNumber);
    }

    @Override
    public User loginUser(final String studentNumber, final String pass) {
        // ユーザ名とパスワードが一致する情報が users テーブルにあれば、user を返す
        // テーブルになければ、nullを返す
        final String sql = "select student_number, name, password from param_schema.users where student_number = ? and password = ?";
        final RowMapper<User> rowMapper = new UserRowMapper();
        return jdbc.queryForObject(sql, rowMapper, studentNumber, pass);
    }
}
