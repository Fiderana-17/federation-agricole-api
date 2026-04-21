CREATE DATABASE agricultural_federation_db;

CREATE USER agricultural_federation_db_manager WITH PASSWORD '123456';

GRANT CONNECT ON DATABASE agricultural_federation_db TO agricultural_federation_db_manager;

\c agricultural_federation_db

GRANT CREATE ON SCHEMA public TO agricultural_federation_db_manager;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO agricultural_federation_db_manager;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO agricultural_federation_db_manager;