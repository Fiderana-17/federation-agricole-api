-- Schema complet a executer UNE SEULE FOIS si pas encore fait

CREATE TABLE IF NOT EXISTS collectivities (
                                              id VARCHAR PRIMARY KEY,
                                              number VARCHAR UNIQUE,
                                              name VARCHAR UNIQUE,
                                              location VARCHAR
);

SELECT * FROM collectivities;



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

-- Colonne membership_date dans members
ALTER TABLE members ADD COLUMN IF NOT EXISTS membership_date DATE DEFAULT CURRENT_DATE;
UPDATE members SET membership_date = CURRENT_DATE WHERE membership_date IS NULL;

-- Table activities (v0.0.6)
CREATE TABLE IF NOT EXISTS activities (
                                          id VARCHAR PRIMARY KEY,
                                          label VARCHAR NOT NULL,
                                          activity_type VARCHAR NOT NULL,
                                          member_occupation_concerned VARCHAR,
                                          recurrence_week_ordinal INTEGER,
                                          recurrence_day_of_week VARCHAR,
                                          executive_date DATE,
                                          collectivity_id VARCHAR NOT NULL REFERENCES collectivities(id)
);

-- Table attendance (v0.0.6)
CREATE TABLE IF NOT EXISTS attendance (
                                          id VARCHAR PRIMARY KEY,
                                          activity_id VARCHAR NOT NULL REFERENCES activities(id),
                                          member_id VARCHAR NOT NULL REFERENCES members(id),
                                          attendance_status VARCHAR NOT NULL DEFAULT 'UNDEFINED'
);

-- Droits
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agricultural_federation_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agricultural_federation_db_manager;