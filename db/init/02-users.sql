DROP TABLE if EXISTS param_schema.auth_user; -- auth_userテーブルがすでに存在すれば削除する

CREATE TYPE param_schema.user_type_enum AS ENUM ('SA', 'TA', 'ADMIN');
CREATE TABLE param_schema.users (
    student_number VARCHAR(8) PRIMARY KEY, --学籍番号
    name VARCHAR(32) NOT NULL, --学生の名前
    password VARCHAR(64) NOT NULL, --パスワード
    type param_schema.user_type_enum NOT NULL
);

GRANT SELECT, UPDATE, INSERT, DELETE ON param_schema.users TO student;