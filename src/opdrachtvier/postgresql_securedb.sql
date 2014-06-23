-- Maak de database
CREATE DATABASE opdractviersql;

-- Maak de klassen tabel aan
CREATE TABLE klassen
(
  klas_id       SERIAL  NOT NULL,
  klas_naam     TEXT    NOT NULL,
  CONSTRAINT k_pk PRIMARY KEY (klas_id)
);

-- Maak een type aan voor ingeschreven
CREATE TYPE INGESCHREVEN AS ENUM('Ja','Nee');

-- Maak de studenten tabel aan
CREATE TABLE studenten
(
  stu_id            SERIAL          NOT NULL,
  stu_naam          TEXT            NOT NULL,
  stu_ww            TEXT            NOT NULL,
  stu_klas_id       INTEGER         NOT NULL,
  stu_ingeschreven  INGESCHREVEN    NOT NULL,
  CONSTRAINT stu_pk PRIMARY KEY (stu_id),
  CONSTRAINT stu_fk1 FOREIGN KEY (stu_klas_id)
      REFERENCES klassen (klas_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

-- Voeg 1 record toe aan klassen tabel, en 2 aan studenten tabel
INSERT INTO klassen (klas_naam) VALUES ('INF2D');
INSERT INTO studenten (stu_naam, stu_ww, stu_klas_id, stu_ingeschreven) VALUES
('nabil','1234',1,'Ja'),
('testnaam','1234',1,'Nee');

-- Maak 3 databaseusers aan
CREATE USER guest WITH PASSWORD '1234';
CREATE USER student WITH PASSWORD '1234';

-- Maak de view aan voor een student
CREATE VIEW stud AS
SELECT * FROM studenten AS s;

-- Maak een user view aan
CREATE VIEW usr AS
SELECT s.stu_naam AS un, s.stu_ww AS ww FROM studenten AS s;

-- Maak de view aan voor een guest
CREATE VIEW 	klasstud AS
SELECT 		s.stu_id, s.stu_naam, k.klas_naam, s.stu_ingeschreven
FROM        	studenten AS s
NATURAL JOIN   	klassen AS k
WHERE 		s.stu_ingeschreven = 'Ja';

-- Maak de view aan voor een klas
CREATE OR REPLACE VIEW klas AS 
 SELECT klassen.klas_id,
    klassen.klas_naam
   FROM klassen;

-- Creeer de groepsrol voor student
CREATE ROLE         students;
GRANT SELECT ON     usr         TO students;
GRANT SELECT ON     stud        TO students;
GRANT SELECT ON     klas        TO students;
GRANT               students    TO student;

-- Creeer de groepsrol voor gast
CREATE ROLE         gast;
GRANT SELECT ON     klas        TO gast;
GRANT SELECT ON     klasstud    TO gast;
GRANT               gast        TO guest;