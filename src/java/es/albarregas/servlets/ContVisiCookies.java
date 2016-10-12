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

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("contador")) {
                    cookie = cookies[i];
                    break;
                }
            }
        }
        
        if(cookie == null){
            cookie = new Cookie("contador", "0");
        }
        
        if(request.getParameter("borrar") != null){
            cookie.setValue("0");
        } 

        contador = Integer.parseInt(cookie.getValue()) + 1;
        cookie.setValue(Integer.toString(contador));
        cookie.setMaxAge(604800); //Una semana en segundos
        response.addCookie(cookie);
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Contador visitas cookies</title>");
            out.println("<link rel='stylesheet' href='estilos/estilo.css' />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='contenedor'>");
            out.println("<h1>Contador de visitas con cookies</h1>");
            out.println("<h2>Número de veces que ha visitado la página: <b>" + cookie.getValue() + "</b> </h2>");
            out.println("<form id='formulario' method='post' action='ContVisiCookies'><p><b>Información de la cookie</b></p>");
            out.println("<p><b>-------------------------------</b></p><ul>");
            if(contador > 1){
               out.println("<li><b>Nombre: </b>" +cookie.getName() +"</li>");
               out.println("<li><b>Contenido: </b>" +cookie.getValue() +"</li>");
               out.println("<li><b>Dominio: </b>" +cookie.getDomain() +"</li>");
               out.println("<li><b>Path: </b>" +cookie.getPath() +"</li>");
               out.println("<li><b>Segura: </b>" +cookie.getSecure() +"</li>");
               out.println("<li><b>Versión: </b>" +cookie.getVersion() +"</li>");
            }
            
            out.println("</ul><p><input type='submit' name='recargar' value='Recargar' />");
            out.println("<input type='submit' name='borrar' value='Eliminar cookie' /></p></form>");
            out.println("<p><a class='enlace' href='index.html'>-> Volver al inicio <-</p></div>");
            out.println("</body>");
            out.println("</html>");
        }
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
