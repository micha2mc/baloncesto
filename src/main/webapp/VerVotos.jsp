<%--
  Created by IntelliJ IDEA.
  User: micha
  Date: 30/12/2023
  Time: 5:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.dto.Jugador" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Listado de votos</title>
    <style type="text/css">
        .cabecera {
            font-size: 1.2em;
            font-weight: bold;
            color: #FFFFFF;
            background-color: #08088A;
        }
        .filas {
            text-align: center;
            background-color: #5882FA;
        }
    </style>
</head>

<body>
<%
    //obtenemos todos los jugadores
    List<Jugador> jugadores = (List<Jugador>) session.getAttribute("jugadores");
%>
    <table aria-describedby="jugadores">
        <tr>
            <th scope="col" class="cabecera">ID</th>
            <th scope="col" class="cabecera">Nombre</th>
            <th scope="col" class="cabecera">Votos</th>
        </tr>
        <% for (Jugador jugador: jugadores) {%>
        <tr>
            <td class="filas"><%=jugador.getId()%></td>
            <td class="filas"><%=jugador.getNombre()%></td>
            <td class="filas"><%=jugador.getVotos()%></td>
        </tr>
        <%}%>
    </table>

<br>
<br> <a href="index.html"> Ir al comienzo</a>

</body>
</html>