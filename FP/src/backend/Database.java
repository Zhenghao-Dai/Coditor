package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Database {
	static Connection conn = null; //how you make the connection
	static Statement st = null; //the actual statement
	static ResultSet rs = null; //what comes back from a select statement
	static PreparedStatement ps = null; //used to send insert statements to db
	static Vector<String> UserEmails = new Vector<String>();
	static Vector<Integer> DocTags = new Vector<Integer>();
	static String databaseUserName = "root";
	static String databasePasswordString = "root";
	static Vector<Integer> validDocIDs = new Vector<Integer>();
	static int docCounter = 0;
	static Vector<Integer> validUserIDs = new Vector<Integer>();
	static int userCounter = 0;
	
	public static void initializeIDs() {
		for (int i = userCounter; i < 1000; i++) {
			validUserIDs.add(i);
		}
		
		for (int i = docCounter; i < 1000; i++) {
			validDocIDs.add(i);
		}
		
	}
	
	public static boolean addNewUser(String userEmail, String userPW) {
		
		int userID = validUserIDs.get(0);
		userCounter++;
		validUserIDs.remove(0);
		if (!userExists(userEmail))	{
			String insert_pw = hashed(userPW);
			char ch = '"';
			String s1 = "INSERT INTO UserAccount (userID, userEmail, userPW) VALUES (" + userID + ", " + ch + userEmail + ch + ", " + ch + insert_pw + ch + ");";
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
				st = conn.createStatement();
				ps = conn.prepareStatement(s1);
				ps.execute();
				return true;
			} catch (SQLException | ClassNotFoundException e) {
				System.err.println("Error adding user: " + e.getMessage());
				return false;
			}
		}
			
			return false;
	}
	
	//deletes user from all shared databases, also deletes all documents that this user is the host of
	public static boolean removeUser(String email) {
		if (!userExists(email))
			return false;
		
		int userID = getUserID(email);
		List<Integer> removeDocs = new ArrayList<Integer>();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
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
//				System.err.println("Remove doc with id: " + i);
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
			System.err.println("Error in removing a user: " + sqle.getMessage());
			return false;
		}
		return true;
	}
	
	public static String hashed(String pw) { //function to hash the given password		
		return pw;
	}
	
	public static boolean createNewDocument(String docName, int docHost) {
		
		char ch = '"';
		try {
			int docID = getDocID(docName);
			if (docID == -1) {
				docID = validDocIDs.get(0);
				validDocIDs.remove(0);
			}
			docCounter++;
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			String s1 = "INSERT INTO Document (docID, docName, docHost, content) VALUES (" + docID + ","+ ch + docName + ch + ", " + docHost + "," + ch + " " + ch + ");";
			ps = conn.prepareStatement(s1);
			ps.execute();
			
			String s2 = "INSERT INTO Master (docID, userID) VALUES (" + docID + "," + docHost + ");";
			ps = conn.prepareStatement(s2);
			ps.execute();
		} catch (SQLException e) {
			System.err.println("Error adding document: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public static boolean removeDocument(String docName) { //deletes a single document, removes it from all shared users' drives
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
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
				return false;
			}
			
		} catch (SQLException sqle) {
			System.err.println("Error in removing a user: " + sqle.getMessage());
			return false;
		}
		return true;
	}
	
	//adds specified user to list of valid users for a particular document
	public static boolean shareDocument(String docName, String email) {
		int docID = getDocID(docName);
		int userID = getUserID(email);
		String s = "INSERT INTO Master (docID, userID) VALUES (" + docID + "," + userID + ");";
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			ps = conn.prepareStatement(s);
			ps.execute();
		} catch (SQLException sqle) {
			System.out.println("Error sharing document: " + sqle.getMessage());
			return false;
		}
		return true;
	}
	
	public static boolean verifyUserDocAccess(String docName, String email) { //checks whether or not this user has access to a document
		int docID = getDocID(docName);
		int userID = getUserID(email);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT docID FROM Master WHERE userID =" + userID);
			while (rs.next()) {
				String temp = rs.getString("docID");
				if (Integer.parseInt(temp) == docID) {
					return true;
				}
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
			return false;
		} catch (SQLException e) {
			System.err.println ("SQLException in docExists(): " + e.getMessage());
			return false;
		}
		return false;
	}

	private static boolean docExists(int DocID) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT docID FROM Document");
			while (rs.next()) {
				String temp = rs.getString("docID");
				int nextID = Integer.parseInt(temp);
				if (nextID == DocID) { 
					return true;
				}
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
			return false;
		} catch (SQLException e) {
			System.err.println ("SQLException in docExists(): " + e.getMessage());
			return false;
		}
		return false;
	}
	
	public static boolean userExists(String email) {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
		
		st = conn.createStatement();
		// check if user exists at all
		ArrayList<String> accounts = new ArrayList<String>();
		String s1 = "SELECT userEmail FROM UserAccount";
		ps = conn.prepareStatement(s1);
		rs = ps.executeQuery(s1);
		while(rs.next())
		{
		accounts.add(rs.getString("userEmail"));
		}

		// if user doesn't exist
		if (accounts.contains(email))
		{
			return true;
		}
		else {
			return false;
		}
		}
		catch (Exception e) {
		// TODO: handle exception
			System.err.println("error in userExists(): " + e.getMessage());
		}
		return false;
	}
	
	
	
	static int getUserID(String email) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString  + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
			char ch = '"';
			if (st == null) {
				System.out.println("st is null");
			}
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
	
	public static int getDocID(String docName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString  + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// returns vector of docIDs of documents that specified userEmail has access to 
	static Vector<Integer> getUsersDocIDs(String userEmail) { 
		int userID = getUserID(userEmail);
		Vector<Integer> v = new Vector<Integer>();
		try { 
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString  + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT docID FROM Master WHERE userID = " + userID);
			while (rs.next()) {
				String temp = rs.getString("docID");
				v.add(Integer.parseInt(temp));
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in getUsersDoc(): " + e.getMessage());
		}
		return v;
	}
	
	// returns vector of docNames of documents that specified userEmail has access to 
	static Vector<String> getUsersDocNames(String userEmail) {
		Vector<String> docNames = new Vector<String>();
		Vector<Integer> docIDs = new Vector<Integer>();
		try { 
			docIDs = getUsersDocIDs(userEmail);
			for (int i = 0; i < docIDs.size(); i++) {
				rs = st.executeQuery("SELECT docName FROM Document WHERE docID = " + docIDs.get(i));
				while (rs.next()) {
					docNames.add(rs.getString("docName"));
				}
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in getUsersDoc(): " + e.getMessage());
		}
		return docNames;
	}
	
	// returns vector of docIDs of documents that specified userEmail owns
	static Vector<Integer> getUsersOwnedDocIDs(String userEmail) { 
		int userID = getUserID(userEmail);
		Vector<Integer> v = new Vector<Integer>();
		try { 
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString  + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT docID FROM Document WHERE docHost = " + userID);
			while (rs.next()) {
				String temp = rs.getString("docID");
				v.add(Integer.parseInt(temp));
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in getUsersDoc(): " + e.getMessage());
		}
		return v;
	}
		
	// returns vector of docNames of documents that specified userEmail owns 
	static Vector<String> getUsersOwnedDocNames(String userEmail) {
		Vector<String> docNames = new Vector<String>();
		Vector<Integer> docIDs = new Vector<Integer>();
		try { 
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user="+databaseUserName+"&password="+databasePasswordString  + "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
			docIDs = getUsersOwnedDocIDs(userEmail);
			for (int i = 0; i < docIDs.size(); i++) {
				rs = st.executeQuery("SELECT docName FROM Document WHERE docID = " + docIDs.get(i));
				while (rs.next()) {
					docNames.add(rs.getString("docName"));
				}
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in getUsersDoc(): " + e.getMessage());
		}
		return docNames;
	}
	
	public boolean databaseUseComplete() { //only use when we are completely done and ready to close database connection
		try {
			st.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Exception with closing the database");
			return false;
		}
	}
}