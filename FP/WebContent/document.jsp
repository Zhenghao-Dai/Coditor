<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.io.IOException" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.sql.Statement" %>
<%@ page import = "java.util.Base64" %>
    
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="robots" content="noindex, nofollow">
		<title>Coditor Document</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
    	
    	 <script src="https://cdn.ckeditor.com/4.14.0/standard-all/ckeditor.js"></script>
    	 <style>
    	 	#dontShow {
    	 		border: none;
    	 		color: white;
    	 	}
    	 </style>
	</head>

	<body>
		<%		
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
				conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=root&useSSL=false&useLagacyDatetimeCode=false&serverTimezone=UTC");
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
				//by here, if we entered  the if above then 'content' has the actual html with the tags, etc
				
				
			} catch (SQLException | ClassNotFoundException sqle) {
				System.out.println("document.jsp - Exception in pulling data from database: " + sqle.getMessage());
			}
		%>

		<div class="container">

			<div class="nav-wrapper">

				<div class="left-side">
	                <div class="brand">
	                    CODITOR
	                </div>
	            </div>

                <div class="right-side">

                	<div class="nav-link-wrapper">
                   		<a href="drive.jsp">Drive</a>
                	</div>
	                <div class="nav-link-wrapper">
	                    <a href="logout.jsp">Logout</a>
	                </div>
            	</div>

			</div>

			<div class="nav-wrapper">

				<div class="left-side">

					<div class="title">
	                	<span class="blue-text"><%=docName %></span>
	               	</div>

	            </div>

                <div class="right-side">
					<form method = "POST" action = "shareIntermediateServlet" name = "shareIntermediate">
		               <div class="nav-link-wrapper">
		               		
		                	<input style = "display:none;" type="text" name="docName" value= "<%=docName %>">
		                	<input type="submit" name="submit" value="Share" />
		                </div>
					</form>
	             </div>

			</div>

			<div class="content-wrapper">
				<!-- Create the editor container -->
				<form method = "GET" action = "saveDocServlet" name = "saveDoc">
					<div id="editor">
						<textarea id="editor1" name="editor1" data-sample-preservewhitespace> <%=content %></textarea>
						<input type="text" id="dontShow" name="docID" value=<%=docID %>>
						<button> Save & Exit </button>
						  <script>
						    var config = {
						      extraPlugins: 'codesnippet',
						      codeSnippet_theme: 'monokai_sublime',
						      height: 356
						    };
						
						    CKEDITOR.replace('editor1', config);
						  </script>
					</div>
				</form>

		</div>
		</div>

	</body>
</html>