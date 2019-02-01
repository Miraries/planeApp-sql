-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 17, 2018 at 09:45 AM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.11

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
(16, 'Lima', 'Peru', -12.05, -77.06);

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
(1, 'Montenegro Airlines', 'Montenegro'),
(2, 'United Airlines', 'USA'),
(3, 'Aeroflot', 'Russia'),
(4, 'United Continental', 'USA'),
(5, 'Mesa Airlines', 'USA'),
(6, 'Australian Airlines', 'Austria'),
(7, 'Swiss', 'Switzerland'),
(8, 'Jet Airways', 'India');

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
(1, 1, 2, 3, 3, '2018-01-12 05:15:00', 180, 350),
(2, 2, 3, 2, 2, '2018-02-12 15:35:00', 300, 250),
(3, 3, 4, 1, 1, '2018-03-12 12:45:00', 400, 400),
(4, 4, 5, 3, 3, '2018-04-12 16:55:00', 450, 500),
(5, 5, 6, 2, 2, '2018-05-12 05:25:00', 310, 550),
(6, 6, 7, 1, 1, '2018-06-12 03:05:00', 120, 600),
(7, 7, 8, 3, 3, '2018-07-12 01:15:00', 340, 200),
(8, 8, 1, 2, 2, '2018-08-12 08:35:00', 530, 250),
(9, 1, 3, 2, 2, '2018-09-12 11:50:00', 240, 600),
(10, 2, 4, 1, 1, '2018-10-12 22:40:00', 210, 750),
(11, 3, 5, 3, 3, '2018-11-12 21:20:00', 360, 800);

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
(10, 'Antonov AN-225', 425);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`id`),
  ADD KEY `company` (`company`),
  ADD KEY `destination` (`destination`),
  ADD KEY `origin` (`origin`),
  ADD KEY `plane` (`plane`);

--
-- Indexes for table `plane`
--
ALTER TABLE `plane`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `city`
--
ALTER TABLE `city`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `flight`
--
ALTER TABLE `flight`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `plane`
--
ALTER TABLE `plane`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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
