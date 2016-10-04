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
@WebServlet(name = "fcompleto", urlPatterns = {"/fcompleto"})
public class fcompleto extends HttpServlet {



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
            out.println("<li><a class='enlace' href='html/fsimple.html'> -> Volver al formulario</a></li>");
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
            out.println("<title>Formulario completo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='contenedor'>");
            out.println("<h1>Formulario simple</h1>");
            out.println("<div id='formulario'>");
            
            out.println("<h2>Datos personales</h2>");
                   
            out.println("<ol><li><b>Usuario:</b> " +request.getParameter("usuario") +"</li>"); 
            out.println("<li><b>Password:</b> " +request.getParameter("pass") +"</li>");           
            out.println("<li><b>Edad:</b> " +request.getParameter("edad") +"</li></ol>");
            
            out.println("<h2>Datos personales</h2>");
            out.println("<ol><li><b>Estado civil:</b> " +request.getParameter("ecivil") +"</li>");
            out.println("<li><b>Aficiones:</b> ");
            String[] aficiones = request.getParameterValues("aficiones");
            for(int i = 0; i < aficiones.length; i++){
                out.println(aficiones[i]);
                if(i < aficiones.length -1){
                out.println(", ");
                }
            }
            out.println("</li>");
            out.println("<li><b>Comentario:</b> " +request.getParameter("comentario") +"</li></ol>");

            out.println("<h2>Medios tecnológicos</h2>");
            out.println("<ol><li><b>Dispones de internet?: </b>" +request.getParameter("internet") +"</li>");
            out.println("<li><b>Sistema operativo: </b>" +request.getParameter("so") +"</li>");
            out.println("<li><b>Campo oculto: </b>" +request.getParameter("oculto") +"</li></ol>");
            out.println("</div>");
            
            out.println("<p><a class='enlace' href='html/fcompleto.html'> -> Volver al formulario <- </a></p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
    
        }

    }
}