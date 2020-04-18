import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class account
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
	public static void login()
	{
		/*
		 * front end stuff
		 */
		System.out.print("Account: ");
		s = new Scanner(System.in);
		String username = s.nextLine();
		System.out.print("Password: ");
		s = new Scanner(System.in);
		String password = s.nextLine();
		
		
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=q3k82018&useSSL=false&useLagacyDatetimeCode=false&serverTimezone=UTC");
			st = conn.createStatement();
			
			
			// check if user exists at all
			ArrayList<String> accounts = new ArrayList<String>();
			String s1 = "SELECT userID FROM UserAccount";
			ps = conn.prepareStatement(s1);
			rs = ps.executeQuery(s1);
			while(rs.next())
			{
				accounts.add(rs.getString("userID"));
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
				s1 = "SELECT userPW FROM UserAccount WHERE userID = " + username;
				ps = conn.prepareStatement(s1);
				rs = ps.executeQuery(s1);
				
				rs.next();
				String pw_actual = rs.getString("userPW");
				
				
				/*
				 * front end stuff
				 */
				// see if the password matches
				while (password.equals(pw_actual) == false)
				{
					
					System.out.println("Password incorrect");
					System.out.print("Password: ");
					s = new Scanner(System.in);
					password = s.nextLine();
				}
				
				System.out.print("Logged in successfully");
			}
			
			
		}
		catch (SQLException sqle) 
		{
			System.out.println("Exception in connecting to database: " + sqle.getMessage());
		}
	}
	
	
	
	
	public static void createAccount(int userID, String userEmail, String userPW)
	{
		/*
		 * insert front end stuff here
		 */
		Database.addUser(userID, userEmail, userPW);
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