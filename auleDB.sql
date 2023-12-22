-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 22, 2023 at 04:23 PM
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
  `nome` varchar(100) NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `AttrezzaturaDisponibile`
--

INSERT INTO `AttrezzaturaDisponibile` (`ID`, `nome`, `version`) VALUES
(1, 'Proiettore', 0),
(2, 'Schermo Motorizzato', 0),
(3, 'Schermo Manuale', 0),
(4, 'Impianto Audio', 0),
(5, 'PC Fisso', 0),
(6, 'Microfono a Filo', 0),
(7, 'Microfono senza Filo', 0),
(8, 'Lavagna Luminosa', 0),
(9, 'WiFi', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Aula`
--

CREATE TABLE `Aula` (
  `ID` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `luogo` varchar(50) NOT NULL,
  `edificio` varchar(50) NOT NULL,
  `piano` varchar(50) NOT NULL,
  `capienza` int(11) NOT NULL,
  `preseElettriche` int(11) DEFAULT NULL,
  `preseRete` int(11) DEFAULT NULL,
  `note` text DEFAULT NULL,
  `IDAttrezzatura` int(11) DEFAULT NULL,
  `IDDipartimento` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Aula`
--

INSERT INTO `Aula` (`ID`, `nome`, `luogo`, `edificio`, `piano`, `capienza`, `preseElettriche`, `preseRete`, `note`, `IDAttrezzatura`, `IDDipartimento`, `version`) VALUES
(1, 'Aula 1', 'Luogo C', 'Edificio B', 'Terzo', 50, 10, 5, 'Questa è un\'aula di prova 1.', 3, 2, 0),
(2, 'Aula 2', 'Luogo A', 'Edificio B', 'Primo', 30, 8, 4, 'Questa è un\'aula di prova 2.', 4, 2, 0),
(3, 'Aula 3', 'Luogo C', 'Edificio B', 'Secondo', 40, 12, 6, 'Questa è un\'aula di prova 3.', 2, 3, 0),
(4, 'Aula 4', 'Luogo B', 'Edificio A', 'Secondo', 50, 10, 5, 'Questa è un\'aula di prova 4.', 6, 1, 0),
(5, 'Aula 5', 'Luogo B', 'Edificio A', 'Terzo', 30, 8, 4, 'Questa è un\'aula di prova 5.', 5, 1, 0),
(6, 'Aula 6', 'Luogo C', 'Edificio C', 'Primo', 40, 12, 6, 'Questa è un\'aula di prova 6.', 5, 2, 0),
(7, 'Aula 7', 'Luogo B', 'Edificio B', 'Terzo', 60, 15, 8, 'Questa è un\'aula di prova 7.', 8, 1, 0),
(8, 'Aula 8', 'Luogo B', 'Edificio C', 'Terzo', 45, 9, 7, 'Questa è un\'aula di prova 8.', 9, 3, 0),
(9, 'Aula 9', 'Luogo B', 'Edificio C', 'Terzo', 70, 20, 10, 'Questa è un\'aula di prova 9.', 8, 1, 0),
(10, 'Aula 10', 'Luogo A', 'Edificio C', 'Terzo', 35, 7, 6, 'Questa è un\'aula di prova 10.', 6, 1, 0),
(11, 'Aula 11', 'Luogo C', 'Edificio A', 'Secondo', 55, 12, 9, 'Questa è un\'aula di prova 11.', 2, 1, 0),
(12, 'Aula 12', 'Luogo C', 'Edificio B', 'Terzo', 42, 11, 6, 'Questa è un\'aula di prova 12.', 2, 3, 0),
(13, 'Aula 13', 'Luogo A', 'Edificio B', 'Terzo', 38, 10, 5, 'Questa è un\'aula di prova 13.', 2, 3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Corso`
--

CREATE TABLE `Corso` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `IDResponsabile` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Corso`
--

INSERT INTO `Corso` (`ID`, `Nome`, `IDResponsabile`, `version`) VALUES
(1, 'Corso 1', 1, 0),
(2, 'Corso 2', 2, 0),
(3, 'Corso 3', 3, 0),
(4, 'Corso 4', 4, 0),
(5, 'Corso 5', 5, 0),
(6, 'Corso 6', 6, 0),
(7, 'Corso 7', 7, 0),
(8, 'Corso 8', 8, 0),
(9, 'Corso 9', 9, 0),
(10, 'Corso 10', 10, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Dipartimento`
--

CREATE TABLE `Dipartimento` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Descrizione` text DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Dipartimento`
--

INSERT INTO `Dipartimento` (`ID`, `Nome`, `Descrizione`, `version`) VALUES
(1, 'Dipartimento di Matematica', 'Dipartimento che si occupa della matematica e delle sue applicazioni.', 0),
(2, 'Dipartimento di Fisica', 'Dipartimento che si occupa della fisica e delle sue applicazioni.', 0),
(3, 'Dipartimento di Biologia', 'Dipartimento che si occupa della biologia e delle scienze della vita.', 0);

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
  `ricorrenza` varchar(100) NOT NULL,
  `dataInizio` date DEFAULT NULL,
  `dataFine` date DEFAULT NULL,
  `IDResponsabile` int(11) DEFAULT NULL,
  `IDCorso` int(11) DEFAULT NULL,
  `tipologiaEvento` varchar(100) DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Evento`
--

INSERT INTO `Evento` (`ID`, `nome`, `oraInizio`, `oraFine`, `descrizione`, `IDAula`, `ricorrenza`, `dataInizio`, `dataFine`, `IDResponsabile`, `IDCorso`, `tipologiaEvento`, `version`) VALUES
(1, 'Evento 1', '08:00:00', '10:00:00', 'Descrizione dell\'evento 1', 8, 'NESSUNA', '2023-11-27', '2023-12-26', 5, NULL, 'LEZIONE', 0),
(2, 'Evento 2', '10:30:00', '12:30:00', 'Descrizione dell\'evento 2', 13, 'NESSUNA', '2023-11-16', '2023-11-21', 9, NULL, 'LEZIONE', 0),
(3, 'Evento 3', '14:00:00', '16:00:00', 'Descrizione dell\'evento 3', 4, 'GIORNALIERA', '2023-11-13', '2023-12-14', 9, NULL, 'LEZIONE', 0),
(4, 'Evento 4', '09:00:00', '11:00:00', 'Descrizione dell\'evento 4', 7, 'NESSUNA', '2023-11-28', '2023-12-04', 7, NULL, 'LEZIONE', 0),
(5, 'Evento 5', '13:30:00', '15:30:00', 'Descrizione dell\'evento 5', 10, 'MENSILE', '2023-11-21', '2024-01-01', 8, NULL, 'SEMINARIO', 0),
(6, 'Evento 6', '12:00:00', '14:00:00', 'Descrizione dell\'evento 6', 7, 'SETTIMANALE', '2023-11-04', '2023-12-10', 9, NULL, 'SEMINARIO', 0),
(7, 'Evento 7', '10:00:00', '12:00:00', 'Descrizione dell\'evento 7', 3, 'NESSUNA', '2023-11-24', '2023-11-29', 2, NULL, 'SEMINARIO', 0),
(8, 'Evento 8', '14:30:00', '16:30:00', 'Descrizione dell\'evento 8', 7, 'NESSUNA', '2023-11-06', '2023-12-26', 1, NULL, 'SEMINARIO', 0),
(9, 'Evento 9', '11:30:00', '13:30:00', 'Descrizione dell\'evento 9', 8, 'NESSUNA', '2023-11-19', '2023-12-12', 7, NULL, 'SEMINARIO', 0),
(10, 'Evento 10', '08:30:00', '10:30:00', 'Descrizione dell\'evento 10', 2, 'NESSUNA', '2023-11-05', '2023-11-08', 4, NULL, 'SEMINARIO', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Responsabile`
--

CREATE TABLE `Responsabile` (
  `ID` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Responsabile`
--

INSERT INTO `Responsabile` (`ID`, `nome`, `cognome`, `email`, `version`) VALUES
(1, 'John', 'Doe', 'john.doe@gmail.com', 0),
(2, 'Jane', 'Smith', 'jane.smith@gmail.com', 0),
(3, 'Robert', 'Johnson', 'robert.johnson@outlook.com', 0),
(4, 'Emily', 'Williams', 'emily.williams@outlook.com', 0),
(5, 'Michael', 'Brown', 'michael.brown@protonmail.com', 0),
(6, 'Jessica', 'Taylor', 'jessica.taylor@protonmail.com', 0),
(7, 'David', 'Martinez', 'david.martinez@gmail.com', 0),
(8, 'Sarah', 'Anderson', 'sarah.anderson@outlook.com', 0),
(9, 'William', 'Wilson', 'william.wilson@protonmail.com', 0),
(10, 'Olivia', 'Davis', 'olivia.davis@gmail.com', 0);

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
