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