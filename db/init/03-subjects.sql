DROP TABLE if EXISTS subjects_schema.subjects;
CREATE TABLE subjects_schema.subjects(
    id SERIAL PRIMARY KEY, --教科ID
    name VARCHAR(32) UNIQUE NOT NULL, --教科の名前
    teacher_name VARCHAR(32) NOT NULL --担当教員の名前
);

GRANT SELECT ON subjects_schema.subjects TO student;