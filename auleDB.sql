-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 19, 2023 at 05:04 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `auleDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `Amministratore`
--

CREATE TABLE `Amministratore` (
  `ID` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Amministratore`
--

INSERT INTO `Amministratore` (`ID`, `username`, `password`, `version`) VALUES
(1, 'a', 'a', 1);

-- --------------------------------------------------------

--
-- Table structure for table `AttrezzaturaDisponibile`
--

CREATE TABLE `AttrezzaturaDisponibile` (
  `ID` int(11) NOT NULL,
  `NomeAttrezzatura` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `AttrezzaturaDisponibile`
--

INSERT INTO `AttrezzaturaDisponibile` (`ID`, `NomeAttrezzatura`) VALUES
(1, 'Proiettore'),
(2, 'Schermo Motorizzato'),
(3, 'Schermo Manuale'),
(4, 'Impianto Audio'),
(5, 'PC Fisso'),
(6, 'Microfono a Filo'),
(7, 'Microfono senza Filo'),
(8, 'Lavagna Luminosa'),
(9, 'WiFi');

-- --------------------------------------------------------

--
-- Table structure for table `Aula`
--

CREATE TABLE `Aula` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Luogo` varchar(50) NOT NULL,
  `Edificio` varchar(50) NOT NULL,
  `Piano` varchar(50) NOT NULL,
  `Capienza` int(11) NOT NULL,
  `PreseElettriche` int(11) DEFAULT NULL,
  `PreseRete` int(11) DEFAULT NULL,
  `Note` text DEFAULT NULL,
  `IDAttrezzatura` int(11) DEFAULT NULL,
  `IDDipartimento` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Aula`
--

INSERT INTO `Aula` (`ID`, `Nome`, `Luogo`, `Edificio`, `Piano`, `Capienza`, `PreseElettriche`, `PreseRete`, `Note`, `IDAttrezzatura`, `IDDipartimento`) VALUES
(1, 'Aula 1', 'Luogo C', 'Edificio B', 'Terzo', 50, 10, 5, 'Questa è un\'aula di prova 1.', 3, 2),
(2, 'Aula 2', 'Luogo A', 'Edificio B', 'Primo', 30, 8, 4, 'Questa è un\'aula di prova 2.', 4, 2),
(3, 'Aula 3', 'Luogo C', 'Edificio B', 'Secondo', 40, 12, 6, 'Questa è un\'aula di prova 3.', 2, 3),
(4, 'Aula 4', 'Luogo B', 'Edificio A', 'Secondo', 50, 10, 5, 'Questa è un\'aula di prova 4.', 6, 1),
(5, 'Aula 5', 'Luogo B', 'Edificio A', 'Terzo', 30, 8, 4, 'Questa è un\'aula di prova 5.', 5, 1),
(6, 'Aula 6', 'Luogo C', 'Edificio C', 'Primo', 40, 12, 6, 'Questa è un\'aula di prova 6.', 5, 2),
(7, 'Aula 7', 'Luogo B', 'Edificio B', 'Terzo', 60, 15, 8, 'Questa è un\'aula di prova 7.', 8, 1),
(8, 'Aula 8', 'Luogo B', 'Edificio C', 'Terzo', 45, 9, 7, 'Questa è un\'aula di prova 8.', 9, 3),
(9, 'Aula 9', 'Luogo B', 'Edificio C', 'Terzo', 70, 20, 10, 'Questa è un\'aula di prova 9.', 8, 1),
(10, 'Aula 10', 'Luogo A', 'Edificio C', 'Terzo', 35, 7, 6, 'Questa è un\'aula di prova 10.', 6, 1),
(11, 'Aula 11', 'Luogo C', 'Edificio A', 'Secondo', 55, 12, 9, 'Questa è un\'aula di prova 11.', 2, 1),
(12, 'Aula 12', 'Luogo C', 'Edificio B', 'Terzo', 42, 11, 6, 'Questa è un\'aula di prova 12.', 2, 3),
(13, 'Aula 13', 'Luogo A', 'Edificio B', 'Terzo', 38, 10, 5, 'Questa è un\'aula di prova 13.', 2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `Corso`
--

CREATE TABLE `Corso` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `IDResponsabile` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Corso`
--

INSERT INTO `Corso` (`ID`, `Nome`, `IDResponsabile`) VALUES
(1, 'Corso 1', 1),
(2, 'Corso 2', 2),
(3, 'Corso 3', 3),
(4, 'Corso 4', 4),
(5, 'Corso 5', 5),
(6, 'Corso 6', 6),
(7, 'Corso 7', 7),
(8, 'Corso 8', 8),
(9, 'Corso 9', 9),
(10, 'Corso 10', 10);

-- --------------------------------------------------------

--
-- Table structure for table `Dipartimento`
--

CREATE TABLE `Dipartimento` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Descrizione` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Dipartimento`
--

INSERT INTO `Dipartimento` (`ID`, `Nome`, `Descrizione`) VALUES
(1, 'Dipartimento di Matematica', 'Dipartimento che si occupa della matematica e delle sue applicazioni.'),
(2, 'Dipartimento di Fisica', 'Dipartimento che si occupa della fisica e delle sue applicazioni.'),
(3, 'Dipartimento di Biologia', 'Dipartimento che si occupa della biologia e delle scienze della vita.');

-- --------------------------------------------------------

--
-- Table structure for table `Evento`
--

CREATE TABLE `Evento` (
  `ID` int(11) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `oraInizio` time DEFAULT NULL,
  `oraFine` time DEFAULT NULL,
  `descrizione` text DEFAULT NULL,
  `IDAula` int(11) DEFAULT NULL,
  `Ricorrenza` varchar(100) NOT NULL,
  `DataInizio` date DEFAULT NULL,
  `DataFine` date DEFAULT NULL,
  `IDResponsabile` int(11) DEFAULT NULL,
  `IDCorso` int(11) DEFAULT NULL,
  `tipologiaEvento` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Evento`
--

INSERT INTO `Evento` (`ID`, `nome`, `oraInizio`, `oraFine`, `descrizione`, `IDAula`, `Ricorrenza`, `DataInizio`, `DataFine`, `IDResponsabile`, `IDCorso`, `tipologiaEvento`) VALUES
(1, 'Evento 1', '08:00:00', '10:00:00', 'Descrizione dell\'evento 1', 8, 'NON RICORRENTE', '2023-11-27', '2023-12-26', 5, NULL, NULL),
(2, 'Evento 2', '10:30:00', '12:30:00', 'Descrizione dell\'evento 2', 13, 'RICORRENTE', '2023-11-16', '2023-11-21', 9, NULL, NULL),
(3, 'Evento 3', '14:00:00', '16:00:00', 'Descrizione dell\'evento 3', 4, 'NON RICORRENTE', '2023-11-13', '2023-12-14', 9, NULL, NULL),
(4, 'Evento 4', '09:00:00', '11:00:00', 'Descrizione dell\'evento 4', 7, 'NON RICORRENTE', '2023-11-28', '2023-12-04', 7, NULL, NULL),
(5, 'Evento 5', '13:30:00', '15:30:00', 'Descrizione dell\'evento 5', 10, 'NON RICORRENTE', '2023-11-21', '2024-01-01', 8, NULL, NULL),
(6, 'Evento 6', '12:00:00', '14:00:00', 'Descrizione dell\'evento 6', 7, 'NON RICORRENTE', '2023-11-04', '2023-12-10', 9, NULL, NULL),
(7, 'Evento 7', '10:00:00', '12:00:00', 'Descrizione dell\'evento 7', 3, 'RICORRENTE', '2023-11-24', '2023-11-29', 2, NULL, NULL),
(8, 'Evento 8', '14:30:00', '16:30:00', 'Descrizione dell\'evento 8', 7, 'NON RICORRENTE', '2023-11-06', '2023-12-26', 1, NULL, NULL),
(9, 'Evento 9', '11:30:00', '13:30:00', 'Descrizione dell\'evento 9', 8, 'NON RICORRENTE', '2023-11-19', '2023-12-12', 7, NULL, NULL),
(10, 'Evento 10', '08:30:00', '10:30:00', 'Descrizione dell\'evento 10', 2, 'RICORRENTE', '2023-11-05', '2023-11-08', 4, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Responsabile`
--

CREATE TABLE `Responsabile` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Cognome` varchar(50) NOT NULL,
  `Email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Responsabile`
--

INSERT INTO `Responsabile` (`ID`, `Nome`, `Cognome`, `Email`) VALUES
(1, 'John', 'Doe', 'john.doe@gmail.com'),
(2, 'Jane', 'Smith', 'jane.smith@gmail.com'),
(3, 'Robert', 'Johnson', 'robert.johnson@outlook.com'),
(4, 'Emily', 'Williams', 'emily.williams@outlook.com'),
(5, 'Michael', 'Brown', 'michael.brown@protonmail.com'),
(6, 'Jessica', 'Taylor', 'jessica.taylor@protonmail.com'),
(7, 'David', 'Martinez', 'david.martinez@gmail.com'),
(8, 'Sarah', 'Anderson', 'sarah.anderson@outlook.com'),
(9, 'William', 'Wilson', 'william.wilson@protonmail.com'),
(10, 'Olivia', 'Davis', 'olivia.davis@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Amministratore`
--
ALTER TABLE `Amministratore`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `AttrezzaturaDisponibile`
--
ALTER TABLE `AttrezzaturaDisponibile`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Aula`
--
ALTER TABLE `Aula`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Aule_Attrezzature` (`IDAttrezzatura`),
  ADD KEY `FK_Aule_Dipartimento` (`IDDipartimento`);

--
-- Indexes for table `Corso`
--
ALTER TABLE `Corso`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Corso_Responsabile` (`IDResponsabile`);

--
-- Indexes for table `Dipartimento`
--
ALTER TABLE `Dipartimento`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Evento`
--
ALTER TABLE `Evento`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Eventi_Aule` (`IDAula`),
  ADD KEY `FK_Eventi_Responsabile` (`IDResponsabile`),
  ADD KEY `FK_Evento_Corso` (`IDCorso`);

--
-- Indexes for table `Responsabile`
--
ALTER TABLE `Responsabile`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Amministratore`
--
ALTER TABLE `Amministratore`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `AttrezzaturaDisponibile`
--
ALTER TABLE `AttrezzaturaDisponibile`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `Aula`
--
ALTER TABLE `Aula`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `Corso`
--
ALTER TABLE `Corso`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Dipartimento`
--
ALTER TABLE `Dipartimento`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `Evento`
--
ALTER TABLE `Evento`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Responsabile`
--
ALTER TABLE `Responsabile`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Aula`
--
ALTER TABLE `Aula`
  ADD CONSTRAINT `FK_Aule_Attrezzature` FOREIGN KEY (`IDAttrezzatura`) REFERENCES `AttrezzaturaDisponibile` (`ID`),
  ADD CONSTRAINT `FK_Aule_Dipartimento` FOREIGN KEY (`IDDipartimento`) REFERENCES `Dipartimento` (`ID`);

--
-- Constraints for table `Corso`
--
ALTER TABLE `Corso`
  ADD CONSTRAINT `FK_Corso_Responsabile` FOREIGN KEY (`IDResponsabile`) REFERENCES `Responsabile` (`ID`);

--
-- Constraints for table `Evento`
--
ALTER TABLE `Evento`
  ADD CONSTRAINT `FK_Eventi_Aule` FOREIGN KEY (`IDAula`) REFERENCES `Aula` (`ID`),
  ADD CONSTRAINT `FK_Eventi_Responsabile` FOREIGN KEY (`IDResponsabile`) REFERENCES `Responsabile` (`ID`),
  ADD CONSTRAINT `FK_Evento_Corso` FOREIGN KEY (`IDCorso`) REFERENCES `Corso` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
