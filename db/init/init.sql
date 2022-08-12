drop table if exists auth_user; -- auth_userテーブルがすでに存在すれば削除する

create table auth_user (
    user_name varchar(32) primary key,
    user_pass varchar(32)
);

drop table if exists ptj_request; -- ptj_requestテーブルがすでに存在すれば削除する

create table ptj_request(
    id serial primary key, --アルバイトを識別するID（INSERT時に勝手に付与される）
    user_name varchar(32), --登録したユーザー
    ptj_date date, --アルバイトの日付
    working_hours_start float, --開始時間
    working_hours_finish float, --終了時間
    break_time float, --休憩時間
    working_type varchar(64), --勤務種別（SA or TA、将来的にはその他も）
    working_subject varchar(64), --勤務対象（SA・TAの場合は科目名）
    working_content varchar(64) --勤務内容
);
