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



@WebServlet("/DocumentServlet")
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null; 
	private Statement st = null;
	private ResultSet rs = null; 
	private PreparedStatement ps = null;
	String content = "";   
	
    public DocumentServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the id of the document being opened
		//get the name of the document being opened
		String docID = request.getParameter("docID");
		String docName = request.getParameter("docName");
		
		try {
			//connect to database
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=root&useSSL=false&useLagacyDatetimeCode=false&serverTimezone=UTC");
			
			
			st = conn.createStatement();
			char c = '"';
			rs = st.executeQuery("SELECT content FROM Document WHERE docID = " + docID);
			
			//get the plain text formatted content for this document
			while (rs.next()) {		
				content = rs.getString(1);
			}
			System.out.println("documentServlet - Content: " + content);
		} catch (SQLException | ClassNotFoundException sqle) {
			System.out.println("DocumentServlet - Exception in pulling data from database: " + sqle.getMessage());
		} 
		
		//encode the plain text content
		content = Base64.getEncoder().withoutPadding().encodeToString(content.getBytes());
		byte[] decodedBytes = Base64.getDecoder().decode(content);
		String decodedString = new String(decodedBytes);
		
		//set the attributes of the request to hold docId, name, and content
		request.setAttribute("docID", docID);
		request.setAttribute("content", content);
		request.setAttribute("docName", docName);
		
		//send to document.jsp with correct data 
		String next = "/document.jsp";
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
		dispatch.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}