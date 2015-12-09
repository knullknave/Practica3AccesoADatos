SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `medicdb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `medicdb`;

CREATE TABLE `analysis` (
  `id` int(10) UNSIGNED NOT NULL,
  `analysisDate` date NOT NULL,
  `analysisType` varchar(50) NOT NULL,
  `report` varchar(255) NOT NULL,
  `reportDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `disease` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(30) NOT NULL,
  `descr` varchar(255) NOT NULL,
  `evolution` varchar(255) DEFAULT NULL,
  `treatment` varchar(255) NOT NULL,
  `prevention` varchar(255) DEFAULT NULL,
  `diseaseType` varchar(100) NOT NULL,
  `pathogenesis` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `episodedisease` (
  `idEpisode` int(10) UNSIGNED DEFAULT NULL,
  `idDisease` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `episodes` (
  `id` int(10) UNSIGNED NOT NULL,
  `descript` varchar(255) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `evolution` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `medic` (
  `collegiateNumber` int(10) UNSIGNED NOT NULL,
  `userName` varchar(20) NOT NULL,
  `userPasword` varchar(16) NOT NULL,
  `name` varchar(20) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `adress` varchar(100) NOT NULL,
  `medicalCentre` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `medicalSpeciality` varchar(100) NOT NULL,
  `telephone` varchar(100) NOT NULL,
  `birthDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `medicament` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(30) NOT NULL,
  `composition` varchar(100) NOT NULL,
  `formatt` varchar(50) NOT NULL,
  `laboratory` varchar(50) NOT NULL,
  `prize` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `patient` (
  `cias` int(10) UNSIGNED NOT NULL,
  `patientUser` varchar(20) DEFAULT NULL,
  `patientPassword` varchar(16) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `sex` char(1) DEFAULT NULL,
  `adress` varchar(100) NOT NULL,
  `birthDate` date NOT NULL,
  `telephone` varchar(100) DEFAULT NULL,
  `bloodType` char(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `pharmacotherapy` (
  `id` int(10) UNSIGNED NOT NULL,
  `descript` varchar(255) NOT NULL,
  `dosage` varchar(255) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `initialWeight` float NOT NULL,
  `finalWeight` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `pharmacotherapymedicament` (
  `idPharmacotherapy` int(10) UNSIGNED DEFAULT NULL,
  `idMedicament` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `radiography` (
  `id` int(10) UNSIGNED NOT NULL,
  `reportDate` date NOT NULL,
  `receptionDate` date NOT NULL,
  `radiographyDate` date NOT NULL,
  `study` varchar(255) NOT NULL,
  `report` varchar(255) NOT NULL,
  `controlDone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `visit` (
  `idVisit` int(10) UNSIGNED NOT NULL,
  `receptionDate` date DEFAULT NULL,
  `visitDate` date DEFAULT NULL,
  `medicalCentre` varchar(100) DEFAULT NULL,
  `idAnalysis` int(10) UNSIGNED DEFAULT NULL,
  `idEpisode` int(10) UNSIGNED DEFAULT NULL,
  `idMedic` int(10) UNSIGNED DEFAULT NULL,
  `idPatient` int(10) UNSIGNED DEFAULT NULL,
  `idPharmacotherapy` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `analysis`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `disease`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `episodedisease`
  ADD KEY `idEpisode` (`idEpisode`),
  ADD KEY `idDisease` (`idDisease`);

ALTER TABLE `episodes`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `medic`
  ADD PRIMARY KEY (`collegiateNumber`);

ALTER TABLE `medicament`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `patient`
  ADD PRIMARY KEY (`cias`);

ALTER TABLE `pharmacotherapy`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `pharmacotherapymedicament`
  ADD KEY `idPharmacotherapy` (`idPharmacotherapy`),
  ADD KEY `idMedicament` (`idMedicament`);

ALTER TABLE `radiography`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `visit`
  ADD PRIMARY KEY (`idVisit`),
  ADD KEY `idAnalysis` (`idAnalysis`),
  ADD KEY `idEpisode` (`idEpisode`),
  ADD KEY `idMedic` (`idMedic`),
  ADD KEY `idPatient` (`idPatient`),
  ADD KEY `idPharmacotherapy` (`idPharmacotherapy`);


ALTER TABLE `analysis`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `disease`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `episodes`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
ALTER TABLE `medic`
  MODIFY `collegiateNumber` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
ALTER TABLE `medicament`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `patient`
  MODIFY `cias` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
ALTER TABLE `pharmacotherapy`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `radiography`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `visit`
  MODIFY `idVisit` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

ALTER TABLE `episodedisease`
  ADD CONSTRAINT `episodedisease_ibfk_1` FOREIGN KEY (`idEpisode`) REFERENCES `episodes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `episodedisease_ibfk_2` FOREIGN KEY (`idDisease`) REFERENCES `disease` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `pharmacotherapymedicament`
  ADD CONSTRAINT `pharmacotherapymedicament_ibfk_1` FOREIGN KEY (`idPharmacotherapy`) REFERENCES `pharmacotherapy` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pharmacotherapymedicament_ibfk_2` FOREIGN KEY (`idMedicament`) REFERENCES `medicament` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
