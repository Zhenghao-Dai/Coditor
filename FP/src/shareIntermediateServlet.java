import java.io.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class shareIntermediateServlet
 */
@WebServlet("/shareIntermediateServlet")
public class shareIntermediateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public shareIntermediateServlet() {
        super();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
        String docName = request.getParameter("docName");
        
        
        String destPage = "share.jsp";
        System.out.println("document name in shareIntemediate: " + docName);
        
        //String userlists = "" + "\n";
        //System.out.println(userlists);
        
        request.setAttribute("docName", docName);
        //request.setAttribute("userlists", userlists);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
    
		
	}

}
