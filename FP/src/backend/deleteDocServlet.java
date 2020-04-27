package backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteDocServlet")
public class deleteDocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public deleteDocServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nextPage = "deletedoc.jsp";
		
		String docName = request.getParameter("docName");
		System.out.println("Deleting doc named: " + docName + " --> in DeleteDocServlet");
		String[] terms = request.getParameterValues("terms");
//		System.out.println("deleteDocServlet - " + docName + "_____" + terms);
//		System.out.println("deleteDocServlet - " + terms.length);
		
		boolean removeSuccess = true;
		PrintWriter out = response.getWriter();
		
		if (docName == null || docName.length() == 0) {
			out.println("Please enter a doc name.");
			removeSuccess = false;
		} if (terms == null || terms.length == 0 ) { 
			out.println("Please check that you understand the terms to delete doc permanently.");
			removeSuccess = false;
		} else {
			int docExists = Database.getDocID(docName);
			if (docExists == -1) {
				out.println("The doc you are trying to delete does not exist. Please enter a valid doc name.");
				removeSuccess = false;
			}
		}
		
		if (removeSuccess) {
			
			removeSuccess = false;
			removeSuccess = Database.removeDocument(docName);
			
			if (!removeSuccess) {
				out.println("Error in creating new doc.");
			}
		}
		
		out.flush();
		out.close();
		
	}

}