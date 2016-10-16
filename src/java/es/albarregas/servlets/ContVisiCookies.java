/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ricardo
 */
@WebServlet(name = "ContVisiCookies", urlPatterns = {"/ContVisiCookies"})
public class ContVisiCookies extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        int contador = 0;

        // si hay cookies usadas por este servidor las recorremos para ver si está la que queremos encontrar (contador)        
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("contador")) {
                    cookie = cookies[i];
                    break; //Cuando la encontramos salimos de la búsquerda-bucle
                }
            }
        }

        //Si no existe la cookie que queremos encontrar la creamos y la inicializamos a 0
        if (cookie == null) {
            cookie = new Cookie("contador", "0");
        }
        //Si el usuario ha pulsado el botón borrar cookie le damos el valor inicial (0)
        if (request.getParameter("borrar") != null) {
            cookie.setValue("0");
        }

        //Exista el valor que exista en la cookie, lo actualizamos a +1 y volvemos a guardarlo
        contador = Integer.parseInt(cookie.getValue()) + 1;
        cookie.setValue(Integer.toString(contador));
        cookie.setMaxAge(604800); //La vida de la cookie será una semana (Dada en segundos)
        response.addCookie(cookie);

        try (PrintWriter out = response.getWriter()) {

            out.println(inicioPagina());
            out.println("<h1>Contador de visitas con cookies</h1>");
            /* 
                Aquí si que podemos poner cookie.getValue() (en diferencia de las sesiones) en vez de la variabe contador, 
                porque nunca va a dar null, ya que cuando no existe la cookie, siempre la creamos nosotros y le damos un valor.
             */
            out.println("<h2>Número de veces que ha visitado la página: <b>" + cookie.getValue() + "</b> </h2>");
            out.println("<form id='formulario' method='post' action='ContVisiCookies'><p><b>Información de la cookie</b></p>");
            out.println("<p><b>-------------------------------</b></p><ul>");
            if (contador > 1) {
                out.println("<li><b>Nombre: </b>" + cookie.getName() + "</li>");
                out.println("<li><b>Contenido: </b>" + cookie.getValue() + "</li>");
                out.println("<li><b>Dominio: </b>" + cookie.getDomain() + "</li>");
                out.println("<li><b>Path: </b>" + cookie.getPath() + "</li>");
                out.println("<li><b>Segura: </b>" + cookie.getSecure() + "</li>");
                out.println("<li><b>Versión: </b>" + cookie.getVersion() + "</li>");
            }

            out.println("</ul><p><input type='submit' name='recargar' value='Recargar' />");
            out.println("<input type='submit' name='borrar' value='Eliminar cookie' /></p></form>");
            out.println(finPagina());
        }
    }

    private StringBuilder inicioPagina() {
        StringBuilder inicio = new StringBuilder();
        inicio.append("<!DOCTYPE html>");
        inicio.append("<html>");
        inicio.append("<head>");
        inicio.append("<title>Contador visitas sesiones</title>");
        inicio.append("<link rel='stylesheet' href='estilos/estilo.css' />");
        inicio.append("</head>");
        inicio.append("<body>");
        inicio.append("<div id='contenedor'>");
        return inicio;
    }

    private StringBuilder finPagina() {
        StringBuilder fin = new StringBuilder();
        fin.append("<p><a class='enlace' href='index.html'>-> Volver al inicio <-</a></p></div>");
        fin.append("<footer id='pie'><p>Created by Ricardo Díaz - Octubre 2016 -</p></footer></body></html>");
        return fin;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
