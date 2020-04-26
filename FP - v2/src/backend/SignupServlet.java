package backend;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public SignupServlet() {
        super();
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        boolean flag = false;
			flag = Account.createAccount(email, password);
		  
		String destPage = "registration.jsp";
		 
		if (flag) {
		    destPage = "login.jsp";
		    
		} else {
		    String message = "Email existed.";
		    request.setAttribute("error", message);
		}
		
		
		//destPage.redirect to 
		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
    }
}