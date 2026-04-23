-- Schema complet a executer UNE SEULE FOIS si pas encore fait

CREATE TABLE IF NOT EXISTS collectivities (
                                              id VARCHAR PRIMARY KEY,
                                              number VARCHAR UNIQUE,
                                              name VARCHAR UNIQUE,
                                              location VARCHAR
);


alter table collectivities add column specialization VARCHAR(100);

CREATE TABLE IF NOT EXISTS members (
                                       id VARCHAR PRIMARY KEY,
                                       first_name VARCHAR,
                                       last_name VARCHAR,
                                       birth_date DATE,
                                       gender VARCHAR,
                                       address VARCHAR,
                                       profession VARCHAR,
                                       phone_number BIGINT,
                                       email VARCHAR,
                                       occupation VARCHAR,
                                       collectivity_id VARCHAR REFERENCES collectivities(id)
    );

CREATE TABLE IF NOT EXISTS member_referees (
                                               member_id VARCHAR REFERENCES members(id),
    referee_id VARCHAR REFERENCES members(id),
    PRIMARY KEY (member_id, referee_id)
    );

CREATE TABLE IF NOT EXISTS membership_fees (
                                               id VARCHAR PRIMARY KEY,
                                               label VARCHAR,
                                               status VARCHAR NOT NULL DEFAULT 'ACTIVE',
                                               frequency VARCHAR NOT NULL,
                                               eligible_from DATE,
                                               amount DOUBLE PRECISION NOT NULL,
                                               collectivity_id VARCHAR NOT NULL REFERENCES collectivities(id)
    );

CREATE TABLE IF NOT EXISTS financial_accounts (
                                                  id VARCHAR PRIMARY KEY,
                                                  type VARCHAR NOT NULL,
                                                  holder_name VARCHAR,
                                                  mobile_banking_service VARCHAR,
                                                  mobile_number BIGINT,
                                                  bank_name VARCHAR,
                                                  bank_code INTEGER,
                                                  bank_branch_code INTEGER,
                                                  bank_account_number INTEGER,
                                                  bank_account_key INTEGER,
                                                  collectivity_id VARCHAR NOT NULL REFERENCES collectivities(id)
    );

CREATE TABLE IF NOT EXISTS member_payments (
                                               id VARCHAR PRIMARY KEY,
                                               amount DOUBLE PRECISION NOT NULL,
                                               payment_mode VARCHAR NOT NULL,
                                               account_credited_id VARCHAR REFERENCES financial_accounts(id),
    creation_date DATE NOT NULL,
    member_id VARCHAR NOT NULL REFERENCES members(id),
    membership_fee_id VARCHAR NOT NULL REFERENCES membership_fees(id)
    );

CREATE TABLE IF NOT EXISTS collectivity_transactions (
                                                         id VARCHAR PRIMARY KEY,
                                                         creation_date DATE NOT NULL,
                                                         amount DOUBLE PRECISION NOT NULL,
                                                         payment_mode VARCHAR NOT NULL,
                                                         account_credited_id VARCHAR REFERENCES financial_accounts(id),
    member_debited_id VARCHAR REFERENCES members(id),
    collectivity_id VARCHAR NOT NULL REFERENCES collectivities(id)
    );

-- Droits
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agricultural_federation_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agricultural_federation_db_manager;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO agricultural_federation_db_manager;