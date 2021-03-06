CREATE DATABASE STOCKSYSTEM;
set foreign_key_checks =0;
USE STOCKSYSTEM;
CREATE TABLE Person (
SSN BIGINT,
LastName CHAR(20) NOT NULL,
FirstName CHAR(20) NOT NULL,
Address CHAR(20),
ZipCode INTEGER,
Telephone BIGINT,
PRIMARY KEY (SSN),
FOREIGN KEY (ZipCode) REFERENCES Location (ZipCode)
  ON DELETE NO ACTION
  ON UPDATE CASCADE );

CREATE TABLE Location (
ZipCode INTEGER,
City CHAR(20) NOT NULL,
State CHAR(20) NOT NULL,
PRIMARY KEY (ZipCode) );

CREATE TABLE Employee (
ID INTEGER,
SSN INTEGER,
StartDate DATE,
HourlyRate INTEGER,
CHECK (ID > 0 AND ID < 1000000000),
PRIMARY KEY (ID),
FOREIGN KEY (SSN) REFERENCES Person (SSN)
  ON DELETE NO ACTION
  ON UPDATE CASCADE );

CREATE TABLE Account (
Id INTEGER,
DateOpened DATE,
Client INTEGER,
CHECK (Id > 0 AND Id < 1000000000),
PRIMARY KEY (Id),
FOREIGN KEY (Client) REFERENCES Client (Id)
  ON DELETE NO ACTION
  ON UPDATE CASCADE );

CREATE TABLE Client (
Email CHAR(32),
Rating INTEGER,
CreditCardNumber INTEGER,
Id BIGINT,
CHECK (Id > 0 AND Id < 1000000000),
PRIMARY KEY (Id),
FOREIGN KEY (Id) REFERENCES Person (SSN)
  ON DELETE NO ACTION
  ON UPDATE CASCADE );

CREATE TABLE Transaction (
Id BIGINT,
Fee DOUBLE,
DateTime DATETIME,
PricePerShare DOUBLE,
PRIMARY KEY (Id) );

CREATE TABLE Orders (
NumShares INTEGER,
PricePerShare DOUBLE,
Id INTEGER,
DateTime DATETIME,
Percentage DOUBLE,
CHECK ( Percentage>=-100.0),
PriceType CHAR(20),
CHECK ( PriceType IN ('Market', 'MarketOnClose', 'TrailingStop', 'HiddenStop')),
OrderType CHAR(5),
CHECK ( OrderType IN ('BUY','SELL') ),
PRIMARY KEY (Id) );

CREATE TABLE Stock (
StockSymbol CHAR(20) NOT NULL,
CompanyName CHAR(20) NOT NULL,
Type CHAR(20) NOT NULL,
PricePerShare DOUBLE,
PRIMARY KEY (StockSymbol) );

CREATE TABLE Trade (
AccountId INTEGER,
BrokerId INTEGER,
TransactionId INTEGER,
OrderId INTEGER,
StockId CHAR(20),
CHECK (BrokerId > 0 AND BrokerId < 1000000000),
PRIMARY KEY (AccountId, BrokerId, TransactionId, OrderId, StockId),
FOREIGN KEY (AccountID) REFERENCES Account (Id)
  ON DELETE NO ACTION
  ON UPDATE CASCADE,
FOREIGN KEY (BrokerId) REFERENCES Employee (Id)
  ON DELETE NO ACTION
  ON UPDATE CASCADE,
FOREIGN KEY (TransactionID) REFERENCES Transaction (Id)
  ON DELETE NO ACTION
  ON UPDATE CASCADE,
FOREIGN KEY (OrderId) REFERENCES Orders (Id)
  ON DELETE NO ACTION
  ON UPDATE CASCADE,
FOREIGN KEY (StockId) REFERENCES Stock (StockSymbol)
  ON DELETE NO ACTION
  ON UPDATE CASCADE );

#realations
CREATE TABLE HasStock(
  StockID CHAR(20),
  AccountID INTEGER,
  NumberOfShare INTEGER,
  PRIMARY KEY (AccountID,StockID),
  FOREIGN KEY (AccountID) REFERENCES Account (Id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  FOREIGN KEY (StockId) REFERENCES Stock (StockSymbol)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

show tables;
SET FOREIGN_KEY_CHECKS  = 1;