-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Vert: 127.0.0.1
-- Generert den: 16. Jan, 2017 13:10 PM
-- Tjenerversjon: 5.5.53-0ubuntu0.14.04.1
-- PHP-Versjon: 5.5.9-1ubuntu4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `g_scrum08`
--

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `Busy`
--

CREATE TABLE IF NOT EXISTS `Busy` (
  `user_id` varchar(30) NOT NULL DEFAULT '',
  `shift_id` int(11) NOT NULL DEFAULT '0',
  `my_date` date NOT NULL DEFAULT '0000-00-00',
  PRIMARY KEY (`user_id`,`shift_id`,`my_date`),
  KEY `fk_ShiftIDBusy` (`shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `Busy`
--

INSERT INTO `Busy` (`user_id`, `shift_id`, `my_date`) VALUES
('elena', 1, '2017-01-10'),
('nina', 1, '2017-01-10'),
('maria', 2, '2017-01-11'),
('anders', 3, '2017-01-12');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `Employee`
--

CREATE TABLE IF NOT EXISTS `Employee` (
  `user_id` varchar(30) NOT NULL DEFAULT '',
  `surname` varchar(30) DEFAULT NULL,
  `firstname` varchar(30) DEFAULT NULL,
  `phone_number` char(8) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `Employee`
--

INSERT INTO `Employee` (`user_id`, `surname`, `firstname`, `phone_number`, `email`, `category`) VALUES
('anders', 'Påsche', 'Anders', '48070441', 'apaasche@gmail.com', 3),
('haakonrp', 'Paulsen', 'Haakon', '12345678', 'email@email.com', 2),
('maria', 'Shchekanenko', 'Maria', '45062269', 'maria@gmail.com', 1),
('nina', 'Meedby', 'Nina', '45432269', 'nina@gmail.com', 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `Shift`
--

CREATE TABLE IF NOT EXISTS `Shift` (
  `shift_id` int(11) NOT NULL DEFAULT '0',
  `hours` int(11) DEFAULT NULL,
  `start_time` int(11) DEFAULT NULL,
  `end_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `Shift`
--

INSERT INTO `Shift` (`shift_id`, `hours`, `start_time`, `end_time`) VALUES
(1, 8, 0, 8),
(2, 8, 8, 16),
(3, 8, 16, 24);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `Shift_list`
--

CREATE TABLE IF NOT EXISTS `Shift_list` (
  `user_id` varchar(30) NOT NULL DEFAULT '',
  `shift_id` int(11) NOT NULL DEFAULT '0',
  `on_duty` tinyint(1) DEFAULT NULL,
  `my_date` date DEFAULT NULL,
  `deviance` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`shift_id`),
  KEY `fk_ShiftIDList` (`shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `Shift_list`
--

INSERT INTO `Shift_list` (`user_id`, `shift_id`, `on_duty`, `my_date`, `deviance`) VALUES
('haakonrp', 3, 1, '2017-01-19', 0),
('maria', 1, 1, '2017-01-10', 0),
('nina', 2, 1, '2017-01-10', 0);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `Time_list`
--

CREATE TABLE IF NOT EXISTS `Time_list` (
  `user_id` varchar(30) NOT NULL DEFAULT '',
  `month` varchar(10) NOT NULL DEFAULT '',
  `ordinary` int(11) DEFAULT NULL,
  `overtime` int(11) DEFAULT NULL,
  `absence` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `Time_list`
--

INSERT INTO `Time_list` (`user_id`, `month`, `ordinary`, `overtime`, `absence`) VALUES
('maria', 'Januar', 64, 0, 0),
('nina', 'Januar', 60, 2, 0);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `user_id` varchar(30) NOT NULL DEFAULT '',
  `password` char(128) DEFAULT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `User`
--

INSERT INTO `User` (`user_id`, `password`, `role`) VALUES
('admin', '8BB7055A726FE28FBC65F72B72B16A2CC9F4594AC09D9276EAFCB9B041F7528880BBEC868B9838C5CD7B7D8C35A386B7EA35C8C0DE70013985E1BB7AEFCE5B84', 0),
('anders', 'Anders123', 0),
('canders', '877DD7D08ECB8A0E1DA68EE928E6A5A844733C9ED263FD1693A848115C48B1239D5AB4B1356828A159A2DB8647D2D57CACA5E42909AAD49D813E8232059DE682', 0),
('elena', 'Elena123', 0),
('employee', 'A69E87FB164C3E401C2AE8FEBD9E4CC61CEF8DA8D95AC0269D14691A134AC5CC564E8B1262C35E967813C7DFABDF47B70F81346517B3EEED91B0B81370E42854', 1),
('haakonrp', 'Haakonrp123', 0),
('maria', 'Maria123', 0),
('nina', 'Nina123', 0);

--
-- Begrensninger for dumpede tabeller
--

--
-- Begrensninger for tabell `Busy`
--
ALTER TABLE `Busy`
  ADD CONSTRAINT `fk_ShiftIDBusy` FOREIGN KEY (`shift_id`) REFERENCES `Shift` (`shift_id`),
  ADD CONSTRAINT `fk_UserBusy` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

--
-- Begrensninger for tabell `Employee`
--
ALTER TABLE `Employee`
  ADD CONSTRAINT `fk_UserEmployee` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

--
-- Begrensninger for tabell `Shift_list`
--
ALTER TABLE `Shift_list`
  ADD CONSTRAINT `fk_ShiftIDList` FOREIGN KEY (`shift_id`) REFERENCES `Shift` (`shift_id`),
  ADD CONSTRAINT `fk_UserShiftList` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

--
-- Begrensninger for tabell `Time_list`
--
ALTER TABLE `Time_list`
  ADD CONSTRAINT `fk_UserTimeList` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
