drop table if exists auth_user; -- auth_userテーブルがすでに存在すれば削除する

create TYPE user_type_enum AS ENUM ('SA', 'TA');
create table auth_user (
    student_number varchar(32) primary key not null,
    user_name varchar(32) not null,
    user_pass varchar(32) not null,
    user_type user_type_enum not null
);

drop table if exists ptj_request; -- ptj_requestテーブルがすでに存在すれば削除する

create table ptj_request(
    id serial primary key not null, --アルバイトを識別するID（INSERT時に勝手に付与される）
    student_number varchar(32) not null, --登録したユーザーの学籍番号
    ptj_date date not null, --アルバイトの日付
    working_hours_start float not null, --開始時間
    working_hours_finish float not null, --終了時間
    break_time float not null, --休憩時間
    sum_working_hours float not null, --合計勤務時間
    working_subject varchar(64) not null, --勤務対象（SA・TAの場合は科目名）
    working_content varchar(64) not null --勤務内容
);
