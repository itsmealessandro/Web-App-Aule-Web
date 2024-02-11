-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 09, 2024 alle 16:19
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `auledb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `amministratore`
--

CREATE TABLE `amministratore` (
  `ID` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `amministratore`
--

INSERT INTO `amministratore` (`ID`, `username`, `password`, `version`) VALUES
(1, 'u', '0518c94d194f5b6ddf06b14fefb1cfb9adfbe3c1dbc2108626bec7252df3e32336b10534a58fc800ed163c36064419b9', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `attrezzaturadisponibile`
--

CREATE TABLE `attrezzaturadisponibile` (
  `ID` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `attrezzaturadisponibile`
--

INSERT INTO `attrezzaturadisponibile` (`ID`, `nome`, `version`) VALUES
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
-- Struttura della tabella `aula`
--

CREATE TABLE `aula` (
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
  `version` int(11) NOT NULL,
  `IDResponsabile` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `aula`
--

INSERT INTO `aula` (`ID`, `nome`, `luogo`, `edificio`, `piano`, `capienza`, `preseElettriche`, `preseRete`, `note`, `IDAttrezzatura`, `IDDipartimento`, `version`, `IDResponsabile`) VALUES
(1, 'Aula 1', 'Luogo C', 'Edificio B', 'Terzo', 50, 10, 5, 'Questa è un\'aula di prova 1.', 3, 2, 0, 1),
(2, 'Aula 2', 'Luogo A', 'Edificio B', 'Primo', 30, 8, 4, 'Questa Ã¨ un\'aula di prova 2.', 4, 2, 1, 2),
(3, 'Aula 3', 'Luogo C', 'Edificio B', 'Secondo', 40, 12, 7, 'test3333', 2, 2, 2, 3),
(4, 'Aula 4', 'Luogo B', 'Edificio A', 'Secondo', 50, 10, 5, '4', 6, 1, 1, 4),
(5, 'Aula 5', 'Luogo B', 'Edificio A', 'Terzo', 30, 8, 4, 'Questa è un\'aula di prova 5.', 5, 1, 0, 5),
(6, 'Aula 6', 'Luogo C', 'Edificio C', 'Primo', 40, 12, 6, 'Questa Ã¨ un\'aula di prova 6.', 5, 2, 1, 6),
(7, 'Aula 7', 'Luogo B', 'Edificio B', 'Terzo', 60, 15, 8, 'Questa è un\'aula di prova 7.', 8, 1, 0, 7),
(8, 'Aula 8', 'Luogo B', 'Edificio C', 'Terzo', 45, 9, 7, 'Questa è un\'aula di prova 8.', 9, 3, 0, 8),
(9, 'Aula 9', 'Luogo B', 'Edificio C', 'Terzo', 70, 20, 10, 'Questa è un\'aula di prova 9.', 8, 1, 0, 9),
(10, 'Aula 10', 'Luogo A', 'Edificio C', 'Terzo', 35, 7, 6, 'Questa è un\'aula di prova 10.', 6, 1, 0, 10),
(11, 'Aula 11', 'Luogo C', 'Edificio A', 'Secondo', 55, 12, 9, 'Questa è un\'aula di prova 11.', 2, 1, 0, 1),
(12, 'Aula 12', 'Luogo C', 'Edificio B', 'Terzo', 42, 11, 6, 'Questa è un\'aula di prova 12.', 2, 3, 0, 2),
(13, 'Aula 13', 'Luogo A', 'Edificio B', 'Terzo', 38, 10, 5, 'Questa è un\'aula di prova 13.', 2, 3, 0, 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `corso`
--

CREATE TABLE `corso` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `IDResponsabile` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `corso`
--

INSERT INTO `corso` (`ID`, `Nome`, `IDResponsabile`, `version`) VALUES
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
-- Struttura della tabella `dipartimento`
--

CREATE TABLE `dipartimento` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Descrizione` text DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `dipartimento`
--

INSERT INTO `dipartimento` (`ID`, `Nome`, `Descrizione`, `version`) VALUES
(1, ' Dipartimento di Matematica', 'Descrizione ', 7),
(2, 'Dipartimento di Fisica', 'Dipartimento che si occupa della fisica e delle sue applicazioni.', 0),
(3, 'Dipartimento di Biologia', 'Dipartimento che si occupa della biologia e delle scienze della vita.', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `evento`
--

CREATE TABLE `evento` (
  `ID` int(11) NOT NULL,
  `IDMaster` int(11) DEFAULT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `oraInizio` time DEFAULT NULL,
  `oraFine` time DEFAULT NULL,
  `descrizione` text DEFAULT NULL,
  `ricorrenza` varchar(100) NOT NULL,
  `Data` date DEFAULT NULL,
  `dataFineRicorrenza` date DEFAULT NULL,
  `tipologiaEvento` varchar(100) DEFAULT NULL,
  `IDResponsabile` int(11) DEFAULT NULL,
  `IDCorso` int(11) DEFAULT NULL,
  `IDAula` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `evento`
--

INSERT INTO `evento` (`ID`, `nome`, `oraInizio`, `oraFine`, `descrizione`, `IDAula`, `ricorrenza`, `IDResponsabile`, `IDCorso`, `tipologiaEvento`, `version`, `IDMaster`, `Data`, `dataFineRicorrenza`) VALUES
(1, 'Evento 1', '08:00:00', '10:00:00', 'Descrizione dell\'evento 1', 8, 'NESSUNA', 5, 1, 'LEZIONE', 0, NULL, '2024-02-03', NULL),
(2, 'Evento 2', '10:30:00', '12:30:00', 'Descrizione dell\'evento 2', 13, 'NESSUNA', 9, 1, 'LEZIONE', 0, NULL, '2024-01-26', NULL),
(3, 'Evento 3', '14:00:00', '16:00:00', 'Descrizione dell\'evento 3', 4, 'GIORNALIERA', 9, 2, 'LEZIONE', 0, 1, '2024-02-02', '2024-05-08'),
(4, 'Evento 4', '09:00:00', '11:00:00', 'Descrizione dell\'evento 4', 7, 'NESSUNA', 7, 2, 'LEZIONE', 0, NULL, '2024-02-01', NULL),
(5, 'Evento 5', '13:30:00', '15:30:00', 'Descrizione dell\'evento 5', 10, 'MENSILE', 8, 3, 'SEMINARIO', 0, 2, '2024-02-04', '2024-05-08'),
(6, 'Evento 6', '12:00:00', '14:00:00', 'Descrizione dell\'evento 6', 7, 'SETTIMANALE', 9, 3, 'SEMINARIO', 0, 3, '2024-02-23', '2024-05-08'),
(7, 'Evento 7', '10:00:00', '12:00:00', 'Descrizione dell\'evento 7', 3, 'NESSUNA', 2, 4, 'SEMINARIO', 0, NULL, '2024-02-20', NULL),
(9, 'Evento 9', '11:30:00', '13:30:00', 'Descrizione dell\'evento 9', 8, 'NESSUNA', 7, 6, 'SEMINARIO', 0, NULL, '2024-02-01', NULL),
(10, 'Evento 10', '08:30:00', '10:30:00', 'Descrizione dell\'evento 10', 2, 'NESSUNA', 4, 7, 'SEMINARIO', 0, NULL, '2024-01-28', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `responsabile`
--

CREATE TABLE `responsabile` (
  `ID` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `responsabile`
--

INSERT INTO `responsabile` (`ID`, `nome`, `cognome`, `email`, `version`) VALUES
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
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `amministratore`
--
ALTER TABLE `amministratore`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `attrezzaturadisponibile`
--
ALTER TABLE `attrezzaturadisponibile`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `aula`
--
ALTER TABLE `aula`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Aule_Attrezzature` (`IDAttrezzatura`),
  ADD KEY `FK_Aule_Dipartimento` (`IDDipartimento`),
  ADD KEY `FK_Aule_Responsabile` (`IDResponsabile`);

--
-- Indici per le tabelle `corso`
--
ALTER TABLE `corso`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Corso_Responsabile` (`IDResponsabile`);

--
-- Indici per le tabelle `dipartimento`
--
ALTER TABLE `dipartimento`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Eventi_Aule` (`IDAula`),
  ADD KEY `FK_Eventi_Responsabile` (`IDResponsabile`),
  ADD KEY `FK_Evento_Corso` (`IDCorso`);

--
-- Indici per le tabelle `responsabile`
--
ALTER TABLE `responsabile`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `amministratore`
--
ALTER TABLE `amministratore`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `attrezzaturadisponibile`
--
ALTER TABLE `attrezzaturadisponibile`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `aula`
--
ALTER TABLE `aula`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT per la tabella `corso`
--
ALTER TABLE `corso`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT per la tabella `dipartimento`
--
ALTER TABLE `dipartimento`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `evento`
--
ALTER TABLE `evento`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT per la tabella `responsabile`
--
ALTER TABLE `responsabile`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `aula`
--
ALTER TABLE `aula`
  ADD CONSTRAINT `FK_Aule_Attrezzature` FOREIGN KEY (`IDAttrezzatura`) REFERENCES `attrezzaturadisponibile` (`ID`),
  ADD CONSTRAINT `FK_Aule_Dipartimento` FOREIGN KEY (`IDDipartimento`) REFERENCES `dipartimento` (`ID`),
  ADD CONSTRAINT `FK_Aule_Responsabile` FOREIGN KEY (`IDResponsabile`) REFERENCES `responsabile` (`ID`);

--
-- Limiti per la tabella `corso`
--
ALTER TABLE `corso`
  ADD CONSTRAINT `FK_Corso_Responsabile` FOREIGN KEY (`IDResponsabile`) REFERENCES `responsabile` (`ID`);

--
-- Limiti per la tabella `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `FK_Eventi_Aule` FOREIGN KEY (`IDAula`) REFERENCES `aula` (`ID`),
  ADD CONSTRAINT `FK_Eventi_Responsabile` FOREIGN KEY (`IDResponsabile`) REFERENCES `responsabile` (`ID`),
  ADD CONSTRAINT `FK_Evento_Corso` FOREIGN KEY (`IDCorso`) REFERENCES `corso` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
