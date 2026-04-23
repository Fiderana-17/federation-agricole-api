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

TRUNCATE TABLE collectivity_transactions CASCADE;
TRUNCATE TABLE member_payments CASCADE;
TRUNCATE TABLE membership_fees CASCADE;
TRUNCATE TABLE members CASCADE;
TRUNCATE TABLE collectivities CASCADE;

-- Table financial_accounts
CREATE TABLE financial_accounts (
                                    id VARCHAR PRIMARY KEY,
                                    type VARCHAR NOT NULL, -- CASH, MOBILE_BANKING, BANK
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

-- Colonne collectivity_id dans members si pas encore presente
ALTER TABLE members ADD COLUMN IF NOT EXISTS collectivity_id VARCHAR REFERENCES collectivities(id);

-- Colonne collectivity_id dans collectivity_transactions si pas encore presente
ALTER TABLE collectivity_transactions ADD COLUMN IF NOT EXISTS collectivity_id VARCHAR REFERENCES collectivities(id);

-- Droits
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agricultural_federation_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agricultural_federation_db_manager;

ALTER TABLE collectivities
    ADD COLUMN specialization VARCHAR(100);





INSERT INTO collectivities (id, name, number, location, federation_approval, specialization) VALUES
                                                                                                 ('col-1', 'Mpanorina', '1', 'Ambatondrazaka', true, 'Riziculture'),
                                                                                                 ('col-2', 'Dobo voalohany', '2', 'Ambatondrazaka', true, 'Pisciculture'),
                                                                                                 ('col-3', 'Tantely mamy', '3', 'Brickaville', true, 'Apiculture');


INSERT INTO members (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id) VALUES
                                                                                                                                               ('C1-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'M', 'Lot II V M Ambato', 'Riziculteur', 341234567, 'member.1@fed-agri.mg', 'Président', 'col-1'),
                                                                                                                                               ('C1-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'M', 'Lot II F Ambato', 'Agriculteur', 321234567, 'member.2@fed-agri.mg', 'Vice président', 'col-1'),
                                                                                                                                               ('C1-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'M', 'Lot II J Ambato', 'Collecteur', 331234567, 'member.3@fed-agri.mg', 'Secrétaire', 'col-1'),
                                                                                                                                               ('C1-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'F', 'Lot A K 50 Ambato', 'Distributeur', 381234567, 'member.4@fed-agri.mg', 'Trésorier', 'col-1'),
                                                                                                                                               ('C1-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'M', 'Lot UV 80 Ambato', 'Riziculteur', 373434567, 'member.5@fed-agri.mg', 'Confirmé', 'col-1'),
                                                                                                                                               ('C1-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'F', 'Lot UV 6 Ambato', 'Riziculteur', 372234567, 'member.6@fed-agri.mg', 'Confirmé', 'col-1'),
                                                                                                                                               ('C1-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'M', 'Lot UV 7 Ambato', 'Riziculteur', 374234567, 'member.7@fed-agri.mg', 'Confirmé', 'col-1'),
                                                                                                                                               ('C1-M8', 'Prénom membre 6', 'Nom membre 8', '1975-08-20', 'M', 'Lot UV 8 Ambato', 'Riziculteur', 370234567, 'member.8@fed-agri.mg', 'Confirmé', 'col-1');




INSERT INTO members (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id) VALUES
                                                                                                                                               ('C2-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'M', 'Lot II V M Ambato', 'Riziculteur', 341234567, 'member.1@fed-agri.mg', 'Confirmé', 'col-2'),
                                                                                                                                               ('C2-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'M', 'Lot II F Ambato', 'Agriculteur', 321234567, 'member.2@fed-agri.mg', 'Confirmé', 'col-2'),
                                                                                                                                               ('C2-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'M', 'Lot II J Ambato', 'Collecteur', 331234567, 'member.3@fed-agri.mg', 'Confirmé', 'col-2'),
                                                                                                                                               ('C2-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'F', 'Lot A K 50 Ambato', 'Distributeur', 381234567, 'member.4@fed-agri.mg', 'Confirmé', 'col-2'),
                                                                                                                                               ('C2-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'M', 'Lot UV 80 Ambato', 'Riziculteur', 373434567, 'member.5@fed-agri.mg', 'Président', 'col-2'),
                                                                                                                                               ('C2-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'F', 'Lot UV 6 Ambato', 'Riziculteur', 372234567, 'member.6@fed-agri.mg', 'Vice président', 'col-2'),
                                                                                                                                               ('C2-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'M', 'Lot UV 7 Ambato', 'Riziculteur', 374234567, 'member.7@fed-agri.mg', 'Secrétaire', 'col-2'),
                                                                                                                                               ('C2-M8', 'Prénom membre 6', 'Nom membre 8', '1975-08-20', 'M', 'Lot UV 8 Ambato', 'Riziculteur', 370234567, 'member.8@fed-agri.mg', 'Trésorier', 'col-2');



INSERT INTO collectivity_members VALUES
                                     ('col-1','C1-M1'),('col-1','C1-M2'),('col-1','C1-M3'),('col-1','C1-M4'),
                                     ('col-1','C1-M5'),('col-1','C1-M6'),('col-1','C1-M7'),('col-1','C1-M8'),

                                     ('col-2','C2-M1'),('col-2','C2-M2'),('col-2','C2-M3'),('col-2','C2-M4'),
                                     ('col-2','C2-M5'),('col-2','C2-M6'),('col-2','C2-M7'),('col-2','C2-M8');


INSERT INTO collectivity_structure VALUES
                                       ('col-1','C1-M1','C1-M2','C1-M4','C1-M3'),
                                       ('col-2','C2-M5','C2-M6','C2-M8','C2-M7');


INSERT INTO member_referees VALUES
                                ('C1-M3','C1-M1'),('C1-M3','C1-M2'),
                                ('C1-M4','C1-M1'),('C1-M4','C1-M2'),
                                ('C1-M5','C1-M1'),('C1-M5','C1-M2'),
                                ('C1-M6','C1-M1'),('C1-M6','C1-M2'),
                                ('C1-M7','C1-M1'),('C1-M7','C1-M2'),
                                ('C1-M8','C1-M6'),('C1-M8','C1-M7'),

                                ('C2-M3','C2-M1'),('C2-M3','C2-M2'),
                                ('C2-M4','C2-M1'),('C2-M4','C2-M2'),
                                ('C2-M5','C2-M1'),('C2-M5','C2-M2'),
                                ('C2-M6','C2-M1'),('C2-M6','C2-M2'),
                                ('C2-M7','C2-M1'),('C2-M7','C2-M2'),
                                ('C2-M8','C2-M6'),('C2-M8','C2-M7');