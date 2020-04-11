-- document and account databases --

DROP DATABASE IF EXISTS FinalProject; -- should i have this line? i remember prof. saying it super dangeorus -- 
CREATE DATABASE FinalProject;

USE FinalProject;
CREATE TABLE Document (
	docID int(11) PRIMARY KEY NOT NULL,
    docName VARCHAR(50) NOT NULL,
    docHost int(11) NOT NULL -- host will correspond to ID number --
    -- store a list of userIDs -- 
); 

CREATE TABLE UserAccount (
	userID int(11) PRIMARY KEY NOT NULL,
    userEmail VARCHAR(100) NOT NULL,
    userPW VARCHAR(100) NOT NULL
    -- store a list of documentIDs --
);

INSERT INTO Document (docID, docName, docHost) 
	VALUES (1, "test1", 3),
			(2, "test2", 2);
            
INSERT INTO UserAccount (userID, userEmail, userPW)
	VALUES (1, "email1", "user1pw"),
			(2, "email2", "user2pw"),
            (3, "email3", "user3pw");

-- workaround to storing for each user what documents they have access to and for each document which users have accesss to that document -- 
CREATE TABLE Access (
	docID int (11) NOT NULL,
    userID int (11) NOT NULL,
    FOREIGN KEY fk1(docId) REFERENCES Document(docID),
    FOREIGN KEY fk2(userID) REFERENCES UserAccount(userID)    
);
