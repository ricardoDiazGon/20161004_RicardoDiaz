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

/**
 *
 * @author Ricardo
 */
@WebServlet(name = "fsimple", urlPatterns = {"/fsimple"})
public class fsimple extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
            out.println("<ol><li><b>Nombre:</b> " +request.getParameter("nombre") +"</li>"); 
            out.println("<li><b>Email:</b> " +request.getParameter("email") +"</li>");           
            if(request.getParameter("marcado") != null){
                out.println("<li><b>El checkbox est&aacute; marcado</b></li>");
            }else{
                out.println("<li><b>El checkbox no est&aacute; marcado</b></li></ol>");
            }
            out.println("</div>");
            
            out.println("<p><a class='enlace' href='html/fsimple.html'> -> Volver al formulario <- </a></p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } 
    }
}