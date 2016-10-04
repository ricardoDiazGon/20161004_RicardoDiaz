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
@WebServlet(name = "fcorrecto", urlPatterns = {"/fcorrecto"})
public class fcorrecto extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = "";
        String contrasenia = "";
        String sexo = "hombre";
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        Enumeration<String> cabeceras = request.getParameterNames();
        if(cabeceras != null){
            nombre = request.getParameter("nombre");
            contrasenia = request.getParameter("contrasenia");
            sexo = request.getParameter("sexo");
            String[] afi = request.getParameterValues("aficiones");
            StringBuilder aficiones = null;
            for(int i = 0; i < afi.length; i++){
                aficiones.append(afi[i]);
                if(i < afi.length - 1)
                    aficiones.append(',');           
            }
        }
        try (PrintWriter out = response.getWriter()) {     
            if(request.getParameter("enviar") == null || request.getParameter("enviar").equals("Limpiar")
            || request.getParameter("enviar").equals("atras")){
                nombre = request.getParameter("nombre");
                contrasenia = request.getParameter("contrasenia");
                out.println(inicioPagina());
                out.println("<h1>Introduzca los datos</h1>");
                out.println("<form id='formulario' method='post' action='./fcorrecto'>");
                out.println("<legend>Datos personales</legend>");
                out.println("<fieldset>");
                out.println("<ul>");
                out.println("<li><label>Nombre</label>" + "<input type='text' name='nombre' value='"  +nombre +"'/></li>");
                out.println("<li><label>Contraseña</label>" + "<input type='password' value='" +contrasenia +"'/></li>");
            if(sexo.equals("mujer")){
                out.println("<li><label>Sexo</label>"
                        + "<input type='radio' name='sexo' value='hombre'/>&nbsp; Nombre" 
                        + "<input type='radio' name='sexo' value='mujer' checked/>&nbsp; Mujer</li>");
            }else{
                out.println("<li><label>Sexo</label>"
                        + "<input type='radio' name='sexo' value='hombre' checked/>&nbsp; Nombre" 
                        + "<input type='radio' name='sexo' value='mujer'/>&nbsp; Mujer</li>");    
            }
            out.println("<li><label>Aficiones:</label>deporte<input type='checkbox' name='aficiones' value='deporte'/> deporte"
                    + "<input type='checkbox' name='aficiones' value='programar' checked/> programar"
                    + "<input type='checkbox' name='aficiones' value='cine'/> cine"
                    + "<input type='checkbox' name='aficiones' value='leer'/></li> leer" );
            out.println("</ul>");
            out.println("</fieldset>");
            out.println("<p><input type='submit' name='enviar' value='Enviar' />");
            out.println("<input type='submit' name='enviar' value='Limpiar' /></p></form>");
            out.println(finPagina());
            } else if(request.getParameter("enviar").equals("Enviar")){
                out.println(inicioPagina());
                out.println("<form id='formulario' method='post' action='./fcorrecto'>");
                out.println("<input type='submit' name='enviar' value='Atras' />");
                out.println("<input type='submit' name='guardar' value='Guardar' />");
                out.println("</form>");
                out.println(finPagina());
            }
        }                              

    }

    private StringBuilder inicioPagina(){
        StringBuilder inicio = new StringBuilder();
        inicio.append("<!DOCTYPE html>");
        inicio.append("<html>");
        inicio.append("<head>");
        inicio.append("<title>Formulario correcto</title>");
        inicio.append("<meta lang='es' charset='UTF-8'>");
        inicio.append("<link rel='stylesheet' href='estilos/estilo.css'");
        inicio.append("</head>");
        inicio.append("<body><div id='contenedor'>");        
        return inicio;
    }
    
    private StringBuilder finPagina(){
        StringBuilder fin = new StringBuilder(); 
        fin.append("<p><a class='enlace' href='index.html'> -> Volver al Inicio <- </a></p>");
        fin.append("</div></body>");
        fin.append("</html>");

        return fin;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

}
