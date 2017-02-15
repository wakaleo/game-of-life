<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.io.InputStream" %>
<%@page import="java.io.IOException" %>
<%@page import="java.util.Poperties" %>
<html>
<head>
    <title>The Game Of Life</title>
    <style type="text/css">
        h2 {
            color: red;
            font-family: sans-serif;
        }

        .intro {
            font-family: sans-serif;
            background: rgb(200, 200, 200);
            border: 1pt rgb(150, 150, 150) solid;
            padding: 8px;
            text-align: justify;
            color: rgb(25, 25, 25);
            margin: 4px;
        }

        .footer {
            color: blue;
            font-size: small;
            text-align: right;
            background-color: silver;
            margin-top: 100px;
            border-top: thin solid black;
            padding: 2px;
            font-family: sans-serif;
        }

        .action-button {
            border-bottom: 2px solid rgb(100, 100, 100);
            border-left: 2px solid rgb(100, 100, 100);
            border-top: 2px solid rgb(150, 150, 150);
            border-right: 2px solid rgb(150, 150, 150);
            background: silver;
            width: 100px;
            margin: 8px;
            padding: 4px;
            text-align: center;
        }

        a {
            text-decoration: none;
            font-size: large;
            font-weight: bold;
            font-family: sans-serif;
        }

        li {
            list-style: circle;
            padding: 4px;
        }

        .breadcrumbs {
            font-family: sans-serif;
            color: blue;
        }

        h4 {
            font-family: sans-serif;

        }

        #grid {
            border: 1px solid grey;
            padding: 4px;
            margin: 16px;
            margin-left: 4px;
            font-family: sans-serif;
            background-color: rgb(255, 255, 225);
        }

        .griddisplay {
        }

    </style>
</head>
<body>
<h2>The Game of Life!</h2>
<c:url value="/home" var="homePage"/>
<c:url value="/game/next" var="nextPage"/>

<div class="breadcrumbs">
    <a href="${homePage}">home</a> > Current Game Progress
</div>
<div class="intro">Press 'Next Generation' to see how this universe evolves...</div>
<div class="griddisplay">
    <form action="${nextPage}" method="get">
        <input type="hidden" name="rows" value="${rows}"/>
        <input type="hidden" name="columns" value="${columns}"/>
        <table id="grid">
            <c:forEach begin="0" var="row" end="${rows - 1}">
                <tr>
                    <c:forEach begin="0" var="column" end="${columns - 1}">
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
    if (inputStream != null) {
        try {
            prop.load(inputStream);
            appVersion = prop.getProperty("app.version");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
%>
<div class="footer">Game Of Life version <%=appVersion%>
</div>
</body>
