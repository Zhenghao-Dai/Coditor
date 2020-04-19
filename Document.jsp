<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Arrays" %>


<!DOCTYPE html>
<html>

<style>
		.editor
        {
			border:solid 1px #ccc;
			padding: 20px;
			min-height:200px;
        }
</style>

<body>

<h1>Document</h1>

<!-- buttons for changing text -->
<button onclick="bold()">Bold</button>
<button onclick="italicize()">Italics</button>
<button onclick="underline()">Underline</button>
<button onclick="codify()">Code</button>

<div class="editor" id="doc">
	</div>

<script>
	document.getElementById("doc").contentEditable = true; 
    
	//bolding function
	function bold() {
    	var f = document.getElementById("doc").style.fontWeight;
        if(f == 'bold')
        {
       		document.execCommand('normal');
        }
        else
        {
        	document.execCommand('bold');
        }
    }
    
    //italicizing function
    function italicize() {
       var f = document.getElementById("doc").style.fontStyle;
        if(f == 'italic')
        {
       		document.execCommand('normal');
        }
        else
        {
        	document.execCommand('italic');
        }
    }
    
    //underlining function
    function underline() {
       var f = document.getElementById("doc").style.textDecoration;
        if(f == 'underline')
        {
       		document.execCommand('normal');
        }
        else
        {
        	document.execCommand('underline');
        }
    }
    
   	//function to change text to code
    function codify() {
    	var editor = document.getElementById('doc');
    	
    	//Set of Java keywords
    	var keywords = new Set(["assert","abstract","boolean","break","byte","case","catch","char","class",
    	    		"const","continue","default","do","double","else","enum","exports","extends","final","finally",
    	    		"float","for","goto","if","implements","import","instaceof","int","interface","long","module",
    	    		"native","new","package","private","protected","public","requires","return","short","static",
    	    		"strictfp","super","super","switch","synchronized","this","throw","throws","transient",
    	    		"try","void","volatile","while","true","false","null","var"]); 
    	    	
    	//listens for user input and executes changeColor() function when user types a character
    	editor.addEventListener("input", changeColor);     

    	function changeColor() {
    		window.onkeydown = function(e){
    			//if space bar is pressed then select the text before the space bar
    	    	if(e.keyCode == 32) {
    	            var range = document.createRange();
    				range.selectNodeContents(editor);
    				var s = window.getSelection();
    				s.removeAllRanges();
    				s.addRange(range);
    	            s = s.toString();
    	            
    	            //check if selected word is a keyword
    	            if(keywords.has(s)) {
    	            	doc.innerHTML = "<strong><span style='color:magenta'>" + s + "</span> </strong>";
    	            }
    	            else
    	            {
    	            	doc.innerHTML = "<span>" + s + "</span> ";
    	            }
		    //move cursor to end of word
    	            //setCursor();
    	        }
    	     }
    	}
	}
</script>

</body>
</html>
