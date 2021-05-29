-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 29, 2021 at 12:43 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dss`
--

-- --------------------------------------------------------

--
-- Table structure for table `alternatif`
--

CREATE TABLE `alternatif` (
  `namaMobil` varchar(255) NOT NULL,
  `Brand` varchar(255) NOT NULL,
  `Type` varchar(255) NOT NULL,
  `Country` varchar(255) NOT NULL,
  `Transmission` varchar(255) NOT NULL,
  `power` int(255) NOT NULL,
  `penumpang` int(11) NOT NULL,
  `bensin` int(255) NOT NULL,
  `harga` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `alternatif`
--

INSERT INTO `alternatif` (`namaMobil`, `Brand`, `Type`, `Country`, `Transmission`, `power`, `penumpang`, `bensin`, `harga`) VALUES
('Cadillac Escalade', '0', '0', '0', '0', 300, 7, 23, 2000),
('Hoonda Jazz', '0', '0', '0', '0', 100, 4, 25, 280),
('Koenigsegg Agera', '0', '0', '0', '0', 1000, 2, 30, 10000),
('Lamborghini Gallardo', '0', '0', '0', '0', 650, 2, 24, 3750),
('Mercedes G Wagen', '0', '0', '0', '0', 350, 7, 24, 1500),
('Pajero', '0', '0', '0', '0', 150, 7, 24, 500),
('Tesla P100D', '0', '0', '0', '0', 200, 4, 100, 1000),
('Toyota Supra', '0', '0', '0', '0', 276, 2, 24, 1250);

-- --------------------------------------------------------

--
-- Table structure for table `alt_bobot`
--

CREATE TABLE `alt_bobot` (
  `power` double NOT NULL DEFAULT 0,
  `penumpang` double NOT NULL DEFAULT 0,
  `bensin` double NOT NULL DEFAULT 0,
  `harga` double NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `alt_bobot`
--

INSERT INTO `alt_bobot` (`power`, `penumpang`, `bensin`, `harga`) VALUES
(0.3, 0.4, 0.2, 0.1);

-- --------------------------------------------------------

--
-- Table structure for table `hasil`
--

CREATE TABLE `hasil` (
  `namaMobil` varchar(255) NOT NULL,
  `nilai` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hasil`
--

INSERT INTO `hasil` (`namaMobil`, `nilai`) VALUES
('Cadillac Escalade', 0.55),
('Hoonda Jazz', 0.41),
('Koenigsegg Agera', 0.48),
('Lamborghini Gallardo', 0.36),
('Mercedes G Wagen', 0.57),
('Pajero', 0.55),
('Tesla P100D', 0.52),
('Toyota Supra', 0.27);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alternatif`
--
ALTER TABLE `alternatif`
  ADD PRIMARY KEY (`namaMobil`);

--
-- Indexes for table `hasil`
--
ALTER TABLE `hasil`
  ADD UNIQUE KEY `namaMobil` (`namaMobil`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `hasil`
--
ALTER TABLE `hasil`
  ADD CONSTRAINT `hasil_ibfk_1` FOREIGN KEY (`namaMobil`) REFERENCES `alternatif` (`namaMobil`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
