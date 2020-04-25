<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor ⋆ Document</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
	</head>
	
	<body>
	
		<%
		String decodedString = "";
		try {
			//connect to database
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=root");
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
			System.out.println("Exception in pulling data from database: " + sqle.getMessage());
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
                	<!--  
                	<div class="nav-link-wrapper">
                   		<a href="drive.html">Drive</a>
                	</div>
	                <div class="nav-link-wrapper">
	                    <a href="login.html">Sign out</a>
	                </div>
	                -->
            	</div>
            	
			</div>
			
			<div class="nav-wrapper">
			
				<div class="left-side">
				
					<div class="title">
	                	<span class="blue-text">Title</span>
	               	</div>
	               
	            </div>
	            
                <div class="right-side">
                
	                
	                <div class="view">View only</div>
	                
	            	
	                	<!--  
	                	<div class="nav-link-wrapper">
		                    <a href="login.html">Bold</a>
		                </div>
		                <div class="nav-link-wrapper">
		                    <a href="login.html">Italics</a>
		                </div>
		                <div class="nav-link-wrapper">
		                    <a href="login.html">Underline</a>
		                </div>
		                <div class="nav-link-wrapper">
		                    <a href="login.html">Code</a>
		                </div>
		                <div class="nav-link-wrapper">
		                	<a href="login.html">Share</a>
		                </div>
	                	-->
	             </div>
			
			</div>
			
			<div class="content-wrapper">
				<div class="welcome"></div><br/>
				
					<p> <%= content %></p>
				
				
			</div>
		
		</div>
		
	</body>
</html>
