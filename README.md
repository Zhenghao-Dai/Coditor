CODITOR - READEME.txt

To use our project:
Step 1: To deploy the web server, download Tomcat v9.0 
Step 2: Download Coditor.zip
Step 3: Unzip Coditor.zip
Step 4: Download SQL script and execute it
Step 5: Create a Dynamic Web Project in Eclipse with any name
Step 6: Import unzipped Coditor project file into newly created Dynamic Web Project
Step 7: Change variable databasePasswordString and databaseUserName in Database.java to your SQL username and password 
Step 8: Run on ‘login.jsp’ tomcat server

Working Functionality:
1) 	User Login/Sign Up/Logout
2)	Creating/Editing/Saving/Sharing/Deleting Document

Working to Fix:
1) 	Users who are not the host of a document can delete that document 

**Note**
1)	Change port number on share link to match whichever port number your machine will be running tomcat server from 
shareServlet.java - line 48
