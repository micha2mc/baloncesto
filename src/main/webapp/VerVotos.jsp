<%--
  Created by IntelliJ IDEA.
  User: micha
  Date: 30/12/2023
  Time: 5:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Jugador" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Listado de votos</title>
</head>
<%
    //obtenemos todos los jugadores
    List<Jugador> jugadores = (List<Jugador>) request.getAttribute("jugadores");
%>
<body>

<%=jugadores%>

</body>
</html>