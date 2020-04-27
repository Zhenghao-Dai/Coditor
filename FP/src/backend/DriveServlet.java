package backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DriveServlet")
public class DriveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String credentials_string = "jdbc:mysql://localhost/FinalProject?user=root&password=root&useSSL=false&useLagacyDatetimeCode=false&serverTimezone=UTC";
	static Connection conn = null; //how you make the connection
	static Statement st = null; //the actual statement
	static PreparedStatement ps = null;
	static ResultSet rs = null; //what comes back from a select statement
	Vector<String> myDocs = new Vector<String>();
	Vector<String> sharedDocs = new Vector<String>();
	Vector<Integer> myDocsID = new Vector<Integer>();
	Vector<Integer> sharedDocsID = new Vector<Integer>();
	
	
	
	String userEmail = "";
       
    public DriveServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		userEmail = (String)session.getAttribute("email");
		
		try {	
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(credentials_string);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			loadDocs(userEmail);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		session.setAttribute("myDocs", myDocs);
		session.setAttribute("sharedDocs", sharedDocs);
		session.setAttribute("myDocsID", myDocsID);
		session.setAttribute("sharedDocsID", sharedDocsID);
		
		//nextPage.redirect to 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/drive.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void loadDocs(String email) throws SQLException {
			
		 myDocs = Database.getUsersOwnedDocNames(email); 	 
		 sharedDocs = Database.getUsersDocNames(email);
		 myDocsID = Database.getUsersOwnedDocIDs(email);
		 sharedDocsID = Database.getUsersDocIDs(email);
		
	}

}