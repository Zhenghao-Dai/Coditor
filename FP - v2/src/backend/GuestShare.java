package backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/guestshare")
public class GuestShare extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GuestShare() {
        super();
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("docId");
        String password = request.getParameter("password");
        
		String destPage = "doc.jsp";
		//Set div = innerhtml
		
		String docID = (String) request.getAttribute("docID");
		if (docID == null) {
			docID = "";
		}
		
		String docName = (String) request.getAttribute("docName");
		if (docName == null) {
			docName = "";
		}
		
		String content = (String) request.getAttribute("content");
		if (content == null) {
			content = "";
		}
		
		Connection conn = null; //how you make the connection
		Statement st = null; //the actual statement
		PreparedStatement ps = null;
		ResultSet rs = null; //what comes back from a select statement
		
		String decodedString = "";
		try {
			//connect to database
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=root");
			st = conn.createStatement();
			char c = '"';
			rs = st.executeQuery("SELECT content FROM Document WHERE docID = " + docID);
			
			//get the plain text formatted content for this document
			while (rs.next()) {		
				content = rs.getString(1);//gets actual string from database
			}
			
			if (!(content.equals(" "))) { //if the database content is not empty 	
				byte[] decodedBytes = Base64.getDecoder().decode(content);
				decodedString = new String(decodedBytes); //this gives the original content of the string
				content = decodedString;
			}
			//by here, if we entered  the if above then 'content' has the actual html with the tags, for
			//bold, italics, underline, code, etc
			
			
		} catch (SQLException | ClassNotFoundException sqle) {
			System.out.println("Exception in pulling data from database: " + sqle.getMessage());
		}
		
		//destPage.redirect to 
		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
    }
}