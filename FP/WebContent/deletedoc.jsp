<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor - Delete Doc</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
    	<script>
		function validate() {
			if (!document.getElementById("terms").checked) {
				document.getElementById("formerror").innerHTML = "Please agree to terms.";
				return false;
			}
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET", "DeleteDocServlet?docName=" + document.deleteform.docName.value + "&terms=" + document.deleteform.terms.value, false);
			xhttp.send();
			console.log(xhttp.responseText);
			console.log(document.deleteform.terms.value);
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
		String deleteerror = (String)request.getAttribute("deleteerror");
		if (deleteerror == null) {
			deleteerror = "";
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
					<div class="welcome"><h1><span class="blue-text">Delete document.</span></h1></div><br />
					
					<form method="POST" action="DriveServlet" name="deleteform" onsubmit="return validate()">
						<div id="formerror"></div>
						<table align="center" style="text-align: left">
							<tr>
								<td>Doc Name:</td>
								<td><input type="text" name="docName"/></td>
							</tr>
							<tr>
								<td>
									<input type="checkbox" id="terms" name="terms">
				 	 				<label for="terms">I have read and agree to terms</label><br/>
				 	 			</td>
							</tr>
						</table>
						
						<input type="submit" name="submit" value="Delete Doc"/>
					</form>
					<p>Terms: I understand that this action will delete the doc permanently on my drive along with every user with shared access. Only the doc owner is able to delete.</p>
				</div>

			</div>


		</div>

	</body>
</html>