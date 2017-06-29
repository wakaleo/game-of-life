<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.io.InputStream" %>
<%@page import="java.io.IOException" %>
<%@page import="java.util.Properties" %>
<html>
<head>
    <title>The Game Of Life</title>
    <!--  TODO: Don't inline this -->
    <style type="text/css">
        h2 {
            color: blue;
            font-family: sans-serif;
        }

        .intro {
            font-family: sans-serif;
            background: rgb(200, 200, 200);
            border: 1pt rgb(150, 150, 150) solid;
            padding: 8px;
            margin: 4px;
            text-align: justify;
            color: rgb(25, 25, 25)
        }

        .footer {
            color: white;
            font-size: medium;
            text-align: right;
            background-color: blue;
            margin-top: 100px;
            border-top: thin solid black;
            padding: 2px;
            font-family: sans-serif;
            font-weight: bold;
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

    </style>
</head>
<body>
<h2>Welcome to Conway's Game Of LifeC_COMMIT rq1</h2>

<div class="intro">
    <p>This is a really cool web version of Conway's famous Game Of
        Life. The Game of Life is a cellular automaton devised by the British
        mathematician John Horton Conway way back in 1970.</p>

    <p>The universe of the Game of Life is an infinite two-dimensional
        orthogonal grid of square cells, each of which is in one of two possible
        states, live or dead. Every cell interacts with its eight neighbors,
        which are the cells that are directly horizontally, vertically, or
        diagonally adjacent. At each step in time, the following transitions
        occur:
    <ul>
        <li>Any live cell with fewer than two live neighbours dies, as if
            caused by underpopulation.
        </li>
        <li>Any live cell with more than three live neighbours dies, as if
            by overcrowding.
        </li>
        <li>Any live cell with two or three live neighbours lives on to
            the next generation.
        </li>
        <li>Any dead cell with exactly three live neighbours becomes a
            live cell.
        </li>
    </ul>
    </p>
</div>

<c:url value="/game/new" var="newGamePage"/>
<div class="action-button">
    <a href="${newGamePage}">New Game</a>
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
</html>
