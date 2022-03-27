-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: examsystem
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clazz`
--

DROP TABLE IF EXISTS `clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clazz` (
  `clazz_id` int NOT NULL AUTO_INCREMENT,
  `clazz_name` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`clazz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz`
--

LOCK TABLES `clazz` WRITE;
/*!40000 ALTER TABLE `clazz` DISABLE KEYS */;
INSERT INTO `clazz` VALUES (1,'计师1901','计师'),(2,'教技1901','教技');
/*!40000 ALTER TABLE `clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sss77699ktvii9dpb1wu8yl4w` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,'admin','$2a$10$12zjo43/83fLj1xPbu8cmOQ4IuI4m2a.JfC7LKXj/vP.fg82DAEDa');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `single_question`
--

DROP TABLE IF EXISTS `single_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `single_question` (
  `ques_id` int NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `opta` varchar(255) DEFAULT NULL,
  `optb` varchar(255) DEFAULT NULL,
  `optc` varchar(255) DEFAULT NULL,
  `optd` varchar(255) DEFAULT NULL,
  `ques_title` varchar(255) DEFAULT NULL,
  `sub_id` int DEFAULT NULL,
  PRIMARY KEY (`ques_id`),
  KEY `FKtl81a8g2q1iiuf9yocsecjrcl` (`sub_id`),
  CONSTRAINT `FKtl81a8g2q1iiuf9yocsecjrcl` FOREIGN KEY (`sub_id`) REFERENCES `subject` (`sub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `single_question`
--

LOCK TABLES `single_question` WRITE;
/*!40000 ALTER TABLE `single_question` DISABLE KEYS */;
INSERT INTO `single_question` VALUES (1,'A','4','5','6','7','123123',1);
/*!40000 ALTER TABLE `single_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `stu_name` varchar(255) DEFAULT NULL,
  `clazz_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_in0cc54bjhs1jqoon9m0tf2ib` (`account`),
  KEY `FKr6vhwx3i4blsfj07c5ttqwj9p` (`clazz_id`),
  CONSTRAINT `FKr6vhwx3i4blsfj07c5ttqwj9p` FOREIGN KEY (`clazz_id`) REFERENCES `clazz` (`clazz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'111','$2a$10$su6dBRO/PkeEpugb.TIlLOBUWSSUJvY7I6JbZQ/So8loKEm4P2fQC',1,'张三',1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `sub_id` int NOT NULL AUTO_INCREMENT,
  `sub_name` varchar(255) DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  PRIMARY KEY (`sub_id`),
  KEY `FKdvgvxo0oxhxeepkkwug7vg4w4` (`teacher_id`),
  CONSTRAINT `FKdvgvxo0oxhxeepkkwug7vg4w4` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'数据库',1),(2,'高数',2),(3,'大物',3);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_has_student`
--

DROP TABLE IF EXISTS `subject_has_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject_has_student` (
  `subject_sub_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`subject_sub_id`,`student_id`),
  KEY `fk_subject_has_student_student1_idx` (`student_id`),
  KEY `fk_subject_has_student_subject1_idx` (`subject_sub_id`),
  CONSTRAINT `fk_subject_has_student_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `fk_subject_has_student_subject1` FOREIGN KEY (`subject_sub_id`) REFERENCES `subject` (`sub_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_has_student`
--

LOCK TABLES `subject_has_student` WRITE;
/*!40000 ALTER TABLE `subject_has_student` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject_has_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `tch_name` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4sx6w4ufcg0cepgorwspiqa9o` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'123','','计师','$2a$10$0sG7IeRQutRpthkomLRWt.0GiNHL4bA7A1d1Bprol9TNy0dLF2QH6',0,'lll',''),(2,'456','','计师','$2a$10$C8Sc6xYnZaYY8eOb/PkVpOBTNLsY4xTUW6ea7Bl8hCmRV8mc5/Gv2',1,'kkk',''),(3,'789','','教技','$2a$10$nIZlBE8TzCB.9ZoyGO4XXepexi2OvuQel95yLg23buIYYxVUNG1Ga',1,'jjj',''),(4,'147','','教技','$2a$10$SBVqLyMBwKy1zIfOtbGg0.IEoHqXCQQW6unltQwQ5i0d2nEYNjcGi',1,'ppp',''),(5,'258','','计师','$2a$10$L8qBnNLYfTau2JZDwJzSpu3juYTxLzuK5Bn3eQtXdx9RflbLz.EZG',0,'ooo',''),(6,'369','','计师','$2a$10$mphwhiqG.BUCaI2uEHjt..W1FCbhQ7MN//sfqv7eeAgFwu6kfGJau',1,'uuu',''),(7,'159','','教技','$2a$10$6SKPVSKKEDgTDXp4eKZg7.xPMmxjzM10s1.f7q2F8FhxY9DUJCeDi',0,'hhh',''),(8,'5656','','计师','$2a$10$ZL16aPetPAc7gF/Tx3Xo9..1ZOcuUACYHDPar53qtYb.Zbnvjlub2',0,'sds','');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `test_id` int NOT NULL AUTO_INCREMENT,
  `begin_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `exam_dure` int DEFAULT NULL,
  `exam_time` int DEFAULT NULL,
  `ques_arr` varchar(255) DEFAULT NULL,
  `test_name` varchar(255) DEFAULT NULL,
  `total_score` int DEFAULT NULL,
  `sub_id` int NOT NULL,
  `test_clazz` int DEFAULT NULL,
  PRIMARY KEY (`test_id`),
  KEY `FK7ygve5cb0x1aihv5vu5c8rs18` (`sub_id`),
  KEY `FKt6vx3t5h6f0m2r5n9utq85e0l` (`test_clazz`),
  CONSTRAINT `FK7ygve5cb0x1aihv5vu5c8rs18` FOREIGN KEY (`sub_id`) REFERENCES `subject` (`sub_id`),
  CONSTRAINT `FKt6vx3t5h6f0m2r5n9utq85e0l` FOREIGN KEY (`test_clazz`) REFERENCES `clazz` (`clazz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'2021-06-23 00:00:00.000000','','2021-07-08 00:00:00.000000',60,2,'11','数据库',NULL,1,1);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testhistory`
--

DROP TABLE IF EXISTS `testhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testhistory` (
  `id` int NOT NULL,
  `finish_date` datetime(6) DEFAULT NULL,
  `score` int DEFAULT NULL,
  `stu_id` int DEFAULT NULL,
  `test_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhfi2hyk61yy5e5sknkb5rmiyy` (`stu_id`),
  KEY `FKe3lr8xvg2wfis8on8v9tr8yew` (`test_id`),
  CONSTRAINT `FKe3lr8xvg2wfis8on8v9tr8yew` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`),
  CONSTRAINT `FKhfi2hyk61yy5e5sknkb5rmiyy` FOREIGN KEY (`stu_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testhistory`
--

LOCK TABLES `testhistory` WRITE;
/*!40000 ALTER TABLE `testhistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `testhistory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-26 22:30:32
