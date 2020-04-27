<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor - Logout</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
	</head>
	
	<body>

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
            	</div>

			</div>

			<div class="content-wrapper">

				<div class="center-box">
					<div class="welcome"><h1><span class="blue-text">Logout</span></h1></div><br />
						<form method="POST" action="LogoutServlet" name="logout">
							<div id="formerror"></div>
								<p>Confirm logout? All documents will be saved.</p>
							<input type="submit" name="submit" value="Logout" />
						</form>
				</div>

			</div>


		</div>

	</body>
</html>