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
    
    //referenced https://codepen.io/brianmearns/pen/YVjZWw for highlighting words in contenteditable div
    function codify() {
    	const editor = document.getElementById('doc');
    	var keywords = new Set(["assert","abstract","boolean","break","byte","case","catch","char","class",
    		"const","continue","default","do","double","else","enum","exports","extends","final","finally",
    		"float","for","goto","if","implements","import","instaceof","int","interface","long","module",
    		"native","new","package","private","protected","public","requires","return","short","static",
    		"strictfp","super","super","switch","synchronized","this","throw","throws","transient",
    		"try","void","volatile","while","true","false","null","var"]); 
    	
        function getTextSegments(element) {
            const textSegments = [];
            Array.from(element.childNodes).forEach((node) => {
                switch(node.nodeType) {
                    case Node.TEXT_NODE:
                        textSegments.push({text: node.nodeValue, node});
                        break;

                    case Node.ELEMENT_NODE:
                        textSegments.splice(textSegments.length, 0, ...(getTextSegments(node)));
                        break;

                    default:
                        throw new Error(`Unexpected node type: ${node.nodeType}`);
                }
            });
            return textSegments;
        }


        editor.addEventListener('input', updateEditor);

        function updateEditor() {
            const sel = window.getSelection();
            const textSegments = getTextSegments(editor);
            const textContent = textSegments.map(({text}) => text).join('');
            let anchorIndex = null;
            let focusIndex = null;
            let currentIndex = 0;
            textSegments.forEach(({text, node}) => {
                if (node === sel.anchorNode) {
                    anchorIndex = currentIndex + sel.anchorOffset;
                }
                if (node === sel.focusNode) {
                    focusIndex = currentIndex + sel.focusOffset;
                }
                currentIndex += text.length;
            });

            editor.innerHTML = renderText(textContent);

            restoreSelection(anchorIndex, focusIndex);
        }

        function restoreSelection(absoluteAnchorIndex, absoluteFocusIndex) {
            const sel = window.getSelection();
            const textSegments = getTextSegments(editor);

            let anchorNode = editor;
            let anchorIndex = 0;
            let focusNode = editor;
            let focusIndex = 0;
            let currentIndex = 0;

            textSegments.forEach(({text, node}) => {
                const startIndexOfNode = currentIndex;
                const endIndexOfNode = startIndexOfNode + text.length;
                if (startIndexOfNode <= absoluteAnchorIndex && absoluteAnchorIndex <= endIndexOfNode) {
                    anchorNode = node;
                    anchorIndex = absoluteAnchorIndex - startIndexOfNode;
                }
                if (startIndexOfNode <= absoluteFocusIndex && absoluteFocusIndex <= endIndexOfNode) {
                    focusNode = node;
                    focusIndex = absoluteFocusIndex - startIndexOfNode;
                }
                currentIndex += text.length;

            });

            sel.setBaseAndExtent(anchorNode,anchorIndex,focusNode,focusIndex);
        }

        function renderText(text) {
            const words = text.split(/(\s+)/);
            const output = words.map((word) => {
            	//find word in set
                if (keywords.has(word)) {
                	return '<strong><span style="color:magenta">' + word + '</span></strong>';
                   // return '<strong><span>${word}</span></strong>';
                }
                else {
                    return word;
                }
            })
            return output.join('');
        }

        updateEditor();
	}
</script>

</body>
</html>
