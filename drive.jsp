<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor ⋆ Drive</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
    	<style>


		</style>
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
                   		<a href="drive.html">Drive</a>
                	</div>
	                <div class="nav-link-wrapper">
	                    <a href="login.html">Sign out</a>
	                </div>
            	</div>

			</div>

			<div class="content-wrapper">

				<div class="center-box">
					<div class="welcome"><h1><span class="blue-text">Weclome!</span></h1></div><br />
					<div class="docs">

						<div class="tab">
						  <button class="tablinks" onclick="openTab(event, 'my')">My Docs</button>
						  <button class="tablinks" onclick="openTab(event, 'shared')">Shared Docs</button>
						</div> <!-- tab -->

						<div id="my" class="tabcontent">
						  <!-- <p>My notes</p> -->
							 <%
							  Vector<String> myDocs = (Vector<String>)session.getAttribute("myDocs");
							  Vector<Integer> myDocsID = (Vector<Integer>)session.getAttribute("myDocsID");
							  for (int i=0; i<myDocs.size(); i++) {
								  String docName = myDocs.get(i);
								  System.out.println(docName);
								  int docID = myDocsID.get(i);
							 %>
							 	<div class="one-doc">
							 		<a href="DocumentServlet?docName=<%=docName%>&docID=<%=docID%>">
							 			<p><%= docName %></p>
							 		</a>
							 	</div> <!-- one-doc -->
							 <%  
							 }
							 %>
						</div>

						<div id="shared" class="tabcontent">
							<!-- <p>Our notes</p> -->
							<%
							Vector<String> sharedDocs = (Vector<String>)session.getAttribute("sharedDocs");
							Vector<String> sharedDocsID = (Vector<String>)session.getAttribute("sharedDocsID");
							for (int i=0; i<sharedDocs.size(); i++) {
							
							}
							%>
							<%
							
							%>
						</div>

						<script>
							function openTab(evt, tabName) {
							  var i, tabcontent, tablinks;
							  tabcontent = document.getElementsByClassName("tabcontent");
							  for (i = 0; i < tabcontent.length; i++) {
							    tabcontent[i].style.display = "none";
							  }
							  tablinks = document.getElementsByClassName("tablinks");
							  for (i = 0; i < tablinks.length; i++) {
							    tablinks[i].className = tablinks[i].className.replace(" active", "");
							  }
							  document.getElementById(tabName).style.display = "block";
							  evt.currentTarget.className += " active";
							}
						</script>

					</div> <!-- docs -->


				</div>

			</div>

		</div>

	</body>
</html> 