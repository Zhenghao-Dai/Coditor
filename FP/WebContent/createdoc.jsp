<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor - Create Doc</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
    	<script>
		function validate() {
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET", "CreateDocServlet?docName=" + document.createform.docName.value, false);
			xhttp.send();
			console.log(xhttp.responseText);
			if (xhttp.responseText.trim().length > 0) {
				document.getElementById("formerror").innerHTML = xhttp.responseText;
				return false;
			}
			return true;
		}
		</script>
	</head>
	
	<body>
		<%
		String createerror = (String)request.getAttribute("createerror");
		if (createerror == null) {
			createerror = "";
		}
		%>

		<div class="container">


			<div class="nav-wrapper">

				<div class="left-side">
	                <div class="brand">
	                    <div>CODITOR</div>
	                </div>
	            </div>

                <div class="right-side">
                	<div class="nav-link-wrapper">
                   		<a href="drive.jsp">DRIVE</a>
                	</div>
	                <div class="nav-link-wrapper">
	                    <a href="logout.jsp">LOGOUT</a>
	                </div>
            	</div>

			</div>

			<div class="content-wrapper">

				<div class="center-box">
					<div class="welcome"><h1><span class="blue-text">Create new document.</span></h1></div><br />
					
					<form method="POST" action="DriveServlet" name="createform" onsubmit="return validate()">
						<div id="formerror"></div>
						<table align="center" style="text-align: left">
							<tr>
								<td>Doc Name:</td>
								<td><input type="text" name="docName"/></td>
							</tr>
						</table>
						<input type="submit" name="submit" value="Create New Doc" />
					</form>
				</div>

			</div>


		</div>

	</body>
</html>