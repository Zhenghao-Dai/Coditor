DROP DATABASE IF EXISTS FinalProject;
CREATE DATABASE FinalProject;

USE FinalProject;
CREATE TABLE Document (
	-- DOCUMENT TABLE --
	-- will hold all the documents and their associated host, documents are identified by ID number, name, and host --
	docID INT(11) PRIMARY KEY NOT NULL,
    docName VARCHAR(50) NOT NULL,
    docHost INT(11) NOT NULL -- host will correspond to ID number --
); 

CREATE TABLE UserAccount (
	-- ACCOUNT TABLE --
    -- will hold all user accounts and associated information, hosts identified by ID number, email, and hashed password --
	userID INT(11) PRIMARY KEY NOT NULL,
    userEmail VARCHAR(100) NOT NULL,
    userPW VARCHAR(100) NOT NULL
);

CREATE TABLE Master (
	-- MASTER TABLE --
    -- will hold every document and every user that has access to that document in a new row --
		-- note: order may not be sequential --
	docID INT (11) NOT NULL,
    userID INT(11) NOT NULL
);

INSERT INTO Document (docID, docName, docHost) 
	VALUE (0, "testDoc", 0);
            
INSERT INTO UserAccount (userID, userEmail, userPW)
	VALUE (0, "testEmail@gmail.com", "test0_pw");

INSERT INTO Master (docID, userID) 
	VALUE (0, 0);
    
