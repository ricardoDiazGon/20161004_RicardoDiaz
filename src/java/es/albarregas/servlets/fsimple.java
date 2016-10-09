/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ricardo
 */
@WebServlet(name = "fsimple", urlPatterns = {"/fsimple"})
public class fsimple extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='estilos/estilo.css'/>");
            out.println("<title>Formulario simple</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='contenedor'>");
            out.println("<h1>No hemos pasado por el formulario</h1>");
            out.println("<p><a class='enlace' href='html/fsimple.html'> -> Volver al formulario</a></p>");
            out.println("</div>)");
            out.println("</body>");
            out.println("</html>");
        } 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<link rel='stylesheet' href='estilos/estilo.css'/>");
            out.println("<title>Formulario simple</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='contenedor'>");
            out.println("<h1>Formulario simple</h1>");
            out.println("<div id='formulario'>");
            Enumeration<String> cabeceras = request.getParameterNames();
            String cabecera = "";
            String marcado = "no est&aacute; marcado";
            
            out.println("<ul>");
            while(cabeceras.hasMoreElements()){
                cabecera = cabeceras.nextElement();
                if(!cabecera.startsWith("env") && !cabecera.startsWith("marc")){
                    out.println("<li><b>" +cabecera + ": </b>" +request.getParameter(cabecera) +"</li>");
                }
                
                if(cabecera.equals("marcado")){
                    marcado = "est&aacute; marcado";
                }
            }
            out.println("<li><b>El checkbox</b> " +marcado +"</li>");
            out.println("</ul>");
         
                
            out.println("</div>");
            
            out.println("<p><a class='enlace' href='html/fsimple.html'> -> Volver al formulario <- </a></p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } 
    }
}