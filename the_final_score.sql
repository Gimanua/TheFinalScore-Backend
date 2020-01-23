-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 23 jan 2020 kl 09:39
-- Serverversion: 10.4.8-MariaDB
-- PHP-version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `the_final_score`
--
CREATE DATABASE IF NOT EXISTS `the_final_score` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `the_final_score`;

-- --------------------------------------------------------

--
-- Tabellstruktur `casts`
--

CREATE TABLE `casts` (
  `id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `genres`
--

CREATE TABLE `genres` (
  `id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `languages`
--

CREATE TABLE `languages` (
  `id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `movies`
--

CREATE TABLE `movies` (
  `id` int(10) UNSIGNED NOT NULL,
  `title` varchar(256) NOT NULL,
  `synopsis` varchar(2048) NOT NULL,
  `logo` varchar(2048) NOT NULL,
  `director` varchar(128) NOT NULL,
  `year` year(4) NOT NULL,
  `runtime` varchar(32) NOT NULL,
  `released` varchar(32) NOT NULL,
  `type` varchar(128) NOT NULL,
  `final_score` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumpning av Data i tabell `movies`
--

INSERT INTO `movies` (`id`, `title`, `synopsis`, `logo`, `director`, `year`, `runtime`, `released`, `type`, `final_score`) VALUES
(6, 'Bat Thumb', 'The Bat Thumb must stop another villian from taking over his precious city, only this time he will have to stop No Face in the hardest battle to date.', 'https://m.media-amazon.com/images/M/MV5BMTY3ODc3MjMzM15BMl5BanBnXkFtZTcwMjIwNzIyMQ@@._V1_SX300.jpg', 'David Bourla', 2001, '28 min', '01 Jan 2001', 'movie', '6.7'),
(7, 'Bat*21', 'Lt. Col. Iceal \"Ham\" Hambleton is a weapons countermeasures expert and when his aircraft is shot over enemy territory the Air Force very much wants to get him back. Hambleton knows the area he\'s in is going to be carpet-bombed but a temporary shortage of helicopters causes a delay. Working with an Air Force reconnaissance pilot, Capt. Bartholomew Clark, he maps out an escape route based on golf courses he has played. Along the way however, he has to face enemy forces and the death of some of his fellow soldiers.', 'https://m.media-amazon.com/images/M/MV5BZDRmNjYwZDktOTYxZi00MTdlLWI5ZjYtYWU4MDE5MDc5NGM3L2ltYWdlXkEyXkFqcGdeQXVyNjQzNDI3NzY@._V1_SX300.jpg', 'Peter Markle', 1988, '105 min', '21 Oct 1988', 'movie', '6.8'),
(8, 'Orange County', 'Shaun Brumder is a local surfer kid from Orange County who dreams of going to Stanford to become a writer and to get away from his disfunctional family household. Except Shaun runs into one complication after another starting when his application is rejected after his dim-witted guidance counselor sends the wrong application. So, Shaun goes to great lengths with a little help from his girlfriend Ashley and his drugged-out loser brother Lance to get into Stanford any way they see fit.', 'https://m.media-amazon.com/images/M/MV5BZGIzYjJmNzQtNDc5YS00ZWJhLWI3NzItODgzNWRlNmQ0NmRlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg', 'Jake Kasdan', 2002, '82 min', '11 Jan 2002', 'movie', '5.2'),
(9, 'Stop-Loss', 'Decorated Iraq war hero Sgt. Brandon King makes a celebrated return to his small Texas hometown following his tour of duty. He tries to resume the life he left behind. Then, against Brandon\'s will, the Army orders him back to duty in Iraq, which upends his world. The conflict tests everything he believes in: the bond of family, the loyalty of friendship, the limits of love and the value of honor.', 'https://m.media-amazon.com/images/M/MV5BMjAyNTI1NDg1MV5BMl5BanBnXkFtZTcwNzE2NTg1MQ@@._V1_SX300.jpg', 'Kimberly Peirce', 2008, '112 min', '28 Mar 2008', 'movie', '6.3');

-- --------------------------------------------------------

--
-- Tabellstruktur `saved_movies`
--

CREATE TABLE `saved_movies` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumpning av Data i tabell `saved_movies`
--

INSERT INTO `saved_movies` (`user_id`, `movie_id`) VALUES
(13, 6),
(13, 7),
(13, 8),
(13, 9);

-- --------------------------------------------------------

--
-- Tabellstruktur `scores`
--

CREATE TABLE `scores` (
  `id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL,
  `value` varchar(8) NOT NULL,
  `source` varchar(64) NOT NULL,
  `source_logo` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `username` varchar(32) NOT NULL,
  `hashed_password` varchar(256) DEFAULT NULL,
  `oauth_id` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumpning av Data i tabell `users`
--

INSERT INTO `users` (`id`, `username`, `hashed_password`, `oauth_id`) VALUES
(4, 'testuser', '$2a$12$yWHepfZPDKQ/Q26o5QzEU.lGeIfxysiWKP6H65cIoWZkqibBcEljO', NULL),
(6, 'testuser2', '$2a$12$FicMIjz4nt28jgSW3B/tE.Cpz9bJWOD9fRNkK3CjRvy4rDBuDHV..', NULL),
(7, 'testuser2232', '$2a$12$Tt54iXBU9Is.K8kWop5kaua9gAJoDXzsPuk6mYXWFG9IUhJCqD5UO', NULL),
(9, 'testuser22', '$2a$12$jnBoTxPw/NT6TMvehhrtfOX6O5TD1XytaBUWtKlISqYnaNihVRPzS', NULL),
(10, 'testuser9001', '$2a$12$Xa9qyJi6QnoM18x/bh6WHOfbrhYOTAHpj7MCpK0hfPccYbNRYo0hy', NULL),
(11, 'newtest', '$2a$12$kiR/x/LSvyU4dbBDLaflhuT33aSBOfF/TgmQIFgFRNBET12sx0UfG', NULL),
(13, 'Gimanua', NULL, 31548090),
(17, 'newtester', '$2a$12$gjJF7CwRg/O7Fsnyeu3q/eiRjIKsqOPXbMfBVrfU7gwYSNAvCkJvu', NULL),
(18, 'hey', '$2a$12$7.ynGpVRJVKDiyg9rMc50eFTM2fgraSeDPDF6gW4LdqT2n98LO806', NULL),
(19, 'heyhohey', '$2a$12$cisFw9T6XDP.Yx.JxrMgquckclQgvJRNlci7AHBnpVqVPn96jZtWK', NULL),
(20, 'hopp', '$2a$12$jz.5r0MAx8d0IWeKRir4yOerYhwqLLgyxVveU7QfrM6O3hKt9wpI6', NULL),
(21, 'haha', '$2a$12$JUuTNvaArMVHKwbKlwz.quvPV2Mpj/An8Y07kDD9r9zP3J/A9wC.W', NULL),
(22, 'mok', '$2a$12$pR14ONXT1XvYLkx6j6DgBeTSqSk4mHfDRf2cfMiyGUQwDf3oPoFcy', NULL);

--
-- Index för dumpade tabeller
--

--
-- Index för tabell `casts`
--
ALTER TABLE `casts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Index för tabell `genres`
--
ALTER TABLE `genres`
  ADD PRIMARY KEY (`id`);

--
-- Index för tabell `languages`
--
ALTER TABLE `languages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Index för tabell `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- Index för tabell `saved_movies`
--
ALTER TABLE `saved_movies`
  ADD PRIMARY KEY (`user_id`,`movie_id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Index för tabell `scores`
--
ALTER TABLE `scores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Index för tabell `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `oauth_id` (`oauth_id`);

--
-- AUTO_INCREMENT för dumpade tabeller
--

--
-- AUTO_INCREMENT för tabell `casts`
--
ALTER TABLE `casts`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT för tabell `genres`
--
ALTER TABLE `genres`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT för tabell `languages`
--
ALTER TABLE `languages`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT för tabell `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT för tabell `scores`
--
ALTER TABLE `scores`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT för tabell `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Restriktioner för dumpade tabeller
--

--
-- Restriktioner för tabell `saved_movies`
--
ALTER TABLE `saved_movies`
  ADD CONSTRAINT `movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
