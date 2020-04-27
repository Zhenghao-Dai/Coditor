<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor - Document</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
	</head>

	<body>
		<%

			String docName = (String) request.getAttribute("docName");
			if (docName == null) {
				docName = "";
			} else {
				request.setAttribute("docName", docName);
			}
			String guestURL = (String) request.getAttribute("guestURL");
			if (guestURL == null) {
				guestURL = "";
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
                	<div class="icon">AB</div>
                	<div class="icon">CD</div>
                	<div class="icon">EF</div>

                	<div class="nav-link-wrapper">
                   		<a href="drive.jsp">Drive</a>
                	</div>
	                <div class="nav-link-wrapper">
	                    <a href="logout.jsp">Logout</a>
	                </div>
            	</div>

			</div>

			<div class="content-wrapper">

				<div class="center-box">
					<div class="welcome"><h1><span class="blue-text">Share <%=docName %>.</span></h1></div><br />
					
						<form method="POST" action="shareServlet" name="share">
							<div id="formerror"></div>
							<table align="center" style="text-align: left">
								<tr>
									<td>Enter the email you want to grant access:</td>
									<td><input type="email" name="shareEmail"/></td>
									<td><input style = "display:none;"type="text" name="docName" value= "<%=docName %>"></td>
								</tr>
							</table>
							<input type="submit" name="submit" value="Share document" />
						</form>
						
						
						
						
				</div> <!-- center-box -->		

				<a><font style="color:blue"><%=guestURL %></font></a>

			</div> <!-- content-wrapper -->

		</div> <!-- container -->

	</body>
</html>