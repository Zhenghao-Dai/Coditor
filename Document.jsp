<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


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
	var Delta = Quill.import('delta');
	var quill = new Quill('#editor', {
	  modules: {
	    syntax: true,              
	    toolbar: [['bold', 'italic','underline', 'code-block']]  
	  },
	  placeholder: 'Enter text here',
	  theme: 'snow'
	});
	
	//Store accumulated changes
	var change = new Delta();
	quill.on('text-change', function(delta) {
	  change = change.compose(delta);
	});
	
	// Save periodically
	// destination locations for both set to redirect back to Document.jsp bc we don't want it to leave after saving 
	setInterval(function() {
	  if (change.length() > 0) {
	    console.log('Saving changes', change);
	    $.post('/Document.jsp', { 
	      partial: JSON.stringify(change) 
	    });
	    
	    $.post('/Document.jsp', { 
	      doc: JSON.stringify(quill.getContents())
	    });
	    
	    change = new Delta();
	  }
	}, 5*1000);
	
	// Check for unsaved data
	window.onbeforeunload = function() {
	  if (change.length() > 0) {
	    return 'There are unsaved changes. Are you sure you want to leave?';
	  }
	}
</script>
<body></body>

</html>
