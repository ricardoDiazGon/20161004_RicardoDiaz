<%-- 
    Document   : Saludo
    Created on : 10-oct-2016, 17:21:55
    Author     : Ricardo
--%>

<%@page import="java.time.LocalTime"%>
<%@page import="java.util.Locale"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../estilos/estilo.css" />
        <title>Saludo con JSP</title>
    </head>
    <body>
        <div id="contenedor">
            <h1>Saludo con JSP</h1>
            
            <% 
                int hora = LocalTime.now().getHour();
                String nombre = request.getParameter("nombre");
                String sexo = request.getParameter("sexo");              
            %>
            <h2>
            <% if(hora >=  8 && hora < 13){ %>
                Buenos d&iacute;ias, 
            <% } else if(hora >= 13 && hora < 19){ %>
                Buenas tardes, 
            <% } else { %>
                Buenas noches, 
            <% } %>
            
            <% if(sexo.equals("hombre")){ %>
            señor 
            <% }else{ %>
            señora 
            <% } %>
            
            <%= request.getParameter("nombre")%>.
            </h2>
            <h2></h2>

            <p><a class="enlace" href="../index.html">-> Ir al inicio <-</a></p>
        </div>
    </body>
</html>
