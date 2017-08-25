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
-- Table structure for table `moviecomment`
--

DROP TABLE IF EXISTS `moviecomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moviecomment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mvid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `username` text,
  `comment` text,
  `rating` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moviecomment`
--

LOCK TABLES `moviecomment` WRITE;
/*!40000 ALTER TABLE `moviecomment` DISABLE KEYS */;
INSERT INTO `moviecomment` VALUES (1,1,1,'tiep','This movie is good!',5),(2,1,2,'vu','Just average movie',4),(3,2,1,'tiep','Ok movie',3),(4,1,1,'tiep','this is a good movie',4),(5,1,1,'tiep','Nice movie for a week end',5),(6,2,4,'testuser','I don\'t think it is good',2),(7,3,4,'testuser','Just ok movie',3),(8,3,2,'vu','Fantastic movie',4),(9,3,2,'vu','gotta watch it again',5),(10,3,1,'tiep','asfkasgj',4),(11,15,1,'tiep','not good to watch',1),(12,4,4,'testuser','my first comment here. ',3),(13,9,1,'tiep','Fantastic movie',5),(14,1,9,'admin','admin approved the movie',3),(15,1,1,'tiep','hehehe',4),(16,1,17,'abc','skfa safka',3);
/*!40000 ALTER TABLE `moviecomment` ENABLE KEYS */;
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
