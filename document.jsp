<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Coditor ⋆ Document</title>
		<link href="https://fonts.googleapis.com/css?family=Lato:300|Poppins:100,200&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="styles.css">
    	
    	<!-- Include the highlight.js library -->
		<link rel="stylesheet"
		      href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.9.0/styles/agate.min.css">
		<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.18.1/highlight.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/KaTeX/0.7.1/katex.min.js"></script>
		<script>hljs.initHighlightingOnLoad();</script>
		
		<!-- Include the Quill library -->
		<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
		
		<!-- Include Quill stylesheet -->
		<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
		<link href="highlight.js/monokai-sublime.min.css" rel="stylesheet">
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
	                    <a href="login.html">Sign out</a>
	                </div>
            	</div>

			</div>

			<div class="nav-wrapper">

				<div class="left-side">

					<div class="title">
	                	<span class="blue-text">Title</span>
	               	</div>

	            </div>

                <div class="right-side">

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

	             </div>

			</div>

			<div class="content-wrapper">
				<!-- Create the editor container -->
				<div id="editor">
				</div>
				
				<!-- Initialize Quill editor -->
				<script>
					var quill = new Quill('#editor', {
					  modules: {
					    syntax: true,              
					    toolbar: [['bold', 'italic','underline', 'code-block']]  
					  },
					  theme: 'snow'
					});
				</script>

			</div>

		</div>

	</body>
</html>