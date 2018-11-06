
DROP TABLE IF EXISTS Attachments;
DROP TABLE IF EXISTS EmailBeanAdresses;
DROP TABLE IF EXISTS EmailBean;
DROP TABLE IF EXISTS EmailAddresses;
DROP TABLE IF EXISTS Folder;

CREATE TABLE Folder (
  Folder_Id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Folder_Name VARCHAR(100) NOT NULL
);

CREATE TABLE EmailAddresses (
  Email_Id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Name     VARCHAR(30),
  Address  VARCHAR(100) NOT NULL
);

CREATE TABLE EmailBean (
  Bean_Id       INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Email_From    INT      NOT NULL,
  Email_Subject VARCHAR(100),
  Message       TEXT,
  HTML          TEXT,
  Send_Date     DateTime NOT NULL,
  Receive_Date  DateTime NOT NULL,
  Priority      INT      NOT NULL,
  Folder_Id     INT      NOT NULL,
  FOREIGN KEY (Email_From) REFERENCES EmailAddresses (Email_Id)
  ON DELETE CASCADE,
  FOREIGN KEY (Folder_Id) REFERENCES Folder (Folder_Id)
  ON DELETE CASCADE
);

CREATE TABLE Attachments (
  Attach_Id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Email_Id    INT         NOT NULL,
  File_Name   VARCHAR(200) NOT NULL,
  File_Attach LONGBLOB,
  File_Type   BOOL        NOT NULL,
  FOREIGN KEY (Email_Id) REFERENCES EmailBean (Bean_Id)
  ON DELETE CASCADE
);


CREATE TABLE EmailBeanAdresses (
  Bean_Id    INT        NOT NULL,
  Email_Id   INT        NOT NULL,
  Email_Type VARCHAR(4) NOT NULL,
  FOREIGN KEY (Bean_Id) REFERENCES EmailBean (Bean_Id)
  ON DELETE CASCADE,
  FOREIGN KEY (Email_Id) REFERENCES EmailAddresses (Email_Id)
  ON DELETE CASCADE
);

