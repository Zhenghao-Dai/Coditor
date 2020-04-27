<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor - Sign up</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
    	<script>
    		function validate() {
    			/*alert("Inside validate");*/
				document.getElementById("errormessage").innerHTML = "";
				var hasError = false;
				if (document.SignUp.email.value.length == 0) {
					document.getElementById("errormessage").innerHTML = "<font color=\"red\">Email needs a value.</font><br />";
					hasError = true;
				}
				if (document.SignUp.password.value.length == 0) {
					document.getElementById("errormessage").innerHTML += "<font color=\"red\">Password needs a value.</font><br />";
					hasError = true;
				}				
				return !hasError;
    		}
    	</script>
	</head>

	<body>
	
		<% 
			String email = request.getParameter("email");
			if (email == null) {
				email = "";
			}
			String error = (String)request.getAttribute("error");
			if (error == null) {
				error = "";
			}
			String uniqueerror = (String)request.getAttribute("uniqueerror");
			if (uniqueerror == null) {
				uniqueerror = "";
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
                   		<a href="login.jsp">Login</a>
                	</div>
	                <div class="nav-link-wrapper active-nav-link">
	                    <a href="registration.jsp">Sign up</a>
	                </div>
            	</div>

			</div>

			<div class="content-wrapper">

				<div class="center-box">
					<div class="welcome"><h1>Welcome to<span class="blue-text"> Coditor.</span></h1></div><br />
					<h2>Sign up for free.</h2>
					<form method="POST" action="signup" name="SignUp" onsubmit="return validate()">
						<div id="errormessage"></div>
						<table align="center" style="text-align: left">
							<tr>
								<td>Email</td>
								<td><input type="email" name="email" value<%=email%>/></td>
							</tr>
							<tr>
								<td>Password</td>
								<td><input type="password" name="password" /></td>
								<font color="red"><%= error %></font>
								<font color="red"><%= uniqueerror %></font>
							</tr>
						</table>
						<input type="submit" name="submit" value="Sign up"/>
					</form>
				</div>

			</div>

		</div>

	</body>
</html>