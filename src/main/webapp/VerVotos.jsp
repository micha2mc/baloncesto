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

    <table>
        <tr>
            <td>ID</td>
            <td>Nombre</td>
            <td>Votos</td>
        </tr>
        <% for (Jugador jugador: jugadores) {%>
        <tr>
            <td><%=jugador.getId()%></td>
            <td><%=jugador.getNombre()%></td>
            <td><%=jugador.getVotos()%></td>
        </tr>
        <%}%>
    </table>

</body>
</html>