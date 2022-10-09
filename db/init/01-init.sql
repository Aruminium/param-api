CREATE SCHEMA subjects_schema;
CREATE SCHEMA param_schema;

CREATE ROLE student WITH LOGIN PASSWORD 'passw0rd';
GRANT USAGE ON SCHEMA subjects_schema TO student;
GRANT USAGE ON SCHEMA param_schema TO student;
