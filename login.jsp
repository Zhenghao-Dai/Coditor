<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor ⋆ Login</title>
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
                	<div class="nav-link-wrapper active-nav-link">
                   		<a href="login.html">Login</a>
                	</div>
	                <div class="nav-link-wrapper">
	                    <a href="registration.html">Sign up</a>
	                </div>
            	</div>

			</div>

			<div class="content-wrapper">

				<div class="center-box">
					<div class="welcome"><h1>Welcome to <span class="blue-text"> Coditor.</span></h1></div><br />
					<h2>Login to continue.</h2>
					<table align="center" style="text-align: left">
						<tr>
							<td>Email</td>
							<td><input type="email" name="email" /></td>
						</tr>
						<tr>
							<td>Password</td>
							<td><input type="password" name="password" /></td>
						</tr>
					</table>
					<input type="submit" name="submit" value="Sign in" />
				</div>

			</div>


		</div>

	</body>
</html>