-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 02, 2019 at 07:11 PM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `planeapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `city`
--

CREATE TABLE `city` (
  `id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `country` varchar(250) NOT NULL,
  `lat` float(4,2) NOT NULL,
  `lon` float(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `city`
--

INSERT INTO `city` (`id`, `name`, `country`, `lat`, `lon`) VALUES
(1, 'San Francisco', 'USA', 37.76, -122.43),
(2, 'Moscow', 'Russia', 55.75, 37.61),
(3, 'Podgorica', 'Montenegro', 42.44, 19.26),
(4, 'Tivat', 'Montenegro', 42.43, 18.69),
(5, 'Cape Town', 'South Africa', -33.94, 18.55),
(6, 'Melbourne', 'Australia', -37.85, 145.06),
(7, 'Rio de Jeneiro', 'Brasil', -22.90, -43.17),
(8, 'Vardo', 'Norway', 70.37, 31.10),
(12, 'Tokyo', 'Japan', 35.66, 139.60),
(13, 'Beijing', 'China', 39.93, 116.11),
(14, 'Mumbai', 'India', 19.08, 72.74),
(15, 'Nairobi', 'Kenya', -1.30, 36.77),
(16, 'Lima', 'Peru', -12.05, -77.06),
(313, 'Niksic', 'Montenegro', 42.77, 18.91),
(314, 'Budapest', 'Hungary', 47.49, 19.06),
(315, 'Prague', 'Chezchia', 50.07, 14.43);

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `country` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`id`, `name`, `country`) VALUES
(3, 'Aeroflot', 'Russia'),
(6, 'Australian Airlines', 'Austria'),
(234, 'Berlin Air', 'Germany'),
(8, 'Jet Airways', 'India'),
(5, 'Mesa Airlines', 'USA'),
(1, 'Montenegro Airlines', 'Montenegro'),
(261, 'Rossiya', 'Russia'),
(236, 'Ryan Air', 'Germany'),
(7, 'Swiss', 'Switzerland'),
(2, 'United Airlines', 'USA'),
(4, 'United Continental', 'USA'),
(235, 'Wizz Air', 'Germany');

-- --------------------------------------------------------

--
-- Table structure for table `flight`
--

CREATE TABLE `flight` (
  `id` int(11) NOT NULL,
  `origin` int(11) NOT NULL,
  `destination` int(11) NOT NULL,
  `company` int(11) NOT NULL,
  `plane` int(11) NOT NULL,
  `takeoff` datetime NOT NULL,
  `duration` int(3) NOT NULL,
  `price` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `flight`
--

INSERT INTO `flight` (`id`, `origin`, `destination`, `company`, `plane`, `takeoff`, `duration`, `price`) VALUES
(121, 315, 3, 3, 9, '2019-01-12 17:05:00', 0, 100),
(122, 314, 4, 234, 4, '2019-01-17 18:30:00', 0, 120),
(123, 3, 315, 235, 2, '2019-01-22 15:20:00', 0, 40),
(124, 4, 313, 236, 170, '2019-01-27 11:30:00', 0, 80),
(125, 313, 2, 3, 171, '2019-02-01 01:32:00', 0, 200),
(131, 2, 314, 234, 177, '2019-02-06 04:56:00', 0, 120),
(150, 2, 3, 236, 170, '2019-01-17 18:30:00', 0, 80),
(151, 313, 4, 3, 171, '2019-01-22 15:20:00', 0, 200),
(152, 314, 315, 234, 177, '2019-01-27 11:30:00', 0, 120);

-- --------------------------------------------------------

--
-- Table structure for table `plane`
--

CREATE TABLE `plane` (
  `id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `seats` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `plane`
--

INSERT INTO `plane` (`id`, `name`, `seats`) VALUES
(1, 'Boeing 737', 148),
(2, 'Airbus A350', 112),
(3, 'Airbus A319', 84),
(4, 'Airbus A340-500', 435),
(5, 'Airbus A350-900', 345),
(6, 'Boeing 777-300', 532),
(7, 'Antonov An-124', 454),
(8, 'Boeing 747-400', 234),
(9, 'Boeing 747-8i', 532),
(170, 'Boeing 747-9i', 153),
(171, 'Airbus A540-200', 253),
(177, 'Airbus A50', 315),
(194, 'Airbus Z32', 568);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`country`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`country`);

--
-- Indexes for table `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `origin_2` (`origin`,`destination`,`company`,`plane`,`takeoff`,`duration`),
  ADD KEY `company` (`company`),
  ADD KEY `destination` (`destination`),
  ADD KEY `origin` (`origin`),
  ADD KEY `plane` (`plane`);

--
-- Indexes for table `plane`
--
ALTER TABLE `plane`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `city`
--
ALTER TABLE `city`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=316;

--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=262;

--
-- AUTO_INCREMENT for table `flight`
--
ALTER TABLE `flight`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=153;

--
-- AUTO_INCREMENT for table `plane`
--
ALTER TABLE `plane`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=195;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `flight`
--
ALTER TABLE `flight`
  ADD CONSTRAINT `flight_ibfk_1` FOREIGN KEY (`company`) REFERENCES `company` (`id`),
  ADD CONSTRAINT `flight_ibfk_2` FOREIGN KEY (`destination`) REFERENCES `city` (`id`),
  ADD CONSTRAINT `flight_ibfk_3` FOREIGN KEY (`origin`) REFERENCES `city` (`id`),
  ADD CONSTRAINT `flight_ibfk_4` FOREIGN KEY (`plane`) REFERENCES `plane` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
