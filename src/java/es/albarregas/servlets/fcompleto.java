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
        StringBuilder afi = new StringBuilder();
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
            out.println("<h1>Formulario completo</h1>");
            out.println("<div id='formulario'>");
            out.println("<h2>Datos del usario introducido </h2>");
            String[] aficiones = request.getParameterValues("aficiones");
            for(int i = 0; i < aficiones.length; i++){
                afi.append(aficiones[i]);
                if(i < aficiones.length -1){
                afi.append(", ");
                }
            }

            Enumeration<String> cabeceras = request.getParameterNames();
            String cabecera = "";
            out.println("<ul>");
            while(cabeceras.hasMoreElements()){
                cabecera = cabeceras.nextElement();
                if(!cabecera.startsWith("env") && !cabecera.startsWith("afi")){
                    out.println("<li><b>" +cabecera +": </b>" +request.getParameter(cabecera) +"</li>");
                }
            }
            
            out.println("<li><b>Aficiones: </b>" +afi +"</li>");
            out.println("</ul></div>");
            
            out.println("<p><a class='enlace' href='html/fcompleto.html'> -> Volver al formulario <- </a></p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
    
        }

    }
}