<%-- 
Document   : Tienda
Created on : 17-oct-2016, 18:32:13
Author     : Ricardo
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="es.albarregas.beans.Libro"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%
    String mensajeError = "";
    String mensajeAniadido = "";
    String plural = "";
    Libro libro = null;
    ArrayList<Libro> libros; //Array de libros
    HttpSession sesion = request.getSession(true);
    //Para que saque del request las cosas en UTF-
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");
    //Aquí controlamos los diferentes errores que se puedan dar en la aplicación
    if (request.getParameter("aniadir") != null) {
        if (request.getParameter("titulo") == null) {
            mensajeError = "Introduzca algún libro. "; //Si no hemos seleccionado un libro
        }
        if (request.getParameter("cantidad") == null) {
            mensajeError += "Introduzca una cantidad. "; //Si no hemos introducido una cantidad
        }
        try {
            if (Integer.parseInt(request.getParameter("cantidad")) < 1) {
                mensajeError += "La cantidad de libros debe ser mayor que 0. "; //Si hemos metido una cantidad negativa
            }
        } catch (NumberFormatException ex) {
            mensajeError += "La cantidad introducida no es válida. "; //Si hemos metidos caracteres en cantidad
        }

        if (mensajeAniadido.equals("")) {

        }
    }
    //Si no hay mensajes de error es porque todo salió bien y podemos guardar datos
    if (request.getParameter("aniadir") != null && mensajeError.equals("")) {
        libro = new Libro(); //Creamos el nuevo bean libro para guardar los datos
        libro.setTitulo(request.getParameter("titulo"));
        libro.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
        if (sesion.getAttribute("libros") == null) { //Si no está el array de libros en la sesion lo creamos
            libros = new ArrayList();
            libros.add(libro);
        } else { //Si estaba el array de libros en la sesion, buscamos si está el que hemos añadido y actualizamos
            libros = (ArrayList<Libro>) sesion.getAttribute("libros");
            Iterator<Libro> it = libros.iterator();
            boolean encontrado = false;
            while (it.hasNext()) {
                Libro libroSacado = it.next();
                if (libro.getTitulo().equals(libroSacado.getTitulo())) {
                    libroSacado.setCantidad(libroSacado.getCantidad() + libro.getCantidad());
                    encontrado = true;
                }
            }

            //Si no estabael libro en el arraylist de la sesión, lo metemos nosotros
            if (!encontrado) {
                libros.add(libro);
            }

        }

        //Al final del todo, añadimos el array de libros a la sesión
        sesion.setAttribute("libros", libros);

        //Controlamos si es un libro o más de uno para ponerlo en plural o singular en el mensaje
        if (libro.getCantidad() > 1) {
            plural = "es";
        }
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../estilos/estilo.css" />
        <title>Tienda On-line</title>
    </head>
    <body>

        <div id="contenedor">
            <h1><img class="libro" src="../imagenes/libros2.jpg" > Librería On-line <img class="libro" src="../imagenes/libros2.jpg" ></h1>
            
            <%-- CUANDO LE DAMOS A FINALIZAR EL CARRITO --%>
            <% 
            if (request.getParameter("finalizar") != null) {  
                if(sesion.getAttribute("libros") != null){ %>
               
                    <h1>Carrito de la compra</h1>

                    <table class="parametros" border="1">
                        <tr><th>Titulo</th><th>Cantidad</th></tr>
                            <% if (sesion.getAttribute("libros") != null) {
                                    libros = (ArrayList<Libro>) sesion.getAttribute("libros");
                                    Iterator it = libros.iterator();
                                while (it.hasNext()) {
                                   libro = (Libro)it.next();
                                %>
                                    <tr><td><%=libro.getTitulo()%></td><td><%=libro.getCantidad()%></td></tr>
                                <%}

                            } %>
                    </table>
                    <h3 class="agradecimiento">Gracias por confiar en nosotros</h3>
                <% }else{  %>
                    <h2 class="error">El carrito está vacío. ¡Llénalo sin miedo!</h2>
                <% } %>
                    <%-- Invalidamos la sesión --%>
                    <% sesion.invalidate(); %>
            <% } 
            //SI AÑADIMOS ALGO AL CARRITO
            else{%>
            <% //SI HEMOS HECHO ALGO MAL AL AÑADIR AL CARRITO
                if (request.getParameter("aniadir") != null && !mensajeError.equals("")) {%>
            <h2 class="error"><%= mensajeError%></h2>
            <%} //SI HEMOS AÑADIDO CORRECTAMENTE COSAS AL CARRITO
                else if (request.getParameter("aniadir") != null) {%>
            <h2 class="correcto">Se ha añadido al carrito <%= libro.getCantidad()%> unidad<%= plural%> de "<%= libro.getTitulo()%>"</h2>
            <%}%>
            <form id="formCarrito" method="post" action="Tienda.jsp" >
                <h3>Seleccione un libro y el número de unidades</h3>
                <ul>
                    <li><label for="libros">Títulos de libros</label><br/>              
                        <select name="titulo" size="6">
                            <option value="El señor de los anillos">El señor de los anillos</option>
                            <option value="El invierno del mundo">El invierno del mundo</option>
                            <option value="La biblia de Java">La biblia de Java</option>
                            <option value="El código Da Vinci">El código Da Vinci</option>
                            <option value="El Hobbit">El Hobbit</option>
                            <option value="Anatomía de un instante">Anatomía de un instante</option>
                        </select></li>
                        <li><label for="cantidad">Cantidad</label><br/><input type="text" name="cantidad" size="5" /></li>
                </ul>
                <p><input type="submit" name="aniadir" value="Añadir a la cesta"/>
                    <input type="reset" name="limpiar" value="Limpiar"/>
                    <input type="submit" name="finalizar" value="Finalizar la compra"/></p>
            </form>
            <% }%>
            <p><a class='enlace' href='../index.html'> -> Volver a inicio <- </a></p>
        </div>
        <footer id="pie">
            <p>
                Created by Ricardo Díaz - Octubre 2016 - 
            </p>
        </footer>
    </body>
</html>
