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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ricardo
 */
@WebServlet(name = "ContVisiSesiones", urlPatterns = {"/ContVisiSesiones"})
public class ContVisiSesiones extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        Integer contador = 0;
        HttpSession sesion = request.getSession(true);
       
        if(request.getParameter("invalidar") != null){
            //Cuando invalidamos ponemos el contador a 0 porque en ese momento no hay sesion
            sesion.invalidate();
        }else if(sesion.getAttribute("contador") == null){
            contador++;
            sesion.setAttribute("contador", contador);
        }else{            
            contador = (Integer) sesion.getAttribute("contador") + 1;
            sesion.setAttribute("contador", contador);
        }       
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Contador visitas sesiones</title>");
            out.println("<link rel='stylesheet' href='estilos/estilo.css' />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='contenedor'>");
            out.println("<h1>Contador de visitas con sesiones</h1>");
            out.println("<h2>Número de veces que ha visitado la página: <b>" +contador + "</b> </h2>");
            out.println("<form id='formulario' method='post' action='ContVisiSesiones'><p><b>Información de la cookie</b>");            
            out.println("<p><input type='checkbox' name='invalidar' value='Invalidar' /> Invalidar</p>");
            out.println("<p><input type='submit' name='refrescar' value='Refrescar' /></p></form>");
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
