-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 24 jan 2020 kl 11:24
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
  `id` int(10) UNSIGNED NOT NULL COMMENT 'The identifier for the cast member.',
  `movie_id` int(10) UNSIGNED NOT NULL COMMENT 'The movie the cast member belongs to.',
  `name` varchar(128) NOT NULL COMMENT 'The name of the cast member.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `genres`
--

CREATE TABLE `genres` (
  `id` int(10) UNSIGNED NOT NULL COMMENT 'The identifier for the genre.',
  `movie_id` int(10) UNSIGNED NOT NULL COMMENT 'The movie the genre belongs to.',
  `name` varchar(128) NOT NULL COMMENT 'The name of the genre.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `languages`
--

CREATE TABLE `languages` (
  `id` int(10) UNSIGNED NOT NULL COMMENT 'The identifier for the language.',
  `movie_id` int(10) UNSIGNED NOT NULL COMMENT 'The movie the language belongs to.',
  `name` varchar(128) NOT NULL COMMENT 'The name of the language.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `movies`
--

CREATE TABLE `movies` (
  `id` int(10) UNSIGNED NOT NULL COMMENT 'The identifier for the movie.',
  `title` varchar(256) NOT NULL COMMENT 'The title of the movie.',
  `synopsis` varchar(2048) NOT NULL COMMENT 'The synopsis/plot/story of the movie.',
  `logo` varchar(2048) NOT NULL COMMENT 'The path to the logo of the movie.',
  `director` varchar(128) NOT NULL COMMENT 'The name of the movies director.',
  `year` year(4) NOT NULL COMMENT 'The year the movie was released.',
  `runtime` varchar(32) NOT NULL COMMENT 'The runtime of the movie.',
  `released` varchar(32) NOT NULL COMMENT 'The release date of the movie.',
  `type` varchar(128) NOT NULL COMMENT 'The type of of the movie. (Series/Movie e.t.c.)',
  `final_score` varchar(64) NOT NULL COMMENT 'The average of all the movies scores.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `saved_movies`
--

CREATE TABLE `saved_movies` (
  `user_id` int(10) UNSIGNED NOT NULL COMMENT 'The user.',
  `movie_id` int(10) UNSIGNED NOT NULL COMMENT 'The movie the user has saved.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `scores`
--

CREATE TABLE `scores` (
  `id` int(10) UNSIGNED NOT NULL COMMENT 'The identifier for the score.',
  `movie_id` int(10) UNSIGNED NOT NULL COMMENT 'The movie the score belongs to.',
  `value` varchar(8) NOT NULL COMMENT 'The value of the score.',
  `source` varchar(64) NOT NULL COMMENT 'The source of the score. (Who made the score)',
  `source_logo` varchar(256) NOT NULL COMMENT 'The path to the logo of the source.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellstruktur `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL COMMENT 'The identifier for the user.',
  `username` varchar(32) NOT NULL COMMENT 'The username of the user.',
  `hashed_password` varchar(256) DEFAULT NULL COMMENT 'The password in hashed form of the user.',
  `oauth_id` int(10) UNSIGNED DEFAULT NULL COMMENT 'The OAuth id of the user.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  ADD PRIMARY KEY (`id`),
  ADD KEY `movie_id` (`movie_id`);

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
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`);

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
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The identifier for the cast member.';

--
-- AUTO_INCREMENT för tabell `genres`
--
ALTER TABLE `genres`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The identifier for the genre.';

--
-- AUTO_INCREMENT för tabell `languages`
--
ALTER TABLE `languages`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The identifier for the language.';

--
-- AUTO_INCREMENT för tabell `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The identifier for the movie.';

--
-- AUTO_INCREMENT för tabell `scores`
--
ALTER TABLE `scores`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The identifier for the score.';

--
-- AUTO_INCREMENT för tabell `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The identifier for the user.';

--
-- Restriktioner för dumpade tabeller
--

--
-- Restriktioner för tabell `casts`
--
ALTER TABLE `casts`
  ADD CONSTRAINT `casts_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);

--
-- Restriktioner för tabell `genres`
--
ALTER TABLE `genres`
  ADD CONSTRAINT `genres_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);

--
-- Restriktioner för tabell `languages`
--
ALTER TABLE `languages`
  ADD CONSTRAINT `languages_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);

--
-- Restriktioner för tabell `saved_movies`
--
ALTER TABLE `saved_movies`
  ADD CONSTRAINT `movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Restriktioner för tabell `scores`
--
ALTER TABLE `scores`
  ADD CONSTRAINT `scores_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
