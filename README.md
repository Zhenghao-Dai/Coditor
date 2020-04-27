# CODITOR

**To use our project**
1. To deploy the web server, download Tomcat v9.0 
2. Download Coditor.zip
3. Unzip Coditor.zip
4. Download SQL script and execute it
5. Create a Dynamic Web Project in Eclipse with any name
6. Import unzipped Coditor project file into newly created Dynamic Web Project
7. Change variable databasePasswordString and databaseUserName in Database.java to your SQL username and password 
8. Run `login.jsp` on Tomcat v9.0 server

**Working Functionality**
1) 	User Login/Sign Up/Logout
2)	Creating/Editing/Saving/Sharing/Deleting Document

**Working to Fix**
1) 	Users who are not the host of a document can delete that document 
2) 	Creating global variables for Database username and password so that all files can use it 

**Note**
1)	Change port number on share link to match whichever port number your machine will be running tomcat server from 
shareServlet.java - line 48
