CREATE TABLE members (
                         id VARCHAR(50) PRIMARY KEY,
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         birth_date DATE,
                         gender VARCHAR(10),
                         address TEXT,
                         profession VARCHAR(100),
                         phone_number BIGINT,
                         email VARCHAR(150),
                         occupation VARCHAR(50)
);

CREATE TABLE collectivities (
                                id VARCHAR(50) PRIMARY KEY,
                                location VARCHAR(150),
                                federation_approval BOOLEAN
);

CREATE TABLE collectivity_members (
                                      collectivity_id VARCHAR(50) NOT NULL,
                                      member_id VARCHAR(50) NOT NULL,
                                      PRIMARY KEY (collectivity_id, member_id),
                                      FOREIGN KEY (collectivity_id) REFERENCES collectivities(id),
                                      FOREIGN KEY (member_id) REFERENCES members(id)
);

CREATE TABLE collectivity_structure (
                                        collectivity_id VARCHAR(50) PRIMARY KEY,
                                        president_id VARCHAR(50),
                                        vice_president_id VARCHAR(50),
                                        treasurer_id VARCHAR(50),
                                        secretary_id VARCHAR(50),
                                        FOREIGN KEY (collectivity_id) REFERENCES collectivities(id),
                                        FOREIGN KEY (president_id) REFERENCES members(id),
                                        FOREIGN KEY (vice_president_id) REFERENCES members(id),
                                        FOREIGN KEY (treasurer_id) REFERENCES members(id),
                                        FOREIGN KEY (secretary_id) REFERENCES members(id)
);

CREATE TABLE member_referees (
                                 member_id VARCHAR(50) NOT NULL,
                                 referee_id VARCHAR(50) NOT NULL,
                                 PRIMARY KEY (member_id, referee_id),
                                 FOREIGN KEY (member_id) REFERENCES members(id),
                                 FOREIGN KEY (referee_id) REFERENCES members(id)
);

ALTER TABLE collectivities
    ADD COLUMN name VARCHAR(150) UNIQUE,
    ADD COLUMN number VARCHAR(50) UNIQUE;

INSERT INTO members(id, first_name, last_name, email) VALUES
                                                          ('parrain-001', 'Jean', 'Rakoto', 'jean.rakoto@email.com'),
                                                          ('parrain-002', 'Marie', 'Rabe', 'marie.rabe@email.com');


-- Membership fees
CREATE TABLE membership_fees (
                                 id VARCHAR PRIMARY KEY,
                                 eligible_from DATE,
                                 frequency VARCHAR NOT NULL,
                                 amount DOUBLE PRECISION NOT NULL,
                                 label VARCHAR,
                                 status VARCHAR NOT NULL DEFAULT 'ACTIVE',
                                 collectivity_id VARCHAR NOT NULL REFERENCES collectivities(id)
);

-- Member payments
CREATE TABLE member_payments (
                                 id VARCHAR PRIMARY KEY,
                                 amount DOUBLE PRECISION NOT NULL,
                                 payment_mode VARCHAR NOT NULL,
                                 account_credited_id VARCHAR,
                                 creation_date DATE NOT NULL,
                                 member_id VARCHAR NOT NULL REFERENCES members(id),
                                 membership_fee_id VARCHAR NOT NULL REFERENCES membership_fees(id)
);

-- Collectivity transactions
CREATE TABLE collectivity_transactions (
                                           id VARCHAR PRIMARY KEY,
                                           creation_date DATE NOT NULL,
                                           amount DOUBLE PRECISION NOT NULL,
                                           payment_mode VARCHAR NOT NULL,
                                           account_credited_id VARCHAR,
                                           member_debited_id VARCHAR REFERENCES members(id),
                                           collectivity_id VARCHAR NOT NULL REFERENCES collectivities(id)
);

-- Droits
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agricultural_federation_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agricultural_federation_db_manager;

-- Insérer des parrains
INSERT INTO members(id, first_name, last_name, email) VALUES
                                                          ('parrain-003', 'Jean', 'Rakoto', 'jean.rakoto@email.com'),
                                                          ('parrain-002', 'Marie', 'Rabe', 'marie.rabe@email.com');