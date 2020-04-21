package BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Database {
	static Connection conn = null; //how you make the connection
	static Statement st = null; //the actual statement
	static ResultSet rs = null; //what comes back from a select statement
	static PreparedStatement ps = null; //used to send insert statements to db
	static Vector<String> UserEmails;
	
	public static void main (String [] args) {
		UserEmails = new Vector<String>();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=root");
			st = conn.createStatement();
		} catch (SQLException sqle) {
			System.out.println("Exception in connecting to database: " + sqle.getMessage());
		}
		
		addNewUser("abc@gmail.com", "password1");
		addNewUser("123456@gmail.com", "password1");
		addNewUser("ivanpeng@gmail.com", "password1");
		addNewUser("xyz@gmail.com", "password 32");
//		System.out.println(getUserID("abc@gmail.com"));
		createNewDocument("testDoc 1", getUserID("abc@gmail.com"));
		createNewDocument("testDoc 2", getUserID("123456@gmail.com"));
		createNewDocument("testDoc 3", getUserID("ivanpeng@gmail.com"));
		createNewDocument("testDoc 4", getUserID("xyz@gmail.com"));
		removeUser("xyz@gmail.com");
		removeDocument("testDoc 3");
	} 
	
	public static void addNewUser(String userEmail, String userPW) {
			String insert_pw = hashed(userPW);
			char ch = '"';
			String s1 = "INSERT INTO UserAccount (userEmail, userPW) VALUES (" + ch + userEmail + ch + ", " + ch + insert_pw + ch + ");";
			
			try {
				ps = conn.prepareStatement(s1);
				ps.execute();
			} catch (SQLException e) {
				System.err.println("Error adding user: " + e.getMessage());
			}
			if (!userExists(userEmail))
				UserEmails.add(userEmail);
	}
	
	//deletes user from all shared databases, also deletes all documents that this user is the host of
	public static void removeUser(String email) {
		if (!userExists(email))
			return;
		
		int userID = getUserID(email);
		List<Integer> removeDocs = new ArrayList<Integer>();
		try {
			rs = st.executeQuery("SELECT docID FROM Document WHERE docHOST = " + userID);
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
		           	String columnValue = rs.getString(i);
		           	removeDocs.add(Integer.parseInt(columnValue));
			       }
			   }
			
			for (Integer i : removeDocs) {
				System.out.println("Remove doc with id: " + i);
				String r = "DELETE FROM Master WHERE docID =" + i + ";";
				ps = conn.prepareStatement(r);
				ps.execute();
			}
			
			String r = "DELETE FROM Document WHERE docHost =" + userID + ";";
			ps = conn.prepareStatement(r);
			ps.execute();
			
			r = "DELETE FROM UserAccount WHERE userID =" + userID + ";";
			ps = conn.prepareStatement(r);
			ps.execute();
			
			
			
		} catch (SQLException sqle) {
			System.out.println("Error in removing a user: " + sqle.getMessage());
		}
	}
	
	public static String hashed(String pw) { //function to hash the given password		
		return pw;
	}
	
	public static void createNewDocument(String docName, int docHost) {
		char ch = '"';
		
		try {
			String s1 = "INSERT INTO Document (docName, docHost) VALUES (" + ch + docName + ch + ", " + docHost + ");";
			ps = conn.prepareStatement(s1);
			ps.execute();
			
			int docID = getDocID(docName);
			String s2 = "INSERT INTO Master (docID, userID) VALUES (" + docID + "," + docHost + ");";
			ps = conn.prepareStatement(s2);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("Error adding document: " + e.getMessage());
		}
	}
	
	public static void removeDocument(String docName) { //deletes a single document, removes it from all shared users' drives
		try {
			int docID = getDocID(docName);
			if (docExists(docID)) {
				String r = "DELETE FROM Document WHERE docID =" + docID + ";";
				ps = conn.prepareStatement(r);
				ps.execute();
				r = "DELETE FROM Master WHERE docID =" + docID + ";";
				ps = conn.prepareStatement(r);
				ps.execute();
			}
			else {
				System.err.println("Can't remove Document " + docID + " because it doesn't exist.");
			}
			
		} catch (SQLException sqle) {
			System.out.println("Error in removing a user: " + sqle.getMessage());
		}
	}
	
	//adds specified user to list of valid users for a particular document
	public static void shareDocument(String docName, String email) {
		int docID = getDocID(docName);
		int userID = getUserID(email);
		String s = "INSERT INTO Master (docID, userID) VALUES (" + docID + "," + userID + ");";
		try {
			ps = conn.prepareStatement(s);
			ps.execute();
		} catch (SQLException sqle) {
			System.out.println("Error sharing document: " + sqle.getMessage());
		}
	}
	
	public static boolean verifyUserDocAccess(String docName, String email) { //checks whether or not this user has access to a document
		int docID = getDocID(docName);
		int userID = getUserID(email);
		try {
			rs = st.executeQuery("SELECT docID FROM Master WHERE userID =" + userID);
			while (rs.next()) {
				String temp = rs.getString("docID");
				if (Integer.parseInt(temp) == docID) {
					System.err.println("User " + userID + " has access to Document " + docID);
					return true;
				}
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in docExists(): " + e.getMessage());
		}
		System.err.println("User " + userID + " does not have access to Document " + docID);
		return false;
	}

	private static boolean docExists(int DocID) {
		try {
			rs = st.executeQuery("SELECT docID FROM Document");
			while (rs.next()) {
				String temp = rs.getString("docID");
				int nextID = Integer.parseInt(temp);
				if (nextID == DocID) { 
					System.err.println("Document " + DocID + " found!");
					return true;
				}
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in docExists(): " + e.getMessage());
		}
		System.err.println("Document " + DocID + " not found!");
		return false;
	}
	
	private static boolean userExists(String email) {
		Collections.sort(UserEmails);
		if (Collections.binarySearch(UserEmails, email) >= 0)
			return true;
		else {
//			System.err.println("User with email: " + email + " does not exist!");
			return false;
		}
	}
	
	private static int getUserID(String email) {
		try {
			char ch = '"';
			rs = st.executeQuery("SELECT userID FROM UserAccount WHERE userEmail = " + ch + email + ch);
			while (rs.next()) {
				String temp = rs.getString("userID");
				return Integer.parseInt(temp);
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in getUserID(): " + e.getMessage());
		}
		return -1;
	}
	
	private static int getDocID(String docName) {
		try {
			char ch = '"';
			rs = st.executeQuery("SELECT docID FROM Document WHERE docName = " + ch + docName + ch);
			while (rs.next()) {
				String temp = rs.getString("docID");
				return Integer.parseInt(temp);
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in getDocID(): " + e.getMessage());
		}
		return -1;
	}
	
	public void databaseUseComplete() { //only use when we are completely done and ready to close database connection
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println("Exception with closing the database");
		}
	}
}
