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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text NOT NULL,
  `passwd` text NOT NULL,
  `fullname` text,
  `mobile` text,
  `email` text,
  `dob` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'vu','1','Vu Tiep','010-4991-3139','vu@gaion.kr','1990-01-03'),(4,'testuser','1','Test User','010-4991-3139','vuduc@gaion.kr','1991-03-13'),(6,'duc','1','Duc Tiep','01049913139','duc@gaion.kr','1991-04-05'),(8,'thanh','1','Doctor Thanh','010-1111-1111','thanh@gaion.kr','1981-01-01'),(9,'admin','1','Administrator','010-4991-3139','admin@gaion.kr','1991-03-13'),(10,'testuser3','1','test 3','912491591','ab@gaion.kr','1995-01-05'),(11,'testuser1','1','Test Dep trai','01010241','tsakf@gaion.kr','1991-12-12'),(12,'testuser4','1','test 3 xau trai','912491591','ab@gaion.kr','1999-05-06'),(14,'testuser2','1','test 3','912491591','ab@gaion.kr','1888-03-05'),(15,'testuser5','1','test 3','912491591','ab@gaion.kr','1998-12-22'),(16,'the','1','Hoang Ngoc The','010-4411-4210','the@gaion.kr','1994-01-02'),(17,'abc','1','abc dkdk','010100','abc@gaion.kr','1990-08-09');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
