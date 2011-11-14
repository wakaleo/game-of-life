<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.io.InputStream" %>
<%@page import="java.io.IOException" %>
<%@page import="java.util.Properties" %>
<html>
<head>
<title>The Game Of Life</title>
    <link rel="stylesheet" href="/css/default.css" />
</style>
</head>
<body>
<h2>The Game!</h2>

<c:url value="/home" var="homePage" />
<c:url value="/game/start" var="startPage" />


<div class="breadcrumbs">
  <a href="${homePage}">home</a> > New Game
</div>
<div class="intro">Please seed your universe</div>
<div class="griddisplay">
<form action="${startPage}" method="get" >
<input type="hidden" name="rows" value="3" />
<input type="hidden" name="columns" value="3" />
<table id="grid">
 <c:forEach begin="0" var="row" end="2" >
  <tr>
    <c:forEach begin="0" var="column" end="2" >  
      <td><input name="cell_${row}_${column}" type="checkbox"/></td>
    </c:forEach>
  </tr>
 </c:forEach>
</table>
<p/>
<button type="submit" id="submit">Go</button>
</form>
</div>
<%
	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/system.properties");
	Properties prop = new Properties();  
	String appVersion = "";
	if ( inputStream != null ) {  
		try {  
			prop.load(inputStream);  
			appVersion = prop.getProperty("app.version");  
		}  
		catch ( IOException ioe ) {  
			ioe.printStackTrace();  
		}  
	}  
%>
<div class="footer">Game Of Life version <%=appVersion%></div>
</body>