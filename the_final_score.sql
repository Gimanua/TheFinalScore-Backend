-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 17 jan 2020 kl 08:43
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
-- Tabellstruktur `movies`
--

CREATE TABLE `movies` (
  `id` int(10) UNSIGNED NOT NULL,
  `title` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `saved_movies`
--

CREATE TABLE `saved_movies` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL
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
-- AUTO_INCREMENT för tabell `movies`
--
ALTER TABLE `movies`
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
