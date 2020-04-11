package BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.AccessLevelOutOfBoundsException;
import util.DocumentNotFound;

public class AccountFunctionality {
	
	private String email;
	private int userID;
	
	private static Connection conn = null;
	private static Statement st = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	public AccountFunctionality() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/NameOfAccountDatabase?user=root&password=root");
	}
	
	public void createNewDoc() {
		try {
			// inserting new document
			ps = conn.prepareStatement("INSERT INTO NameOfUser'sDocumentsTable () VALUES ()");
			rs = ps.executeQuery();
			// inserting userIDs of all sharedUsers
			ps = conn.prepareStatement("INSERT INTO NameOfDocument'sSharedUsersTable () VALUES ()");
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			System.err.println ("SQLException in createNewDoc(): " + sqle.getMessage());
		}
	}
	
	public void deleteDoc(int DocID) {
		try {
			// extracting entire list of current documents in user
			ps = conn.prepareStatement("SELECT ListOfDocumentIDs FROM NameOfUser'sDocumentsTable");
			rs = ps.executeQuery();
			
			// checks to see if document exists
			if (!docExists(DocID))
				return;
			
			// deleting document
			ps = conn.prepareStatement("DELTE FROM NameOfUser'sDocumentsTable WHERE DocID = DocID");
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			System.err.println ("SQLException in deleteDoc(): " + sqle.getMessage());
		} catch (DocumentNotFound dnf) {
			System.err.println (dnf.getMessage());
		}
	}
	
	private boolean docExists(int DocID) throws DocumentNotFound {
		boolean docIDFound = false;
		int docNotFoundID = 0;
		try {
			while (rs.next()) {
				String temp = rs.getString("DocID");
				int nextID = Integer.parseInt(temp);
				if (nextID == DocID) {
					docIDFound = true;
					docNotFoundID = nextID;
				}
			}
		} catch (NumberFormatException e) {
			System.err.println ("NumberFormatException in docExists(): " + e.getMessage());
		} catch (SQLException e) {
			System.err.println ("SQLException in docExists(): " + e.getMessage());
		}
		if (!docIDFound) 
			throw new DocumentNotFound(docNotFoundID);
		else 
			return docIDFound;
	}
	
	public void shareDoc(int DocID, ArrayList<Integer> users, ArrayList<Integer> accessLevels) {
		
		/**
		 * accessLevel = 0; View
		 * accessLevel = 1; Comment
		 * accessLevel = 2; Edit
		 */
		try {
			for (int i = 0; i < users.size(); i++) {
				int temp = users.get(i);
				String userID = String.valueOf(temp);
				int temp1 = accessLevels.get(i);
					if (temp1 < 0 || temp1 > 2)
						throw new AccessLevelOutOfBoundsException("Access level integer out of bounds");
				String access = String.valueOf(temp1);
				ps = conn.prepareStatement("INSERT INTO NameOfDocument'sSharedUsersTable (userID, accessLevel) "
						+ "VALUES (" + userID + ", + " + access + ")");
				rs = ps.executeQuery();
			}
		} catch (SQLException sqle) {
			System.err.println ("SQLException in shareDoc(): " + sqle.getMessage());
		} catch (AccessLevelOutOfBoundsException e) {
			System.err.println (e.getMessage());
		}
	}
}
