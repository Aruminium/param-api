drop table if exists auth_user; -- auth_userテーブルがすでに存在すれば削除する
create TYPE user_type_enum AS ENUM ('SA', 'TA');
create table users (
    student_number varchar(32) primary key, --学籍番号
    name varchar(32) not null, --学生の名前
    pass varchar(32) not null, --パスワード
    type user_type_enum not null --SAかTA
);

drop table if exists subjects;
create table subjects(
    id serial primary key, --教科ID
    name varchar(32) unique not null, --教科の名前
    teacher_name varchar(32) not null --担当教員の名前
);

drop table if exists ptj_requests;
create table ptj_requests(
    ptj_id serial primary key,
    -- 外部キー
    user_student_number varchar(32) not null references users(student_number)
        on delete cascade,
        on update cascade,
    subject_id serial not null references subjects(id)
        on delete cascade,
        on update cascade,
    -- キー
    ptj_date date not null, --アルバイトの日付
    start_time time not null, --開始時間
    finish_time time not null, --終了時間
    break_time_minutes int not null, --休憩時間
    office_hours float not null, --勤務時間
    duties varchar(64) not null --勤務内容
);