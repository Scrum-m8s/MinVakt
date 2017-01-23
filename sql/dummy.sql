-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Vert: 127.0.0.1
-- Generert den: 18. Jan, 2017 16:41 PM
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
  `surname` varchar(50) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `Employee`
--

INSERT INTO `Employee` (`user_id`, `surname`, `firstname`, `phone_number`, `email`, `category`) VALUES
('admin', 'Nils', 'Hendersen', '911911911', 'set@hørt.no', 0),
('anders', 'Påsche', 'Anders', '48070441', 'apaasche@gmail.com', 3),
('employee', 'Per', 'Gundersen', '1771717711', 'fem@seks.syv', 2),
('haakonrp', 'Paulsen', 'Haakon', '12345678', 'email@email.com', 2),
('kristo4', ' ', ' ', '', ' ', -1),
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
  `want_swap` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `Shift_list`
--

INSERT INTO `Shift_list` (`user_id`, `shift_id`, `on_duty`, `my_date`, `deviance`, `want_swap`) VALUES
('anders', 2, 0, '2017-01-27', 0, 1),
('forsøksperson1', 1, 0, '2017-01-10', 0, NULL),
('forsøksperson2', 1, 0, '2017-01-10', 0, NULL),
('forsøksperson3', 1, 1, '2017-01-10', 0, NULL),
('forsøksperson4', 1, 0, '2017-01-10', 0, NULL),
('haakonrp', 2, 1, '2017-01-30', 0, 1),
('haakonrp', 3, 1, '2017-01-19', 0, 1),
('maria', 1, 1, '2017-01-10', 0, 0),
('nina', 1, 0, '2017-01-11', 0, 1),
('nina', 2, 1, '2017-01-10', 0, 0);

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
('forsøksperson1', 'Januar', 0, 0, -10),
('forsøksperson2', 'Januar', 0, 10, 0),
('forsøksperson3', 'Januar', 0, 288, -42),
('forsøksperson4', 'Januar', 0, 12, 0),
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
('forsøksperson1', 'none', 0),
('forsøksperson2', 'none', 0),
('forsøksperson3', 'none', 0),
('forsøksperson4', 'none', 0),
('haakonrp', 'Haakonrp123', 1),
('heisann', 'C761CD89681AC39772E1D4CA22C619C958A87179AF7A932C2FBC1177C837289EBCB0C981AE8DAC5F436D5E7052AA5077578085CBC48B72F295F14C7F1ACCA542', -1),
('hola', 'C18FA6CE34C0CE0A47C25BA2CFE3A2BC7534A70605495D6DB70C78D97C903E644A8B4D41455A6CEA44B000581240B388AFBC557CF962F3C429F258D8BFEAE12C', -1),
('kristo4', 'A6446F0F0791CE5C8BF32C3B3932E17C1F03F91EA02DBA243BC9A9711044005FB567D77FFF67CAEF031B16EFE2C106BE626F865BB5FB6ACA5D8DAF38F0BA5FE3', 1),
('maria', 'maria1234', 1),
('nina', 'Nina123', 0),
('test', NULL, 0),
('test1', 'test', 1),
('testerino', '76D7AA0062CB36F25F9765ACA4EA9A2907428CB8462BC67223F0125F7D4744E6752BB4EBC66A6C08FA5F57F08CCD5E94E1A63FDB92BADFF830C9737CE0BBCB2B', -1);

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
-- Begrensninger for tabell `Time_list`
--
ALTER TABLE `Time_list`
  ADD CONSTRAINT `fk_UserTimeList` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
