DROP TABLE if EXISTS param_schema.ptj_requests;
CREATE TABLE param_schema.ptj_requests(
    ptj_id serial PRIMARY KEY,
    -- 外部キー
    user_student_number VARCHAR(32) NOT NULL REFERENCES param_schema.users(student_number)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    subject_id SERIAL NOT NULL REFERENCES subjects_schema.subjects(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    -- キー
    ptj_date DATE NOT NULL, --アルバイトの日付
    start_time TIME NOT NULL, --開始時間
    finish_time TIME NOT NULL, --終了時間
    break_time_minutes INT NOT NULL, --休憩時間
    office_hours FLOAT NOT NULL, --勤務時間
    duties VARCHAR(64) NOT NULL --勤務内容
);

GRANT SELECT, UPDATE, INSERT, DELETE, REFERENCES ON param_schema.ptj_requests TO student;
GRANT USAGE ON SEQUENCE param_schema.ptj_requests_ptj_id_seq TO student;