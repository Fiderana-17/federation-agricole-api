-- =============================================
-- NETTOYAGE
-- =============================================
TRUNCATE TABLE attendance CASCADE;
TRUNCATE TABLE activities CASCADE;
TRUNCATE TABLE collectivity_transactions CASCADE;
TRUNCATE TABLE member_payments CASCADE;
TRUNCATE TABLE membership_fees CASCADE;
TRUNCATE TABLE financial_accounts CASCADE;
TRUNCATE TABLE members CASCADE;
TRUNCATE TABLE collectivities CASCADE;

-- =============================================
-- COLLECTIVITES (inchangées)
-- =============================================
INSERT INTO collectivities(id, number, name, location, specialization) VALUES
                                                                           ('col-1', '1', 'Mpanorina', 'Ambatondrazaka', 'Riziculture'),
                                                                           ('col-2', '2', 'Dobo voalohany', 'Ambatondrazaka', 'Pisciculture'),
                                                                           ('col-3', '3', 'Tantely mamy', 'Brickaville', 'Apiculture');

-- =============================================
-- MEMBRES ANCIENS (membership_date = 01/01/2026)
-- =============================================
INSERT INTO members(id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, membership_date) VALUES
                                                                                                                                                               ('C1-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', 'Lot II V M Ambato.', 'Riziculteur', 341234567, 'member.1@fed-agri.mg', 'PRESIDENT', 'col-1', '2026-01-01'),
                                                                                                                                                               ('C1-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', 'Lot II F Ambato.', 'Agriculteur', 321234567, 'member.2@fed-agri.mg', 'VICE_PRESIDENT', 'col-1', '2026-01-01'),
                                                                                                                                                               ('C1-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', 'Lot II J Ambato.', 'Collecteur', 331234567, 'member.3@fed-agri.mg', 'SECRETARY', 'col-1', '2026-01-01'),
                                                                                                                                                               ('C1-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', 381234567, 'member.4@fed-agri.mg', 'TREASURER', 'col-1', '2026-01-01'),
                                                                                                                                                               ('C1-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', 'Lot UV 80 Ambato.', 'Riziculteur', 373434567, 'member.5@fed-agri.mg', 'SENIOR', 'col-1', '2026-01-01'),
                                                                                                                                                               ('C1-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.', 'Riziculteur', 372234567, 'member.6@fed-agri.mg', 'SENIOR', 'col-1', '2026-01-01'),
                                                                                                                                                               ('C1-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', 'Lot UV 7 Ambato.', 'Riziculteur', 374234567, 'member.7@fed-agri.mg', 'SENIOR', 'col-1', '2026-01-01'),
                                                                                                                                                               ('C1-M8', 'Prénom membre 8', 'Nom membre 8', '1975-08-20', 'MALE', 'Lot UV 8 Ambato.', 'Riziculteur', 370234567, 'member.8@fed-agri.mg', 'SENIOR', 'col-1', '2026-01-01'),
                                                                                                                                                               ('C2-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', 'Lot II V M Ambato.', 'Riziculteur', 341234567, 'member.1@fed-agri.mg', 'SENIOR', 'col-2', '2026-01-01'),
                                                                                                                                                               ('C2-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', 'Lot II F Ambato.', 'Agriculteur', 321234567, 'member.2@fed-agri.mg', 'SENIOR', 'col-2', '2026-01-01'),
                                                                                                                                                               ('C2-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', 'Lot II J Ambato.', 'Collecteur', 331234567, 'member.3@fed-agri.mg', 'SENIOR', 'col-2', '2026-01-01'),
                                                                                                                                                               ('C2-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', 381234567, 'member.4@fed-agri.mg', 'SENIOR', 'col-2', '2026-01-01'),
                                                                                                                                                               ('C2-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', 'Lot UV 80 Ambato.', 'Riziculteur', 373434567, 'member.5@fed-agri.mg', 'PRESIDENT', 'col-2', '2026-01-01'),
                                                                                                                                                               ('C2-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.', 'Riziculteur', 372234567, 'member.6@fed-agri.mg', 'VICE_PRESIDENT', 'col-2', '2026-01-01'),
                                                                                                                                                               ('C2-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', 'Lot UV 7 Ambato.', 'Riziculteur', 374234567, 'member.7@fed-agri.mg', 'SECRETARY', 'col-2', '2026-01-01'),
                                                                                                                                                               ('C2-M8', 'Prénom membre 8', 'Nom membre 8', '1975-08-20', 'MALE', 'Lot UV 8 Ambato.', 'Riziculteur', 370234567, 'member.8@fed-agri.mg', 'TREASURER', 'col-2', '2026-01-01'),
                                                                                                                                                               ('C3-M1', 'Prénom membre 9', 'Nom membre 9', '1988-01-02', 'MALE', 'Lot 33 J Antsirabe', 'Apiculteur', 34034567, 'member.9@fed-agri.mg', 'PRESIDENT', 'col-3', '2026-01-01'),
                                                                                                                                                               ('C3-M2', 'Prénom membre 10', 'Nom membre 10', '1982-03-05', 'MALE', 'Lot 2 J Antsirabe', 'Agriculteur', 338634567, 'member.10@fed-agri.mg', 'VICE_PRESIDENT', 'col-3', '2026-01-01'),
                                                                                                                                                               ('C3-M3', 'Prénom membre 11', 'Nom membre 11', '1992-03-12', 'MALE', 'Lot 8 KM Antsirabe', 'Collecteur', 338234567, 'member.11@fed-agri.mg', 'SECRETARY', 'col-3', '2026-01-01'),
                                                                                                                                                               ('C3-M4', 'Prénom membre 12', 'Nom membre 12', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe', 'Distributeur', 382334567, 'member.12@fed-agri.mg', 'TREASURER', 'col-3', '2026-01-01'),
                                                                                                                                                               ('C3-M5', 'Prénom membre 13', 'Nom membre 13', '1999-08-11', 'MALE', 'Lot UV 80 Antsirabe', 'Apiculteur', 373365567, 'member.13@fed-agri.mg', 'SENIOR', 'col-3', '2026-01-01'),
                                                                                                                                                               ('C3-M6', 'Prénom membre 14', 'Nom membre 14', '1998-09-08', 'FEMALE', 'Lot UV 6 Antsirabe', 'Apiculteur', 378234567, 'member.14@fed-agri.mg', 'SENIOR', 'col-3', '2026-01-01'),
                                                                                                                                                               ('C3-M7', 'Prénom membre 15', 'Nom membre 15', '1998-01-13', 'MALE', 'Lot UV 7 Antsirabe', 'Apiculteur', 374914567, 'member.15@fed-agri.mg', 'SENIOR', 'col-3', '2026-01-01'),
                                                                                                                                                               ('C3-M8', 'Prénom membre 16', 'Nom membre 16', '1975-02-08', 'MALE', 'Lot UV 8 Antsirabe', 'Apiculteur', 370634567, 'member.16@fed-agri.mg', 'SENIOR', 'col-3', '2026-01-01');

-- =============================================
-- COMPTES FINANCIERS
-- =============================================
INSERT INTO financial_accounts(id, type, holder_name, mobile_banking_service, mobile_number, collectivity_id) VALUES
                                                                                                                  ('C1-A-CASH', 'CASH', null, null, null, 'col-1'),
                                                                                                                  ('C1-A-MOBILE-1', 'MOBILE_BANKING', 'Mpanorina', 'ORANGE_MONEY', 370489612, 'col-1'),
                                                                                                                  ('C2-A-CASH', 'CASH', null, null, null, 'col-2'),
                                                                                                                  ('C2-A-MOBILE-1', 'MOBILE_BANKING', 'Dobo voalohany', 'MVOLA', 320489613, 'col-2'),
                                                                                                                  ('C3-A-CASH', 'CASH', null, null, null, 'col-3');

-- Nouveaux comptes col-3 (tableau 11)
INSERT INTO financial_accounts(id, type, holder_name, bank_name, bank_code, bank_branch_code, bank_account_number, bank_account_key, collectivity_id) VALUES
                                                                                                                                                          ('C3-A-BANK-1', 'BANK', 'Koto', 'BMOI', 00004, 00001, 1234567890, 12, 'col-3'),
                                                                                                                                                          ('C3-A-BANK-2', 'BANK', 'Naina', 'BRED', 00005, 00003, 4567890123, 58, 'col-3');

INSERT INTO financial_accounts(id, type, holder_name, mobile_banking_service, mobile_number, collectivity_id) VALUES
    ('C3-A-MOBILE-1', 'MOBILE_BANKING', 'Koto', 'MVOLA', 341588612, 'col-3');

-- =============================================
-- COTISATIONS (tableaux 12, 13, 14)
-- =============================================
-- col-1
INSERT INTO membership_fees(id, label, status, frequency, eligible_from, amount, collectivity_id) VALUES
                                                                                                      ('cot-1', 'Cotisation annuelle', 'ACTIVE', 'ANNUALLY', '2026-01-01', 200000, 'col-1'),
                                                                                                      ('cot-2', 'Famangiana', 'ACTIVE', 'PUNCTUALLY', '2026-04-30', 20000, 'col-1');

-- col-2
INSERT INTO membership_fees(id, label, status, frequency, eligible_from, amount, collectivity_id) VALUES
                                                                                                      ('cot-3', 'Cotisation annuelle', 'ACTIVE', 'ANNUALLY', '2026-01-01', 200000, 'col-2'),
                                                                                                      ('cot-4', 'Cotisation 2025', 'INACTIVE', 'ANNUALLY', '2025-01-01', 100000, 'col-2');

-- col-3
INSERT INTO membership_fees(id, label, status, frequency, eligible_from, amount, collectivity_id) VALUES
    ('cot-5', 'Cotisation mensuelle', 'ACTIVE', 'MONTHLY', '2026-04-01', 25000, 'col-3');

-- =============================================
-- PAIEMENTS col-1 (tableau 15)
-- =============================================
INSERT INTO member_payments(id, amount, payment_mode, account_credited_id, creation_date, member_id, membership_fee_id) VALUES
                                                                                                                            ('pay-c1-m1', 200000, 'CASH',         'C1-A-CASH',     '2026-01-01', 'C1-M1', 'cot-1'),
                                                                                                                            ('pay-c1-m2', 200000, 'CASH',         'C1-A-CASH',     '2026-01-01', 'C1-M2', 'cot-1'),
                                                                                                                            ('pay-c1-m3', 200000, 'MOBILE_MONEY', 'C1-A-MOBILE-1', '2026-01-01', 'C1-M3', 'cot-1'),
                                                                                                                            ('pay-c1-m4', 200000, 'MOBILE_MONEY', 'C1-A-MOBILE-1', '2026-01-01', 'C1-M4', 'cot-1'),
                                                                                                                            ('pay-c1-m5', 150000, 'MOBILE_MONEY', 'C1-A-MOBILE-1', '2026-01-01', 'C1-M5', 'cot-1'),
                                                                                                                            ('pay-c1-m6', 100000, 'CASH',         'C1-A-CASH',     '2026-05-01', 'C1-M6', 'cot-1'),
                                                                                                                            ('pay-c1-m7', 80000,  'CASH',         'C1-A-CASH',     '2026-05-01', 'C1-M7', 'cot-1'),
                                                                                                                            ('pay-c1-m8', 90000,  'CASH',         'C1-A-CASH',     '2026-05-01', 'C1-M8', 'cot-1');

-- PAIEMENTS col-2 (tableau 16)
INSERT INTO member_payments(id, amount, payment_mode, account_credited_id, creation_date, member_id, membership_fee_id) VALUES
                                                                                                                            ('pay-c2-m1', 120000, 'CASH',         'C2-A-CASH',     '2026-01-01', 'C2-M1', 'cot-3'),
                                                                                                                            ('pay-c2-m2', 100000, 'CASH',         'C2-A-CASH',     '2026-01-01', 'C2-M2', 'cot-3'),
                                                                                                                            ('pay-c2-m3', 200000, 'CASH',         'C2-A-CASH',     '2026-01-01', 'C2-M3', 'cot-3'),
                                                                                                                            ('pay-c2-m4', 200000, 'CASH',         'C2-A-CASH',     '2026-01-01', 'C2-M4', 'cot-3'),
                                                                                                                            ('pay-c2-m5', 200000, 'CASH',         'C2-A-CASH',     '2026-01-01', 'C2-M5', 'cot-3'),
                                                                                                                            ('pay-c2-m6', 200000, 'CASH',         'C2-A-CASH',     '2026-01-01', 'C2-M6', 'cot-3'),
                                                                                                                            ('pay-c2-m7', 80000,  'MOBILE_MONEY', 'C2-A-MOBILE-1', '2026-01-01', 'C2-M7', 'cot-3'),
                                                                                                                            ('pay-c2-m8', 120000, 'MOBILE_MONEY', 'C2-A-MOBILE-1', '2026-01-01', 'C2-M8', 'cot-3');

-- PAIEMENTS col-3 (tableau 17)
INSERT INTO member_payments(id, amount, payment_mode, account_credited_id, creation_date, member_id, membership_fee_id) VALUES
                                                                                                                            ('pay-c3-m1-1',  25000, 'BANK', 'C3-A-BANK-1', '2026-04-04', 'C3-M1', 'cot-5'),
                                                                                                                            ('pay-c3-m2-1',  25000, 'BANK', 'C3-A-BANK-1', '2026-04-04', 'C3-M2', 'cot-5'),
                                                                                                                            ('pay-c3-m3-1',  25000, 'BANK', 'C3-A-BANK-1', '2026-04-04', 'C3-M3', 'cot-5'),
                                                                                                                            ('pay-c3-m4-1',  25000, 'BANK', 'C3-A-BANK-1', '2026-04-04', 'C3-M4', 'cot-5'),
                                                                                                                            ('pay-c3-m5-1',  25000, 'BANK', 'C3-A-BANK-2', '2026-04-04', 'C3-M5', 'cot-5'),
                                                                                                                            ('pay-c3-m6-1',  25000, 'BANK', 'C3-A-BANK-2', '2026-04-04', 'C3-M6', 'cot-5'),
                                                                                                                            ('pay-c3-m7-1',  35000, 'CASH', 'C3-A-CASH',   '2026-04-04', 'C3-M7', 'cot-5'),
                                                                                                                            ('pay-c3-m8-1',  35000, 'CASH', 'C3-A-CASH',   '2026-04-04', 'C3-M8', 'cot-5'),
                                                                                                                            ('pay-c3-m1-2',  25000, 'BANK', 'C3-A-BANK-1', '2026-05-01', 'C3-M1', 'cot-5'),
                                                                                                                            ('pay-c3-m2-2',  25000, 'BANK', 'C3-A-BANK-1', '2026-05-01', 'C3-M2', 'cot-5'),
                                                                                                                            ('pay-c3-m3-2',  15000, 'MOBILE_BANKING', 'C3-A-MOBILE-1', '2026-05-01', 'C3-M3', 'cot-5'),
                                                                                                                            ('pay-c3-m4-2',  15000, 'MOBILE_BANKING', 'C3-A-MOBILE-1', '2026-05-01', 'C3-M4', 'cot-5'),
                                                                                                                            ('pay-c3-m5-2',  20000, 'BANK', 'C3-A-BANK-2', '2026-05-01', 'C3-M5', 'cot-5'),
                                                                                                                            ('pay-c3-m6-2',  20000, 'BANK', 'C3-A-BANK-2', '2026-05-01', 'C3-M6', 'cot-5'),
                                                                                                                            ('pay-c3-m7-2',  5000,  'CASH', 'C3-A-CASH',   '2026-05-01', 'C3-M7', 'cot-5'),
                                                                                                                            ('pay-c3-m8-2',  5000,  'CASH', 'C3-A-CASH',   '2026-05-01', 'C3-M8', 'cot-5');

-- =============================================
-- TRANSACTIONS (générées automatiquement)
-- =============================================
INSERT INTO collectivity_transactions(id, creation_date, amount, payment_mode, account_credited_id, member_debited_id, collectivity_id) VALUES
                                                                                                                                            ('tx-c1-m1', '2026-01-01', 200000, 'CASH',         'C1-A-CASH',     'C1-M1', 'col-1'),
                                                                                                                                            ('tx-c1-m2', '2026-01-01', 200000, 'CASH',         'C1-A-CASH',     'C1-M2', 'col-1'),
                                                                                                                                            ('tx-c1-m3', '2026-01-01', 200000, 'MOBILE_MONEY', 'C1-A-MOBILE-1', 'C1-M3', 'col-1'),
                                                                                                                                            ('tx-c1-m4', '2026-01-01', 200000, 'MOBILE_MONEY', 'C1-A-MOBILE-1', 'C1-M4', 'col-1'),
                                                                                                                                            ('tx-c1-m5', '2026-01-01', 150000, 'MOBILE_MONEY', 'C1-A-MOBILE-1', 'C1-M5', 'col-1'),
                                                                                                                                            ('tx-c1-m6', '2026-05-01', 100000, 'CASH',         'C1-A-CASH',     'C1-M6', 'col-1'),
                                                                                                                                            ('tx-c1-m7', '2026-05-01', 80000,  'CASH',         'C1-A-CASH',     'C1-M7', 'col-1'),
                                                                                                                                            ('tx-c1-m8', '2026-05-01', 90000,  'CASH',         'C1-A-CASH',     'C1-M8', 'col-1'),
                                                                                                                                            ('tx-c2-m1', '2026-01-01', 120000, 'CASH',         'C2-A-CASH',     'C2-M1', 'col-2'),
                                                                                                                                            ('tx-c2-m2', '2026-01-01', 100000, 'CASH',         'C2-A-CASH',     'C2-M2', 'col-2'),
                                                                                                                                            ('tx-c2-m3', '2026-01-01', 200000, 'CASH',         'C2-A-CASH',     'C2-M3', 'col-2'),
                                                                                                                                            ('tx-c2-m4', '2026-01-01', 200000, 'CASH',         'C2-A-CASH',     'C2-M4', 'col-2'),
                                                                                                                                            ('tx-c2-m5', '2026-01-01', 200000, 'CASH',         'C2-A-CASH',     'C2-M5', 'col-2'),
                                                                                                                                            ('tx-c2-m6', '2026-01-01', 200000, 'CASH',         'C2-A-CASH',     'C2-M6', 'col-2'),
                                                                                                                                            ('tx-c2-m7', '2026-01-01', 80000,  'MOBILE_MONEY', 'C2-A-MOBILE-1', 'C2-M7', 'col-2'),
                                                                                                                                            ('tx-c2-m8', '2026-01-01', 120000, 'MOBILE_MONEY', 'C2-A-MOBILE-1', 'C2-M8', 'col-2'),
                                                                                                                                            ('tx-c3-m1-1', '2026-04-04', 25000, 'BANK', 'C3-A-BANK-1', 'C3-M1', 'col-3'),
                                                                                                                                            ('tx-c3-m2-1', '2026-04-04', 25000, 'BANK', 'C3-A-BANK-1', 'C3-M2', 'col-3'),
                                                                                                                                            ('tx-c3-m3-1', '2026-04-04', 25000, 'BANK', 'C3-A-BANK-1', 'C3-M3', 'col-3'),
                                                                                                                                            ('tx-c3-m4-1', '2026-04-04', 25000, 'BANK', 'C3-A-BANK-1', 'C3-M4', 'col-3'),
                                                                                                                                            ('tx-c3-m5-1', '2026-04-04', 25000, 'BANK', 'C3-A-BANK-2', 'C3-M5', 'col-3'),
                                                                                                                                            ('tx-c3-m6-1', '2026-04-04', 25000, 'BANK', 'C3-A-BANK-2', 'C3-M6', 'col-3'),
                                                                                                                                            ('tx-c3-m7-1', '2026-04-04', 35000, 'CASH', 'C3-A-CASH',   'C3-M7', 'col-3'),
                                                                                                                                            ('tx-c3-m8-1', '2026-04-04', 35000, 'CASH', 'C3-A-CASH',   'C3-M8', 'col-3'),
                                                                                                                                            ('tx-c3-m1-2', '2026-05-01', 25000, 'BANK', 'C3-A-BANK-1', 'C3-M1', 'col-3'),
                                                                                                                                            ('tx-c3-m2-2', '2026-05-01', 25000, 'BANK', 'C3-A-BANK-1', 'C3-M2', 'col-3'),
                                                                                                                                            ('tx-c3-m3-2', '2026-05-01', 15000, 'MOBILE_BANKING', 'C3-A-MOBILE-1', 'C3-M3', 'col-3'),
                                                                                                                                            ('tx-c3-m4-2', '2026-05-01', 15000, 'MOBILE_BANKING', 'C3-A-MOBILE-1', 'C3-M4', 'col-3'),
                                                                                                                                            ('tx-c3-m5-2', '2026-05-01', 20000, 'BANK', 'C3-A-BANK-2', 'C3-M5', 'col-3'),
                                                                                                                                            ('tx-c3-m6-2', '2026-05-01', 20000, 'BANK', 'C3-A-BANK-2', 'C3-M6', 'col-3'),
                                                                                                                                            ('tx-c3-m7-2', '2026-05-01', 5000,  'CASH', 'C3-A-CASH',   'C3-M7', 'col-3'),
                                                                                                                                            ('tx-c3-m8-2', '2026-05-01', 5000,  'CASH', 'C3-A-CASH',   'C3-M8', 'col-3');

-- =============================================
-- DROITS
-- =============================================
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agricultural_federation_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agricultural_federation_db_manager;