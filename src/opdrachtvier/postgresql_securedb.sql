-- Maak de database
CREATE DATABASE opdractviersecsql;

-- Maak de klassen tabel aan
CREATE TABLE klassen
(
  klas_id serial NOT NULL,
  klas_naam text NOT NULL,
  CONSTRAINT k_pk PRIMARY KEY (klas_id)
);

-- Maak een type aan voor ingeschreven
CREATE TYPE ingeschreven AS ENUM('Ja','Nee');

-- Maak de studenten tabel aan
CREATE TABLE studenten
(
  stu_id serial NOT NULL,
  stu_naam text NOT NULL,
  stu_klas_id integer NOT NULL,
  stu_ingeschreven ingeschreven NOT NULL,
  CONSTRAINT stu_pk PRIMARY KEY (stu_id),
  CONSTRAINT stu_fk1 FOREIGN KEY (stu_klas_id)
      REFERENCES klassen (klas_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

-- Voeg 1 record toe aan klassen tabel, en 2 aan studenten tabel
INSERT INTO klassen (klas_naam) VALUES ('INF2D');
INSERT INTO studenten (stu_naam, stu_klas_id, stu_ingeschreven) VALUES
('nabil',1,'Ja'),
('testnaam',1,'Nee');

-- Maak 3 databaseusers aan
CREATE USER guest WITH PASSWORD '1234';
CREATE USER nabil WITH PASSWORD '1234';
CREATE USER testnaam WITH PASSWORD '1234';

-- Maak de view aan voor een student
CREATE VIEW stud AS
SELECT * FROM studenten AS s
WHERE s.stu_naam = user;

-- Maak de view aan voor een guest
CREATE VIEW klasstud AS
SELECT s.stu_id, s.stu_naam, k.klas_naam, s.stu_ingeschreven
FROM studenten AS s
LEFT JOIN klassen AS k
ON s.stu_klas_id = k.klas_id
WHERE s.stu_ingeschreven = 'Ja';

-- Creeer de groepsrol voor student
CREATE ROLE student;
GRANT SELECT ON stud TO student;
GRANT SELECT ON klasstud TO student;
GRANT student TO nabil, testnaam;

-- Creeer de groepsrol voor gast
CREATE ROLE gast;
GRANT SELECT ON klasstud TO gast;
GRANT gast TO guest;