package backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/CreateDocServlet")
public class createDocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public createDocServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nextPage = "createdoc.jsp";

		String docName = request.getParameter("docName");
		System.out.println("Creating new doc named: " + docName + " --> in CreateDocServlet");

		boolean addSuccess = true;
		PrintWriter out = response.getWriter();

		if (docName == null || docName.length() == 0) {
			out.println("Please enter a doc name.");
			addSuccess = false;
		} else {
			int docExists = Database.getDocID(docName);
			if (docExists != -1) {
				out.println("Another doc with this name already exists. Please enter a new doc name.");
				addSuccess = false;
			}
		}

		if (addSuccess) {

			addSuccess = false;

			HttpSession session = request.getSession();
			String email = (String)session.getAttribute("email");
			int userID = Database.getUserID(email);

			addSuccess = Database.createNewDocument(docName, userID);

			if (!addSuccess) {
				out.println("Error in creating new doc.");
			}
		}

		out.flush();
		out.close();

	}

}