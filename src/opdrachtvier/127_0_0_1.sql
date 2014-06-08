-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Machine: 127.0.0.1
-- Genereertijd: 08 jun 2014 om 19:30
-- Serverversie: 5.5.34
-- PHP-versie: 5.4.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `opdrachtviersql`
--
CREATE DATABASE IF NOT EXISTS `opdrachtviersql` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `opdrachtviersql`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `klassen`
--

CREATE TABLE IF NOT EXISTS `klassen` (
  `klas_id` int(11) NOT NULL AUTO_INCREMENT,
  `klas_naam` text NOT NULL,
  PRIMARY KEY (`klas_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Gegevens worden uitgevoerd voor tabel `klassen`
--

INSERT INTO `klassen` (`klas_id`, `klas_naam`) VALUES
(1, 'INF2D');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `studenten`
--

CREATE TABLE IF NOT EXISTS `studenten` (
  `stu_id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_naam` text NOT NULL,
  `stu_ww` text NOT NULL,
  `stu_klas_id` int(11) NOT NULL,
  `stu_ingeschreven` enum('Ja','Nee') NOT NULL,
  PRIMARY KEY (`stu_id`),
  KEY `st_klas_id` (`stu_klas_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Gegevens worden uitgevoerd voor tabel `studenten`
--

INSERT INTO `studenten` (`stu_id`, `stu_naam`, `stu_ww`, `stu_klas_id`, `stu_ingeschreven`) VALUES
(1, 'nabil', 'welkom', 1, 'Ja'),
(2, 'testnaam', 'welkom', 1, 'Nee');

--
-- Beperkingen voor gedumpte tabellen
--

--
-- Beperkingen voor tabel `studenten`
--
ALTER TABLE `studenten`
  ADD CONSTRAINT `studenten_ibfk_1` FOREIGN KEY (`stu_klas_id`) REFERENCES `klassen` (`klas_id`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
