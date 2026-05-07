CREATE DATABASE agricultural_federation_db;

CREATE USER agricultural_federation_db_manager WITH PASSWORD '123456';

GRANT CONNECT ON DATABASE agricultural_federation_db TO agricultural_federation_db_manager;

\c agricultural_federation_db

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agricultural_federation_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agricultural_federation_db_manager;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL PRIVILEGES ON TABLES TO agricultural_federation_db_manager;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL PRIVILEGES ON SEQUENCES TO agricultural_federation_db_manager;