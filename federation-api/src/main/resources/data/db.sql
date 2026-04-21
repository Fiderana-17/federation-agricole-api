CREATE DATABASE agricultural_federation_db;

CREATE USER agricultural_federation_db_manager WITH PASSWORD '123456';

GRANT CONNECT ON DATABASE agricultural_federation_db TO agricultural_federation_db_manager;

\c agricultural_federation_db