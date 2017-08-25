-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: movie
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movieinfo`
--

DROP TABLE IF EXISTS `movieinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movieinfo` (
  `id` int(11) NOT NULL,
  `title` text,
  `release` int(11) DEFAULT NULL,
  `genre` text,
  `rating` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movieinfo`
--

LOCK TABLES `movieinfo` WRITE;
/*!40000 ALTER TABLE `movieinfo` DISABLE KEYS */;
INSERT INTO `movieinfo` VALUES (1,'Toy Story ',1995,'Adventure|Animation|Children|Comedy|Fantasy',2),(2,'Jumanji ',1995,'Adventure|Children|Fantasy',4),(3,'Grumpier Old Men ',1995,'Comedy|Romance',4),(4,'Waiting to Exhale ',1995,'Comedy|Drama|Romance',2),(5,'Father of the Bride Part II ',1995,'Comedy',2),(6,'Heat ',1995,'Action|Crime|Thriller',2),(7,'Sabrina ',1995,'Comedy|Romance',1),(8,'Tom and Huck ',1995,'Adventure|Children',5),(9,'Sudden Death ',1995,'Action',1),(10,'GoldenEye ',1995,'Action|Adventure|Thriller',2),(11,'American President, The ',1995,'Comedy|Drama|Romance',2),(12,'Dracula: Dead and Loving It ',1995,'Comedy|Horror',3),(13,'Balto ',1995,'Adventure|Animation|Children',1),(14,'Nixon ',1995,'Drama',4),(15,'Cutthroat Island ',1995,'Action|Adventure|Romance',5),(16,'Casino ',1995,'Crime|Drama',4),(17,'Sense and Sensibility ',1995,'Drama|Romance',5),(18,'Four Rooms ',1995,'Comedy',2),(19,'Ace Ventura: When Nature Calls ',1995,'Comedy',4),(20,'Money Train ',1995,'Action|Comedy|Crime|Drama|Thriller',3),(21,'Get Shorty ',1995,'Comedy|Crime|Thriller',5),(22,'Copycat ',1995,'Crime|Drama|Horror|Mystery|Thriller',2),(23,'Assassins ',1995,'Action|Crime|Thriller',4),(24,'Powder ',1995,'Drama|Sci-Fi',3),(25,'Leaving Las Vegas ',1995,'Drama|Romance',2),(26,'Othello ',1995,'Drama',1),(27,'Now and Then ',1995,'Children|Drama',3),(28,'Persuasion ',1995,'Drama|Romance',3);
/*!40000 ALTER TABLE `movieinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-25 13:50:24
