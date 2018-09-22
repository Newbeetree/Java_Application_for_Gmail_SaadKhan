CREATE DATABASE IF NOT EXISTS JAG;

USE JAG;

DROP TABLE IF EXISTS Attachments;
DROP TABLE IF EXISTS EmailBeanAdresses;
DROP TABLE IF EXISTS EmailAddresses;
DROP TABLE IF EXISTS Folder;
DROP TABLE IF EXISTS EmailBean;

CREATE TABLE EmailBean (
  Bean_Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Email_From VARCHAR(50),
  Email_Subject VARCHAR(100),
  Message TEXT,
  HTML TEXT,
  Send_Date DateTime,
  Receive_Date DateTime,
  Priority INT(1),
  Folder_Id Int(10)
);

CREATE TABLE Attachments (
  Attach_Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Email_Id INT,
  File_Name VARCHAR(20),
  File_Attach BLOB,
  File_Type BOOL,
  FOREIGN KEY (Email_Id) REFERENCES EmailBean(Bean_Id) ON DELETE CASCADE
);


CREATE TABLE EmailAddresses (
  Email_Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(30),
  Address VARCHAR(50)
);

CREATE TABLE Folder (
  Folder_Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Folder_Name VARCHAR(100)
);

CREATE TABLE EmailBeanAdresses (
  Bean_Id INT(10),
  Email_Id INt(10),
  Email_Type VARCHAR(4),
  FOREIGN KEY (Bean_Id) REFERENCES EmailBean (Bean_Id) ON DELETE CASCADE,
  FOREIGN KEY (Email_Id) REFERENCES EmailAddresses (Email_Id) ON DELETE CASCADE
);

