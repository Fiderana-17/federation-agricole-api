-- Modifier activities pour supporter les occurrences multiples
-- Une activité récurrente génère plusieurs occurrences (activity_occurrences)
ALTER TABLE activities ADD COLUMN IF NOT EXISTS activity_type_extra VARCHAR; -- PUNCTUAL

-- Table des occurrences d'activités (chaque date concrète d'une activité)
CREATE TABLE IF NOT EXISTS activity_occurrences (
                                                    id VARCHAR PRIMARY KEY,
                                                    activity_id VARCHAR NOT NULL REFERENCES activities(id),
    occurrence_date DATE NOT NULL
    );

-- Modifier attendance pour référencer une occurrence et non juste une activité
-- On garde activity_id pour référence mais on ajoute occurrence_date
ALTER TABLE attendance ADD COLUMN IF NOT EXISTS occurrence_date DATE;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agricultural_federation_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agricultural_federation_db_manager;




                            -- =============================================
-- ACTIVITES
-- =============================================

-- col-1
INSERT INTO activities(id, label, activity_type, member_occupation_concerned,
                       recurrence_week_ordinal, recurrence_day_of_week,
                       executive_date, collectivity_id) VALUES
                                                            ('act-1', 'AG1', 'MEETING',
                                                             'JUNIOR,SENIOR,SECRETARY,TREASURER,VICE_PRESIDENT,PRESIDENT',
                                                             1, 'SA', null, 'col-1'),
                                                            ('act-2', 'Formation de base', 'TRAINING',
                                                             'JUNIOR',
                                                             2, 'SU', null, 'col-1');

-- col-2
INSERT INTO activities(id, label, activity_type, member_occupation_concerned,
                       recurrence_week_ordinal, recurrence_day_of_week,
                       executive_date, collectivity_id) VALUES
                                                            ('act-3', 'AG2', 'MEETING',
                                                             'JUNIOR,SENIOR,SECRETARY,TREASURER,VICE_PRESIDENT,PRESIDENT',
                                                             1, 'SU', null, 'col-2'),
                                                            ('act-4', 'Formation de base', 'TRAINING',
                                                             'JUNIOR',
                                                             3, 'SU', null, 'col-2'),
                                                            ('act-5', 'Perfectionnement', 'OTHER',
                                                             'SENIOR',
                                                             null, null, '2026-04-30', 'col-2');

-- col-3
INSERT INTO activities(id, label, activity_type, member_occupation_concerned,
                       recurrence_week_ordinal, recurrence_day_of_week,
                       executive_date, collectivity_id) VALUES
                                                            ('act-6', 'AG3', 'MEETING',
                                                             'JUNIOR,SENIOR,SECRETARY,TREASURER,VICE_PRESIDENT,PRESIDENT',
                                                             1, 'FR', null, 'col-3'),
                                                            ('act-7', 'Formation de base', 'TRAINING',
                                                             'JUNIOR',
                                                             4, 'WE', null, 'col-3');

-- =============================================
-- OCCURRENCES DES ACTIVITES
-- (dates concrètes de chaque activité)
-- =============================================
-- act-1 AG1 col-1 : 1er samedi de chaque mois
-- Janvier: 03/01, Février: 07/02, Mars: 07/03, Avril: 04/04
INSERT INTO activity_occurrences(id, activity_id, occurrence_date) VALUES
                                                                       ('occ-act1-jan', 'act-1', '2026-01-03'),
                                                                       ('occ-act1-feb', 'act-1', '2026-02-07'),
                                                                       ('occ-act1-mar', 'act-1', '2026-03-07'),
                                                                       ('occ-act1-apr', 'act-1', '2026-04-04');

-- act-2 Formation col-1 : 2è dimanche de chaque mois
-- Janvier: 11/01, Février: 08/02, Mars: 08/03, Avril: 12/04
INSERT INTO activity_occurrences(id, activity_id, occurrence_date) VALUES
                                                                       ('occ-act2-jan', 'act-2', '2026-01-11'),
                                                                       ('occ-act2-feb', 'act-2', '2026-02-08'),
                                                                       ('occ-act2-mar', 'act-2', '2026-03-08'),
                                                                       ('occ-act2-apr', 'act-2', '2026-04-12');

-- act-3 AG2 col-2 : 1er dimanche de chaque mois
-- Janvier: 04/01, Février: 01/02, Mars: 08/03, Avril: 05/04
INSERT INTO activity_occurrences(id, activity_id, occurrence_date) VALUES
                                                                       ('occ-act3-jan', 'act-3', '2026-01-04'),
                                                                       ('occ-act3-feb', 'act-3', '2026-02-01'),
                                                                       ('occ-act3-mar', 'act-3', '2026-03-08'),
                                                                       ('occ-act3-apr', 'act-3', '2026-04-05');

-- act-4 Formation col-2 : 3è dimanche de chaque mois
-- Janvier: 18/01, Février: 15/02, Mars: 15/03, Avril: 19/04
INSERT INTO activity_occurrences(id, activity_id, occurrence_date) VALUES
                                                                       ('occ-act4-jan', 'act-4', '2026-01-18'),
                                                                       ('occ-act4-feb', 'act-4', '2026-02-15'),
                                                                       ('occ-act4-mar', 'act-4', '2026-03-15'),
                                                                       ('occ-act4-apr', 'act-4', '2026-04-19');

-- act-5 Perfectionnement col-2 : ponctuel 30/04/2026
INSERT INTO activity_occurrences(id, activity_id, occurrence_date) VALUES
    ('occ-act5', 'act-5', '2026-04-30');

-- act-6 AG3 col-3 : 1er vendredi de chaque mois
-- Janvier: 02/01, Février: 06/02, Mars: 06/03, Avril: 03/04
INSERT INTO activity_occurrences(id, activity_id, occurrence_date) VALUES
                                                                       ('occ-act6-jan', 'act-6', '2026-01-02'),
                                                                       ('occ-act6-feb', 'act-6', '2026-02-06'),
                                                                       ('occ-act6-mar', 'act-6', '2026-03-06'),
                                                                       ('occ-act6-apr', 'act-6', '2026-04-03');

-- act-7 Formation col-3 : 4è mercredi de chaque mois
-- Janvier: 28/01, Février: 25/02, Mars: 25/03, Avril: 22/04
INSERT INTO activity_occurrences(id, activity_id, occurrence_date) VALUES
                                                                       ('occ-act7-jan', 'act-7', '2026-01-28'),
                                                                       ('occ-act7-feb', 'act-7', '2026-02-25'),
                                                                       ('occ-act7-mar', 'act-7', '2026-03-25'),
                                                                       ('occ-act7-apr', 'act-7', '2026-04-22');

-- =============================================
-- ATTENDANCES col-1 act-1 (AG1)
-- =============================================
-- Mars 07/03/2026 (tableau 24)
INSERT INTO attendance(id, activity_id, member_id, attendance_status, occurrence_date) VALUES
                                                                                           ('att-act1-mar-m1', 'act-1', 'C1-M1', 'ATTENDED', '2026-03-07'),
                                                                                           ('att-act1-mar-m2', 'act-1', 'C1-M2', 'ATTENDED', '2026-03-07'),
                                                                                           ('att-act1-mar-m3', 'act-1', 'C1-M3', 'ATTENDED', '2026-03-07'),
                                                                                           ('att-act1-mar-m4', 'act-1', 'C1-M4', 'ATTENDED', '2026-03-07'),
                                                                                           ('att-act1-mar-m5', 'act-1', 'C1-M5', 'ATTENDED', '2026-03-07'),
                                                                                           ('att-act1-mar-m6', 'act-1', 'C1-M6', 'ATTENDED', '2026-03-07'),
                                                                                           ('att-act1-mar-m7', 'act-1', 'C1-M7', 'MISSING',  '2026-03-07'),
                                                                                           ('att-act1-mar-m8', 'act-1', 'C1-M8', 'MISSING',  '2026-03-07');

-- Avril 04/04/2026 (tableau 25)
INSERT INTO attendance(id, activity_id, member_id, attendance_status, occurrence_date) VALUES
                                                                                           ('att-act1-apr-m1', 'act-1', 'C1-M1', 'ATTENDED', '2026-04-04'),
                                                                                           ('att-act1-apr-m2', 'act-1', 'C1-M2', 'ATTENDED', '2026-04-04'),
                                                                                           ('att-act1-apr-m3', 'act-1', 'C1-M3', 'MISSING',  '2026-04-04'),
                                                                                           ('att-act1-apr-m4', 'act-1', 'C1-M4', 'MISSING',  '2026-04-04'),
                                                                                           ('att-act1-apr-m5', 'act-1', 'C1-M5', 'ATTENDED', '2026-04-04'),
                                                                                           ('att-act1-apr-m6', 'act-1', 'C1-M6', 'ATTENDED', '2026-04-04'),
                                                                                           ('att-act1-apr-m7', 'act-1', 'C1-M7', 'ATTENDED', '2026-04-04'),
                                                                                           ('att-act1-apr-m8', 'act-1', 'C1-M8', 'ATTENDED', '2026-04-04');

-- =============================================
-- ATTENDANCES col-2 act-3 (AG2)
-- =============================================
-- Mars 08/03/2026 (tableau 26)
-- Note: membres col-1 assistent à col-2
INSERT INTO attendance(id, activity_id, member_id, attendance_status, occurrence_date) VALUES
                                                                                           ('att-act3-mar-m1', 'act-3', 'C1-M1', 'ATTENDED', '2026-03-08'),
                                                                                           ('att-act3-mar-m2', 'act-3', 'C1-M2', 'ATTENDED', '2026-03-08'),
                                                                                           ('att-act3-mar-m3', 'act-3', 'C1-M3', 'MISSING',  '2026-03-08'),
                                                                                           ('att-act3-mar-m4', 'act-3', 'C1-M4', 'MISSING',  '2026-03-08'),
                                                                                           ('att-act3-mar-m5', 'act-3', 'C1-M5', 'ATTENDED', '2026-03-08'),
                                                                                           ('att-act3-mar-m6', 'act-3', 'C1-M6', 'ATTENDED', '2026-03-08'),
                                                                                           ('att-act3-mar-m7', 'act-3', 'C1-M7', 'ATTENDED', '2026-03-08'),
                                                                                           ('att-act3-mar-m8', 'act-3', 'C1-M8', 'ATTENDED', '2026-03-08');

-- Avril 05/04/2026 (tableau 27)
INSERT INTO attendance(id, activity_id, member_id, attendance_status, occurrence_date) VALUES
                                                                                           ('att-act3-apr-m1', 'act-3', 'C1-M1', 'ATTENDED', '2026-04-05'),
                                                                                           ('att-act3-apr-m2', 'act-3', 'C1-M2', 'ATTENDED', '2026-04-05'),
                                                                                           ('att-act3-apr-m3', 'act-3', 'C1-M3', 'MISSING',  '2026-04-05'),
                                                                                           ('att-act3-apr-m4', 'act-3', 'C1-M4', 'ATTENDED', '2026-04-05'),
                                                                                           ('att-act3-apr-m5', 'act-3', 'C1-M5', 'ATTENDED', '2026-04-05'),
                                                                                           ('att-act3-apr-m6', 'act-3', 'C1-M6', 'ATTENDED', '2026-04-05'),
                                                                                           ('att-act3-apr-m7', 'act-3', 'C1-M7', 'ATTENDED', '2026-04-05'),
                                                                                           ('att-act3-apr-m8', 'act-3', 'C1-M8', 'MISSING',  '2026-04-05');

-- act-5 Perfectionnement 30/04/2026 (tableau 28)
INSERT INTO attendance(id, activity_id, member_id, attendance_status, occurrence_date) VALUES
                                                                                           ('att-act5-m1', 'act-5', 'C1-M1', 'ATTENDED',  '2026-04-30'),
                                                                                           ('att-act5-m2', 'act-5', 'C1-M2', 'ATTENDED',  '2026-04-30'),
                                                                                           ('att-act5-m3', 'act-5', 'C1-M3', 'ATTENDED',  '2026-04-30'),
                                                                                           ('att-act5-m4', 'act-5', 'C1-M4', 'MISSING',   '2026-04-30'),
                                                                                           ('att-act5-m5', 'act-5', 'C1-M5', 'UNDEFINED', '2026-04-30'),
                                                                                           ('att-act5-m6', 'act-5', 'C1-M6', 'UNDEFINED', '2026-04-30'),
                                                                                           ('att-act5-m7', 'act-5', 'C1-M7', 'UNDEFINED', '2026-04-30'),
                                                                                           ('att-act5-m8', 'act-5', 'C1-M8', 'UNDEFINED', '2026-04-30');

-- =============================================
-- ATTENDANCES col-3 act-6 (AG3)
-- =============================================
-- Mars 06/03/2026 (tableau 29)
INSERT INTO attendance(id, activity_id, member_id, attendance_status, occurrence_date) VALUES
                                                                                           ('att-act6-mar-m1', 'act-6', 'C3-M1', 'ATTENDED', '2026-03-06'),
                                                                                           ('att-act6-mar-m2', 'act-6', 'C3-M2', 'ATTENDED', '2026-03-06'),
                                                                                           ('att-act6-mar-m3', 'act-6', 'C3-M3', 'ATTENDED', '2026-03-06'),
                                                                                           ('att-act6-mar-m4', 'act-6', 'C3-M4', 'ATTENDED', '2026-03-06'),
                                                                                           ('att-act6-mar-m5', 'act-6', 'C3-M5', 'ATTENDED', '2026-03-06'),
                                                                                           ('att-act6-mar-m6', 'act-6', 'C3-M6', 'ATTENDED', '2026-03-06'),
                                                                                           ('att-act6-mar-m7', 'act-6', 'C3-M7', 'MISSING',  '2026-03-06'),
                                                                                           ('att-act6-mar-m8', 'act-6', 'C3-M8', 'MISSING',  '2026-03-06');

-- Avril 03/04/2026 (tableau 30)
-- Note: C1-M1 membre col-1 assiste à col-3 → ne compte pas dans assiduité col-3
INSERT INTO attendance(id, activity_id, member_id, attendance_status, occurrence_date) VALUES
                                                                                           ('att-act6-apr-m1',    'act-6', 'C3-M1', 'ATTENDED', '2026-04-03'),
                                                                                           ('att-act6-apr-m2',    'act-6', 'C3-M2', 'ATTENDED', '2026-04-03'),
                                                                                           ('att-act6-apr-m3',    'act-6', 'C3-M3', 'MISSING',  '2026-04-03'),
                                                                                           ('att-act6-apr-m4',    'act-6', 'C3-M4', 'MISSING',  '2026-04-03'),
                                                                                           ('att-act6-apr-m5',    'act-6', 'C3-M5', 'ATTENDED', '2026-04-03'),
                                                                                           ('att-act6-apr-m6',    'act-6', 'C3-M6', 'ATTENDED', '2026-04-03'),
                                                                                           ('att-act6-apr-m7',    'act-6', 'C3-M7', 'MISSING',  '2026-04-03'),
                                                                                           ('att-act6-apr-m8',    'act-6', 'C3-M8', 'ATTENDED', '2026-04-03'),
-- C1-M1 vient de col-1 → ATTENDED mais ne compte pas dans assiduité col-3
                                                                                           ('att-act6-apr-c1m1',  'act-6', 'C1-M1', 'ATTENDED', '2026-04-03');

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO agricultural_federation_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO agricultural_federation_db_manager;