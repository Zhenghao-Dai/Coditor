package coditor;


import java.io.*;
import java.sql.SQLException;
 
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
 
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
        super();
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
  
        
        Account user = new Account();
         
        //if not exist then return null
		boolean flag = user.login(username, password);
		String destPage = "login.jsp";
		 
		if (flag) {
		    HttpSession session = request.getSession();
		    session.setAttribute("user", user);
		    destPage = "home.jsp";
		    
		} else {
		    String message = "Invalid email/password";
		    request.setAttribute("message", message);
		    
		}
		
		
		//destPage.redirect to 
		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
    }
}




