drop table if exists auth_user; -- auth_userテーブルがすでに存在すれば削除する
create TYPE user_type_enum AS ENUM ('SA', 'TA');
create table users (
    student_number varchar(32) primary key not null, --学籍番号
    name varchar(32) not null, --学生の名前
    pass varchar(32) not null, --パスワード
    type user_type_enum not null --SAかTA
);

drop table if exists subjects;
create table subjects(
    id serial primary key not null, --教科ID
    name varchar(32) not null, --教科の名前
    teacher_name varchar(32) not null --担当教員の名前
);

drop table if exists ptj_requests;
create table ptj_requests(
    -- 外部キー
    user_student_number varchar(32) not null references users(student_number),
    subject_id serial not null references subjects(id),
    -- キー
    ptj_date date not null, --アルバイトの日付
    working_hours_start float not null, --開始時間
    working_hours_finish float not null, --終了時間
    break_time float not null, --休憩時間
    working_hours float not null, --勤務時間
    working_content varchar(64) not null --勤務内容
);