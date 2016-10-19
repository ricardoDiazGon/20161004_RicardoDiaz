/*
    Actividad en la que hay un formulario en la que se pide introducir varios usuarios (3 para ser más exactos) 
    a la vez y guardarlos en un ArrayList a través de un bean en una sesión. El Bean es Usuario.java
*/

package es.albarregas.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import es.albarregas.beans.Usuario;
import java.util.Iterator;

@WebServlet(name = "UsuariosALaVez", urlPatterns = {"/UsuariosALaVez"})
public class UsuariosALaVez extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(inicioPagina());

            if (request.getParameter("enviar") == null && request.getParameter("mostrar") == null) {
                //Para mostrar el formulario de introducción de datos
                out.println("<h1>Introduce los tres usuarios</h1>");
                out.println("<form id='formulario' method='post' action='UsuariosALaVez'>");
                out.println("<fieldset><legend>Datos personales Usuario1</legend><ul>");
                out.println("<li><label for='id1'>Id</label><input type='text' size='6' name='id1' /></li>");
                out.println("<li><label for='nombre1'>Nombre</label><input type='text' name='nombre1' /></li>");
                out.println("<li><label for='salario1'>Salario</label><input type='text' name='salario1' /></li>");
                out.println("</ul></fieldset>");
                out.println("<fieldset><legend>Datos personales Usuario2</legend><ul>");
                out.println("<li><label for='id2'>Id</label><input type='text' size='6' name='id2' /></li>");
                out.println("<li><label for='nombre2'>Nombre</label><input type='text' name='nombre2' /></li>");
                out.println("<li><label for='salario1'>Salario</label><input type='text' name='salario2' /></li>");
                out.println("</ul></fieldset>");
                out.println("<fieldset><legend>Datos personales Usuario2</legend><ul>");
                out.println("<li><label for='id3'>Id</label><input type='text' size='6' name='id3' /></li>");
                out.println("<li><label for='nombre3'>Nombre</label><input type='text' name='nombre3' /></li>");
                out.println("<li><label for='salario3'>Salario</label><input type='text' name='salario3' /></li>");
                out.println("</ul></fieldset>");
                out.println("<p><input type='submit' name='enviar' value='Enviar' /><input type='reset' name='limpiar' value='Limpiar' /></p>");
                out.println("</form>");
            } else if (request.getParameter("mostrar") == null) {
                //Para mostrar que los datos han sido guardados
                HttpSession sesion = request.getSession();
                

            } else {
                //Para mostrar los datos del usuario
                HttpSession sesion = request.getSession(true);
                ArrayList<Usuario> usuarios = (ArrayList) sesion.getAttribute("usuarios");
                Iterator<Usuario> it = usuarios.iterator();
                out.println(finPagina());
                int i = 0;
                while(it.hasNext()){
                    Usuario usuario = it.next();
                    //Datos del usuario
                }
                
                sesion.invalidate();
            }
        }
    }

    private StringBuilder inicioPagina() {
        StringBuilder inicio = new StringBuilder();
        inicio.append("<!DOCTYPE html>");
        inicio.append("<html>");
        inicio.append("<head>");
        inicio.append("<title>Varios usuarios a la vez</title>");
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
