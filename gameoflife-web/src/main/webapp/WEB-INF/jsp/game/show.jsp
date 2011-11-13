<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.io.InputStream" %>
<%@page import="java.io.IOException" %>
<%@page import="java.util.Properties" %>
<html>
<head>
<title>The Game Of Life</title>
    <link rel="stylesheet" href="/css/default.css" />
</head>
<body>
<h2>The Game!</h2>
<c:url value="/home" var="homePage" />
<c:url value="/game/next" var="nextPage" />

<div class="breadcrumbs">
  <a href="${homePage}">home</a> > Game in Progress
</div>
<div class="intro">Press 'Next Generation' to see how this universe evolves...</div>
<div class="griddisplay">
<form action="${nextPage}" method="get" >
<input type="hidden" name="rows" value="${rows}" />
<input type="hidden" name="columns" value="${columns}" />
<table id="grid">
 <c:forEach begin="0" var="row" end="${rows - 1}" >
  <tr>
    <c:forEach begin="0" var="column" end="${columns - 1}" >  
      <td>
      <c:if test="${universe.cells[row][column].symbol == '*'}">
        <input type="hidden" name="cell_${row}_${column}" value="on"/>
      </c:if>
      &nbsp;${universe.cells[row][column].symbol}&nbsp;</td>
    </c:forEach>
  </tr>
 </c:forEach>
</table>
</p>
<button type="submit" id="submit">Next Generation</button>
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