-- MySQL dump 10.13  Distrib 5.5.62, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: STOCKSYSTEM
-- ------------------------------------------------------
-- Server version	5.5.62-0ubuntu0.14.04.1

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
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `DateOpened` date DEFAULT NULL,
  `Client` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Client` (`Client`),
  CONSTRAINT `Account_ibfk_1` FOREIGN KEY (`Client`) REFERENCES `Client` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account`
--

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;
INSERT INTO `Account` VALUES (1,'2019-05-06',125447982);
/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Client`
--

DROP TABLE IF EXISTS `Client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Client` (
  `Email` char(50) DEFAULT NULL,
  `Rating` int(11) DEFAULT '0',
  `CreditCardNumber` bigint(20) DEFAULT NULL,
  `Id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  CONSTRAINT `Client_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `Person` (`SSN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Client`
--

LOCK TABLES `Client` WRITE;
/*!40000 ALTER TABLE `Client` DISABLE KEYS */;
INSERT INTO `Client` VALUES ('howard.customer@test.com',3,1234569878909870,125447982);
/*!40000 ALTER TABLE `Client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employee`
--

DROP TABLE IF EXISTS `Employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Employee` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Role` char(30) DEFAULT NULL,
  `SSN` int(11) DEFAULT NULL,
  `StartDate` date DEFAULT NULL,
  `HourlyRate` float DEFAULT '20',
  PRIMARY KEY (`ID`),
  KEY `SSN` (`SSN`),
  CONSTRAINT `Employee_ibfk_1` FOREIGN KEY (`SSN`) REFERENCES `Person` (`SSN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employee`
--

LOCK TABLES `Employee` WRITE;
/*!40000 ALTER TABLE `Employee` DISABLE KEYS */;
INSERT INTO `Employee` VALUES (1,'manager',874592145,'2019-05-06',20),(2,'customerRepresentative',789541263,'2019-05-06',20),(3,'manager',111111111,'2019-05-06',20),(4,'customerRepresentative',123121234,'1999-09-12',20);
/*!40000 ALTER TABLE `Employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HasStock`
--

DROP TABLE IF EXISTS `HasStock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HasStock` (
  `StockID` char(20) NOT NULL DEFAULT '',
  `AccountID` int(11) NOT NULL DEFAULT '0',
  `NumberOfShare` int(11) DEFAULT NULL,
  PRIMARY KEY (`AccountID`,`StockID`),
  KEY `StockID` (`StockID`),
  CONSTRAINT `HasStock_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `Account` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `HasStock_ibfk_2` FOREIGN KEY (`StockID`) REFERENCES `Stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HasStock`
--

LOCK TABLES `HasStock` WRITE;
/*!40000 ALTER TABLE `HasStock` DISABLE KEYS */;
INSERT INTO `HasStock` VALUES ('AAPL',1,100);
/*!40000 ALTER TABLE `HasStock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HiddenStop`
--

DROP TABLE IF EXISTS `HiddenStop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HiddenStop` (
  `OrderId` int(11) NOT NULL DEFAULT '0',
  `PricePerShare` double DEFAULT NULL,
  `OriginalPrice` double DEFAULT NULL,
  `OrderDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`OrderId`,`OrderDate`),
  CONSTRAINT `HiddenStop_ibfk_1` FOREIGN KEY (`OrderId`) REFERENCES `Orders` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HiddenStop`
--

LOCK TABLES `HiddenStop` WRITE;
/*!40000 ALTER TABLE `HiddenStop` DISABLE KEYS */;
INSERT INTO `HiddenStop` VALUES (36,207.27,200,'2019-05-06 13:53:00'),(36,10,200,'2019-05-06 13:53:21');
/*!40000 ALTER TABLE `HiddenStop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Location` (
  `ZipCode` int(11) NOT NULL DEFAULT '0',
  `City` char(20) NOT NULL,
  `State` char(20) NOT NULL,
  PRIMARY KEY (`ZipCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
INSERT INTO `Location` VALUES (11780,'St. James','NY'),(11790,'Stony Brook','NY'),(48336,'farmington','Michigan');
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Login`
--

DROP TABLE IF EXISTS `Login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Login` (
  `Email` char(50) NOT NULL,
  `Password` char(20) NOT NULL,
  `Role` char(30) NOT NULL,
  PRIMARY KEY (`Email`),
  CONSTRAINT `Login_ibfk_1` FOREIGN KEY (`Email`) REFERENCES `Person` (`Email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Login`
--

LOCK TABLES `Login` WRITE;
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
INSERT INTO `Login` VALUES ('alice.manager@test.com','test','manager'),('howard.customer@test.com','test','customer'),('john.cr@test.com','test','customerRepresentative'),('test2@test.com','qq123456','customerRepresentative'),('test@test.com','qq123456','manager');
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Orders` (
  `NumShares` int(11) DEFAULT NULL,
  `PricePerShare` double DEFAULT NULL,
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `DateTime` datetime DEFAULT NULL,
  `Percentage` double DEFAULT NULL,
  `PriceType` char(20) DEFAULT NULL,
  `OrderType` char(5) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (500,207.27,32,'2019-05-06 13:52:15',NULL,'Market','Buy'),(500,207.27,33,'2019-05-06 13:52:22',NULL,'Market','Buy'),(500,207.27,34,'2019-05-06 13:52:33',NULL,'MarketOnClose','Sell'),(300,NULL,35,'2019-05-06 13:52:47',-0.05,'TrailingStop','Sell'),(300,200,36,'2019-05-06 13:53:00',NULL,'HiddenStop','Sell'),(100,10,37,'2019-05-06 15:52:52',NULL,'Market','Buy');
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Person`
--

DROP TABLE IF EXISTS `Person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Person` (
  `SSN` int(11) NOT NULL DEFAULT '0',
  `LastName` char(20) NOT NULL,
  `FirstName` char(20) NOT NULL,
  `Address` char(20) DEFAULT NULL,
  `ZipCode` int(11) DEFAULT NULL,
  `Telephone` bigint(20) DEFAULT NULL,
  `Email` char(50) NOT NULL,
  PRIMARY KEY (`SSN`),
  UNIQUE KEY `Email` (`Email`),
  KEY `ZipCode` (`ZipCode`),
  CONSTRAINT `Person_ibfk_1` FOREIGN KEY (`ZipCode`) REFERENCES `Location` (`ZipCode`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Person`
--

LOCK TABLES `Person` WRITE;
/*!40000 ALTER TABLE `Person` DISABLE KEYS */;
INSERT INTO `Person` VALUES (111111111,'test','test','test',11790,1234567890,'test@test.com'),(123121234,'test2','test2','test2',11790,1234567899,'test2@test.com'),(125447982,'Customer','Howard','12 784thRd',11790,6987451236,'howard.customer@test.com'),(789541263,'CR','John','210 15th Street',11780,6541125972,'john.cr@test.com'),(874592145,'Manager','Alice','212301 farmington Rd',48336,6512236984,'alice.manager@test.com');
/*!40000 ALTER TABLE `Person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Stock`
--

DROP TABLE IF EXISTS `Stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Stock` (
  `StockSymbol` char(20) NOT NULL,
  `CompanyName` char(20) NOT NULL,
  `Type` char(20) NOT NULL,
  `PricePerShare` double DEFAULT NULL,
  `TotalShare` int(11) DEFAULT NULL,
  PRIMARY KEY (`StockSymbol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Stock`
--

LOCK TABLES `Stock` WRITE;
/*!40000 ALTER TABLE `Stock` DISABLE KEYS */;
INSERT INTO `Stock` VALUES ('AAPL','Apple Inc','Technology',10,1000000),('AMZN','Amazom Inc','E-commerce',1943.75,8001000),('GOOGL','Alphabet Inc','Technology',1185.04,1222000),('MSFT','Microsoft Corp','Technology',127.5,6500000),('TEST','Test.Inc','Test',777,1000),('TM','Toyota','Automobile',122.2,580000),('TSLA','Tesla Inc','Automobile',253.82,478000);
/*!40000 ALTER TABLE `Stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StockHistory`
--

DROP TABLE IF EXISTS `StockHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StockHistory` (
  `StockSymbol` char(20) NOT NULL DEFAULT '',
  `ChangeDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `PricePerShare` double DEFAULT NULL,
  PRIMARY KEY (`StockSymbol`,`ChangeDate`),
  CONSTRAINT `StockHistory_ibfk_1` FOREIGN KEY (`StockSymbol`) REFERENCES `Stock` (`StockSymbol`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StockHistory`
--

LOCK TABLES `StockHistory` WRITE;
/*!40000 ALTER TABLE `StockHistory` DISABLE KEYS */;
INSERT INTO `StockHistory` VALUES ('AAPL','2019-05-01 00:00:00',207.27),('AAPL','2019-05-06 13:53:20',10),('AMZN','2019-05-01 00:00:00',1943.75),('GOOGL','2019-05-01 00:00:00',1185.04),('MSFT','2019-05-01 00:00:00',127.5),('TEST','2019-05-07 11:59:23',777),('TM','2019-05-01 00:00:00',122.2),('TSLA','2019-05-01 00:00:00',253.82);
/*!40000 ALTER TABLE `StockHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Trade`
--

DROP TABLE IF EXISTS `Trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Trade` (
  `AccountId` int(11) NOT NULL DEFAULT '0',
  `BrokerId` int(11) DEFAULT '0',
  `TransactionId` int(11) NOT NULL DEFAULT '0',
  `OrderId` int(11) NOT NULL DEFAULT '0',
  `StockId` char(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`AccountId`,`TransactionId`,`OrderId`,`StockId`),
  KEY `BrokerId` (`BrokerId`),
  KEY `TransactionId` (`TransactionId`),
  KEY `OrderId` (`OrderId`),
  KEY `StockId` (`StockId`),
  CONSTRAINT `Trade_ibfk_1` FOREIGN KEY (`AccountId`) REFERENCES `Account` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `Trade_ibfk_2` FOREIGN KEY (`BrokerId`) REFERENCES `Employee` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `Trade_ibfk_3` FOREIGN KEY (`TransactionId`) REFERENCES `Transaction` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `Trade_ibfk_4` FOREIGN KEY (`OrderId`) REFERENCES `Orders` (`Id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `Trade_ibfk_5` FOREIGN KEY (`StockId`) REFERENCES `Stock` (`StockSymbol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trade`
--

LOCK TABLES `Trade` WRITE;
/*!40000 ALTER TABLE `Trade` DISABLE KEYS */;
INSERT INTO `Trade` VALUES (1,NULL,32,32,'AAPL'),(1,NULL,33,33,'AAPL'),(1,NULL,34,34,'AAPL'),(1,NULL,35,35,'AAPL'),(1,NULL,36,36,'AAPL'),(1,4,37,37,'AAPL');
/*!40000 ALTER TABLE `Trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TrailingStop`
--

DROP TABLE IF EXISTS `TrailingStop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TrailingStop` (
  `OrderId` int(11) NOT NULL DEFAULT '0',
  `PricePerShare` double DEFAULT NULL,
  `OriginalPrice` double DEFAULT NULL,
  `OrderDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`OrderId`,`OrderDate`),
  CONSTRAINT `TrailingStop_ibfk_1` FOREIGN KEY (`OrderId`) REFERENCES `Orders` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TrailingStop`
--

LOCK TABLES `TrailingStop` WRITE;
/*!40000 ALTER TABLE `TrailingStop` DISABLE KEYS */;
INSERT INTO `TrailingStop` VALUES (35,207.27,196.9065,'2019-05-06 13:52:47'),(35,10,196.9065,'2019-05-06 13:53:22');
/*!40000 ALTER TABLE `TrailingStop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transaction`
--

DROP TABLE IF EXISTS `Transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Transaction` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Fee` double DEFAULT NULL,
  `DateTime` datetime DEFAULT NULL,
  `PricePerShare` double DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction`
--

LOCK TABLES `Transaction` WRITE;
/*!40000 ALTER TABLE `Transaction` DISABLE KEYS */;
INSERT INTO `Transaction` VALUES (32,5181.75,'2019-05-06 13:52:15',207.27),(33,5181.75,'2019-05-06 13:52:22',207.27),(34,5181.75,'2019-05-06 13:52:33',207.27),(35,100,'2019-05-06 13:52:47',10),(36,150,'2019-05-06 13:53:00',10),(37,50,'2019-05-06 15:52:52',10);
/*!40000 ALTER TABLE `Transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-07 13:22:59
