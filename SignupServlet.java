package coditor;

import java.io.IOException;

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
