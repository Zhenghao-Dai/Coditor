import java.io.*;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import backend.Database;

 
@WebServlet("/shareServlet")
public class shareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public shareServlet() {
        super();
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String shareEmail = request.getParameter("shareEmail");
        String docName = request.getParameter("docName");
        
        
        String destPage = "drive.jsp";
        System.out.println("shareServlet - email: "+shareEmail);
        System.out.println("shareServlet - document name:  " + docName);
        
        Database.userExists(shareEmail);
        
        
        if ((shareEmail == null) || (shareEmail == ""))
        {
        	request.setAttribute("docName", docName);
        	destPage = "share.jsp";
        }
        else if (Database.userExists(shareEmail) == true)
        {
        	System.out.println("shareServlet - sharing with existing user" );
        	// share to registered user
        	Database.shareDocument(docName, shareEmail);
        }
        else
        {
        	
        	System.out.println("shareServlet - sharing with guest" );
        	int docID = Database.getDocID(docName);
        	String guestURL = "Copy the following link and send it to the guest: " + "localhost:3456/CS201FP/guestshare?docName="+docName+"&docID="+docID;
        	request.setAttribute("docName", docName);
        	request.setAttribute("guestURL", guestURL);
        	destPage = "share.jsp";
        	/*
        	int docID = Database.getDocID(docName);
        	try {
				SendEmail.SendEmail(shareEmail, docName, String.valueOf(docID));
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
        	// guest share
        }
        /*
        Account user = new Account();
         
        //if not exist then return null
        boolean flag = false;
		try {
			flag = user.login(email, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		String destPage = "login.jsp";
		 
		if (flag) {
		    HttpSession session = request.getSession();
		    session.setAttribute("user", user);
		    destPage = "DriveServlet";
		    session.setAttribute("email", email);
		    
		} else {
		    String message = "Invalid email/password";
		    request.setAttribute("message", message);
		    
		}
			
		//destPage.redirect to 
		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
		
		*/
		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
    }
}