-- MySQL dump 10.13  Distrib 5.6.35, for Win32 (AMD64)
--
-- Host: 127.0.0.1    Database: my_blog
-- ------------------------------------------------------
-- Server version	5.6.35-log

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `idarticle` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_content` text,
  `title` varchar(50) NOT NULL,
  `title_img_path` varchar(500) NOT NULL,
  PRIMARY KEY (`idarticle`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (8,'# 插入图片测试\n* ![Description](http://127.0.0.1:9000/img/4a32ad9c-ce68-4fe6-8e59-ee4207d2ba50_Screenshot%202024-02-24%20192943.png)','第一篇测试','http://127.0.0.1:9000/img/c95de0cb-cbca-406a-b46a-14cbac1f4e15_Screenshot 2024-03-02 182815.png'),(12,'# 有标题？\n* ![Description](http://127.0.0.1:9000/img/d63e2dd3-9466-4a4f-bb7a-47de2fba77b0_Screenshot%202024-02-13%20220227.png)','是真的可用了吗','http://127.0.0.1:9000/img/1de43e51-ea2d-43da-aa08-f717aa5d3c28_Screenshot 2024-02-05 221508.png'),(14,'![图片描述](http://127.0.0.1:9000/img/781600d8-a7a8-44bc-a950-a9d1d20787ae_powershell.jpg)\n![图片描述](http://127.0.0.1:9000/img/bf6e90b3-e9a1-4989-b49b-0c68bbff5cd9_image.png)\n![图片描述](http://127.0.0.1:9000/img/74fec733-9927-4d69-8206-82a50b013aa3_test.png)','测试图片插入','http://127.0.0.1:9000/img/48d433b7-fb7d-44b6-8549-0190e856913b_Screenshot 2024-02-22 170204.png'),(15,'![图片描述](http://127.0.0.1:9000/img/1700f795-7dfa-4ea9-ae5c-b9793c6e97fe_image.png)\n![图片描述](http://127.0.0.1:9000/img/99fe8b0c-d690-4ac0-81e2-81f0e1a50b2c_1BCA136E0B7CEB1F5926EA7AC34BB7F4.jpg)','新的一天测试','http://127.0.0.1:9000/img/c2256605-04dd-4ec0-9cac-21675e66a0db_test.png'),(16,'# 无所谓','第二行第一个','http://127.0.0.1:9000/img/d68bea09-51cc-4c44-afa6-0468ace3fe21_powershell.jpg'),(17,'# 无所谓','第二行第二个','http://127.0.0.1:9000/img/01a64b01-12c0-4ba0-995a-f77b74fc2975_powershell.jpg'),(18,'# 无所谓','第二行第三个','http://127.0.0.1:9000/img/e79623e8-d9c1-4e57-815d-6a5ed081e0e1_powershell.jpg'),(19,'# hi','倒数第二个','http://127.0.0.1:9000/img/1cf05d52-193d-4463-bc31-cd899ef5a797_powershell.jpg'),(20,'# 你好','最后一个了','http://127.0.0.1:9000/img/51aa1091-2fc9-42b0-a08d-78478878777e_test.png');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diary`
--

DROP TABLE IF EXISTS `diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diary` (
  `iddiary` bigint(20) NOT NULL AUTO_INCREMENT,
  `diary_content` text,
  `title` varchar(50) NOT NULL,
  `title_img_path` varchar(500) NOT NULL,
  PRIMARY KEY (`iddiary`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diary`
--

LOCK TABLES `diary` WRITE;
/*!40000 ALTER TABLE `diary` DISABLE KEYS */;
INSERT INTO `diary` VALUES (1,'# 日记\n* 加了一句话\n![图片描述](http://127.0.0.1:9000/img/b99495ab-892e-4f65-822a-08f1605bccd9_test.png)\n![图片描述](http://127.0.0.1:9000/img/1531b252-3402-4290-8fc9-e22d5b23a262_image.png)\n','日记第一篇','http://127.0.0.1:9000/img/46ed184c-36c3-4cd4-a467-ef1842030efa_1BCA136E0B7CEB1F5926EA7AC34BB7F4.jpg');
/*!40000 ALTER TABLE `diary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-25 11:14:46
