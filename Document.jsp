<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jsyntaxpane.DefaultSyntaxKit" %>


<!DOCTYPE html>
<html>

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
<body></body>

</html>

