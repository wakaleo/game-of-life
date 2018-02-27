tinyMCEPopup.requireLangPack();

// replace special HTML chars with their escape counterparts
function escapeHtml(text) {
    //	return text.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").
    //		replace(/\r\n/g,"<br>").replace(/\n/g,"<br>").replace(/\r/g,"<br>");
    return text.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
}

// convert escaped characters back to unescaped ones (inversion of escapeHtml)
function unescapeHtml(text) {
    //	return text.replace(/<br>/g,"\n").replace(/&lt;/g,"<").replace(/&gt;/g,">").replace(/&amp;/g,"&");
    return text.replace(/&lt;/g,"<").replace(/&gt;/g,">").replace(/&amp;/g,"&");
}

function trim(str) {
    if(typeof(str)=="string") return str.replace(/^\s+|\s+$/g,"");
    else return str;
}

function findPreTag(ed) {
    var pre = ed.selection.getNode();

    if(pre == null || pre.tagName.toLowerCase() != 'pre')
        return null;
	
    return pre;	
}

var SyntaxHLDialog = {
    init : function() {
        var f, pre;
        var parameters = Array();
        var type = "";
				
        ed = tinyMCEPopup.editor;
        f = document.forms[0];
        pre = findPreTag(ed);

        if(pre != null) {
            f.syntaxhl_code.value = unescapeHtml(pre.innerHTML);
            f.prettify_code.value = unescapeHtml(pre.innerHTML);

            // Parse parameters
            var classparts = ed.dom.getAttrib(pre, 'class').split(";");
            for(var i = 0; i < classparts.length; i++) {
                var values = classparts[i].split(":");
                if(values.length == 2) {
                    parameters[ trim(values[0]) ] = trim(values[1]);
                }
            }
        }

        // Map brush-parameter to type in dialog.htm
        switch(parameters["brush"]) {
            case "as3": 
            case "actionscript3":
                type = "actionscript3";
                break;
            case "bash":
            case "shell":
                type = "bash";
                break;
            case "coldfusion":
            case "cf":
                type = "coldfusion";
                break;
            case "c#":
            case "c-sharp":
            case "csharp":
                type = "c#";
                break;
            case "cpp":
            case "c":
                type = "cpp";
                break;	
            case "delphi":
            case "pascal":
                type = "delphi";
                break;			
            case "diff":
            case "patch":
            case "pas":
                type = "diff";
                break;			
            case "erl":
            case "erlang":
                type = "erl";
                break;			
            case "js":
            case "jscript":
            case "javascript":
                type = "js";
                break;			
            case "jfx":
            case "javafx":
                type = "jfx";
                break;			
            case "perl":
            case "pl":
                type = "perl";
                break;			
            case "plain":
            case "text":
                type = "plain";
                break;			
            case "ps":
            case "powershell":
                type = "ps";
                break;			
            case "py":
            case "python":
                type = "py";
                break;			
            case "rails":
            case "ror":
            case "ruby":
            case "rb":
                type = "ruby";
                break;	
            case "sass":
            case "scss":
                type = "sass";
                break;
            case "vb":
            case "vbnet":
                type = "vb";
                break;			
            case "xml":
            case "xhtml":
            case "xslt": 
            case "html":
                type = "xml";
                break;		
            case undefined:
                type = "java";
                break;
            default:
                type = parameters["brush"];
                break;			
        }
       
        f.syntaxhl_language.value = type;
		
        f.syntaxhl_gutter.checked = !("gutter" in parameters && parameters["gutter"] == "false");
        f.syntaxhl_toolbar.checked = !("toolbar" in parameters && parameters["toolbar"] == "false");
        f.syntaxhl_autolinks.checked = !("auto-links" in parameters && parameters["auto-links"] == "false");
        f.syntaxhl_htmlscript.checked =  ("html-script" in parameters && parameters["html-script"] == "true");
        //f.syntaxhl_ruler.checked =  ("ruler" in parameters && parameters["ruler"] == "true");
        //f.syntaxhl_wrap_lines.checked = !("wrap-lines" in parameters && parameters["wrap-lines"] == "false");
        f.syntaxhl_light.checked =  ("light" in parameters && parameters["light"] == "true");
        f.syntaxhl_collapse.checked =  ("collapse" in parameters && parameters["collapse"] == "true");

        f.syntaxhl_firstline.value =  ("first-line" in parameters) ? parameters["first-line"] :"1";
        f.syntaxhl_highlight.value =  ("highlight" in parameters) ? parameters["highlight"] : "";
    },

    insert : function() {
        var f = document.forms[0], textarea_output, options = '';
		
        var commands = new Array();

        if (document.getElementById("general_tab").className === "current") {
            //If no code just return.
            if(f.syntaxhl_code.value == '') {
                tinyMCEPopup.close();
                return false;
            }
            
            commands.push('brush: ' + f.syntaxhl_language.value);

            if(!f.syntaxhl_gutter.checked) commands.push('gutter: false');
            if(!f.syntaxhl_toolbar.checked) commands.push('toolbar: false');
            if(!f.syntaxhl_autolinks.checked) commands.push('auto-links: false');
            if( f.syntaxhl_htmlscript.checked) commands.push('html-script: true');
            //if( f.syntaxhl_ruler.checked) commands.push('ruler: true');
            //if(!f.syntaxhl_wrap_lines.checked) commands.push('wrap-lines: false');
            if( f.syntaxhl_light.checked) commands.push('light: true');
            if( f.syntaxhl_collapse.checked) commands.push('collapse: true');

            if( f.syntaxhl_firstline.value != "1") commands.push('first-line: ' + f.syntaxhl_firstline.value);
            if( f.syntaxhl_highlight.value != "") commands.push('highlight: ' + f.syntaxhl_highlight.value);
		

            var pre = findPreTag(tinyMCEPopup.editor);
            if(pre == null) {
                textarea_output = '<pre class="' + commands.join("; ") + '">';
                textarea_output +=  escapeHtml(f.syntaxhl_code.value);
                textarea_output += '</pre> '; /* note space at the end, had a bug it was inserting twice? */
                tinyMCEPopup.editor.execCommand('mceInsertContent', false, textarea_output);
            } else {
                pre.className = commands.join(";");
                pre.innerHTML = escapeHtml(f.syntaxhl_code.value);
            }

            tinyMCEPopup.close();
        } else {
            //If no code just return.
            if(f.prettify_code.value == '') {
                tinyMCEPopup.close();
                return false;
            }
            
            var pre = findPreTag(tinyMCEPopup.editor);
            if(pre == null) {
                textarea_output = '<pre class="prettyprint">';
                textarea_output +=  escapeHtml(f.prettify_code.value);
                textarea_output += '</pre> '; /* note space at the end, had a bug it was inserting twice? */
                tinyMCEPopup.editor.execCommand('mceInsertContent', false, textarea_output);
            } else {
                pre.className = "prettyprint";
                pre.innerHTML = escapeHtml(f.prettify_code.value);
            }

            tinyMCEPopup.close();
        }
    }	
};

tinyMCEPopup.onInit.add(SyntaxHLDialog.init, SyntaxHLDialog);
