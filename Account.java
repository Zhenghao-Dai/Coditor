package coditor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Account
{
	static Scanner s = new Scanner(System.in);
	static Connection conn = null; //how you make the connection
	static Statement st = null; //the actual statement
	static PreparedStatement ps = null;
	static ResultSet rs = null; //what comes back from a select statement
	
	
	
	public static void main (String [] args)
	{
		// login();
		// guestAccess(0);
		
		
	}
	public boolean login(String username,String password) throws ClassNotFoundException 
	{
		/*
		 * front end stuff
		 */
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=q82070002");
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
			if (!accounts.contains(username))
			{
				/*
				 * front end stuff
				 */
				System.out.println("User does not exist. Please create account.");
			}
			else
			{
				s1 = "SELECT userPW FROM UserAccount WHERE userEmail = ?";
				ps = conn.prepareStatement(s1);
				ps.setString(1,username);
				rs = ps.executeQuery();
				
				rs.next();
				String pw_actual = rs.getString("userPW");
				
				
				/*
				 * front end stuff
				 */
				// see if the password matches
				if (password.equals(pw_actual))
				{
					return true;
				}
				else {
					return false;
				}
				
				
			}
			
			
		}
		catch (SQLException sqle) 
		{
			System.out.println("Exception in connecting to database: " + sqle.getMessage());
		}
		return false;
	}
	
	
	
	
	public static void createAccount(int userID, String userEmail, String userPW) throws SQLException
	{
		/*
		 * insert front end stuff here
		 */
		
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
		if (accounts.contains(userEmail))
		{
			/*
			 * front end stuff
			 */
			System.out.println("User exists already.");
		}
		else
		{
			Database.addUser(userID, userEmail, userPW);
		}
	}
	
	
	
	
	public static void guestAccess(int docID)
	{
		// Guest access to invited documents (read only) via email link
		/*
		 *  front end stuff
		 */
		
		
		// Viewing userIDs: set of all users that have access to the document
		
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=q3k82018&useSSL=false&useLagacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
			
			
			// check if user exists at all
			ArrayList<String> accounts = new ArrayList<String>();
			String s1 = "SELECT userID FROM Master WHERE docID = " + docID;
			ps = conn.prepareStatement(s1);
			rs = ps.executeQuery(s1);
			while(rs.next())
			{
				accounts.add(rs.getString("userID"));
			}
			
			/*
			 * front end stuff
			 */
			for (int i = 0; i < accounts.size(); i++)
			{
				System.out.println(accounts.get(i));
			}
			
		}
		catch (SQLException sqle) 
		{
			System.out.println("Exception in connecting to database: " + sqle.getMessage());
		}
		
		
		
	
	}
	
	
	
	
}
