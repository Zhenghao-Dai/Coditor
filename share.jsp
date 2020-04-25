<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor ⋆ Document</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
	</head>

	<body>

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
                   		<a href="drive.html">Drive</a>
                	</div>
	                <div class="nav-link-wrapper">
	                    <a href="logout">Logout</a>
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
								<td>Enter the email you want to grant access:</td>
								<td><input type="text" name="shareEmail"/></td>
							</tr>
						</table>
						<input type="submit" name="submit" value="Create New Doc" />
					</form>
				</div> <!-- center-box -->		


			</div> <!-- content-wrapper -->

		</div> <!-- container -->

	</body>
</html>