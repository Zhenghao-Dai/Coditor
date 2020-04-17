import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
	static Connection conn = null; //how you make the connection
	static Statement st = null; //the actual statement
	static ResultSet rs = null; //what comes back from a select statement
	static PreparedStatement ps = null; //used to send insert statements to db
	
	public static void main (String [] args) {
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=root");
			st = conn.createStatement();
		} catch (SQLException sqle) {
			System.out.println("Exception in connecting to database: " + sqle.getMessage());
		}
		
		// addUser(1, "insertTest", "badpw");
		// addDocument(4, "testadd", 1);
		// addDocument(3, "add2", 1);
		// addDocument(5, "add3", 1);
		// removeUser(1);
		// addUser(32, "testEmail@gmail.com", "1231");	
		// shareDocument(11, 2);
		// docExists(4);
		// docExists(6);
		// verifyUser(4, 1);
		// verifyUser(4, 0);
		// removeDocument(4);
	}
	
	public static void addUser(int userID, String userEmail, String userPW) {
		String insert_pw = hashed(userPW);
		char ch = '"';
		String s1 = "INSERT INTO UserAccount (userID, userEmail, userPW) VALUES (" + userID + ", " + ch + userEmail + ch + ", " + ch + insert_pw + ch + ");";
		try {
			ps = conn.prepareStatement(s1);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("Error adding user: " + e.getMessage());
		}	
	}
	
	public static void removeUser(int userID) { //deletes user from all shared databases, also deletes all documents that this user is the host of 
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
	
	public static void addDocument(int docID, String docName, int docHost) {
		char ch = '"';
		String s1 = "INSERT INTO Document (docID, docName, docHost) VALUES (" + docID + ", " + ch + docName + ch + ", " + docHost + ");";
		String s2 = "INSERT INTO Master (docID, userID) VALUES (" + docID + "," + docHost + ");";
		try {
			ps = conn.prepareStatement(s1);
			ps.execute();
			
			ps = conn.prepareStatement(s2);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("Error adding document: " + e.getMessage());
		}
	}

	public static void removeDocument(int docID) { //deletes a single document, removes it from all shared users' drives
		try {
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
	
	public static void shareDocument(int docID, int userID) {//adds specified user to list of valid users for a particular document
		String s = "INSERT INTO Master (docID, userID) VALUES (" + docID + "," + userID + ");";
		try {
			ps = conn.prepareStatement(s);
			ps.execute();
		} catch (SQLException sqle) {
			System.out.println("Error sharing document: " + sqle.getMessage());
		}
	}
	
	public static boolean verifyUser(int docID, int userID) { //checks whether or not this user has access to a document
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
	
	public void databaseUseComplete() { //only use when we are completely done and ready to close database connection
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println("Exception with closing the database");
		}
	}
}
