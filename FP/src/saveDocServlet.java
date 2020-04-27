

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Base64;

@WebServlet("/saveDocServlet")
public class saveDocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Connection conn = null; 
	static Statement st = null;
	static ResultSet rs = null; 
	static PreparedStatement ps = null; 
	
    public saveDocServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = (String) request.getParameter("editor1");
		String docID = (String) request.getParameter("docID");
		
		//encode content so that it can be saved in database (make sure to import java.util.Base64)
		content = Base64.getEncoder().withoutPadding().encodeToString(content.getBytes());
		
		//use this to decode the encoded string after retrieving from database
		//byte[] decodedBytes = Base64.getDecoder().decode(content);
		//String decodedString = new String(decodedBytes); //this gives the original content of the string
				
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=root&useSSL=false&useLagacyDatetimeCode=false&serverTimezone=UTC");
			
			st = conn.createStatement();
			char c = '"';
			System.out.println("saveDocServlet - storing content: " + content);
			System.out.println("saveDocServlet - docID: "  + docID);
			ps = conn.prepareStatement("UPDATE Document SET content=" + c + content + c + "WHERE docID=" + docID + ";");
			ps.execute();
						
		} catch (SQLException | ClassNotFoundException sqle) {
			System.out.println("Exception in saving to database: " + sqle.getMessage());
		}
		
		String next = "/drive.jsp";
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
		dispatch.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
