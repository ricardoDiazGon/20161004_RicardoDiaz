// Formulario en el que cuando introducimos los datos podemos volver para atrás a rectificarlos o guardarlos
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String nombre = "";
        String contrasenia = "";
        String sexo = "hombre"; //El sexo por defecto es hombre
        String[] aficion = {"", "", "", ""}; //Este array recogerá con "checked" o nada ("") si cada aficion ha sido o no checkeada.
        Enumeration<String> cabeceras = request.getParameterNames();
        String cabecera = "";
        String[] afi = request.getParameterValues("aficiones");
        StringBuilder aficiones = new StringBuilder();
        
        //Si pulsamos limpiar tendremos que inicializar todos los campos a en blanco, lo hacemos con las variables.
        if(request.getParameter("enviar") != null && !request.getParameter("enviar").equals("Limpiar")){
            nombre = (request.getParameter("nombre") != null) ? request.getParameter("nombre") : "";
            contrasenia = (request.getParameter("contrasenia") != null) ? request.getParameter("contrasenia") : "";
            sexo = (request.getParameter("sexo") != null) ? request.getParameter("sexo") : "";
            for (int i = 0; (afi != null) && (i < afi.length); i++) {
                aficiones.append(afi[i]);
                if (i < afi.length - 1) {
                    aficiones.append(", ");
                }

                //Comprobamos qué aficiones están chequeadas
                if (afi[i].equals("deporte")) {
                    aficion[0] = "checked";
                } else if (afi[i].equals("programar")) {
                    aficion[1] = "checked";
                } else if (afi[i].equals("cine")) {
                    aficion[2] = "checked";
                } else if (afi[i].equals("leer")) {
                    aficion[3] = "checked";
                }
            }
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println(inicioPagina());
            
            //Si hemos venido de fuera (null) o le damos a Limpiar o Atras, tenemos que mostrar el formulario
            if (request.getParameter("enviar") == null || request.getParameter("enviar").equals("Limpiar")
                    || request.getParameter("enviar").equals("Atras")) {

                out.println("<h1>Introduzca los datos</h1>");
                out.println("<form id='formulario' method='post' action='fcorrecto'>");
                out.println("<legend>Datos personales</legend>");
                out.println("<fieldset>");
                out.println("<ul>");
                out.println("<li><label>Nombre</label>" + "<input type='text' name='nombre' value='" + nombre + "'/></li>");
                out.println("<li><label>Contraseña</label>" + "<input type='password' name='contrasenia' value='" + contrasenia + "'/></li>");
                //Ponemos el sexo, si se ha checkeado anteriormente mujer, se checkeará, sino por defecto el hombre
                if (sexo.equals("mujer")) {
                    out.println("<li><label>Sexo</label>"
                            + "<input type='radio' name='sexo' value='hombre'/>&nbsp; Nombre"
                            + "<input type='radio' name='sexo' value='mujer' checked/>&nbsp; Mujer</li>");
                } else {
                    out.println("<li><label>Sexo</label>"
                            + "<input type='radio' name='sexo' value='hombre' checked/>&nbsp; Nombre"
                            + "<input type='radio' name='sexo' value='mujer'/>&nbsp; Mujer</li></ul>");
                }

                //Ponemos las aficiones, si cada una está checkeada con el vector aficion[] las chechearemos, ya que guardará "checked"
                out.println("<label>Aficiones:</label><ul><li><input type='checkbox' name='aficiones' value='deporte' " + aficion[0] + " /> deporte</li>"
                        + "<li><input type='checkbox' name='aficiones' value='programar' " + aficion[1] + " /> programar</li>"
                        + "<li><input type='checkbox' name='aficiones' value='cine' " + aficion[2] + " /> cine</li>"
                        + "<li><input type='checkbox' name='aficiones' value='leer' " + aficion[3] + " />leer</li></ul>");
                out.println("</fieldset>");
                out.println("<p><input type='submit' name='enviar' value='Enviar' />");
                out.println("<input type='submit' name='enviar' value='Limpiar' /></p></form>");
            //Si damos a enviar los datos, los mostramos en otra pantalla, donde pedimos guardarlos o rectificarlos volviendo a atras
            } else if (request.getParameter("enviar").equals("Enviar")) {
                out.println("<h1>Datos enviados con el formulario</h1>");
                out.println("<form id='formulario' method='post' action='fcorrecto'>");
                out.println("<ul>");
                while (cabeceras.hasMoreElements()) {
                    cabecera = cabeceras.nextElement();
                    //Si no es enviar ni aficiones
                    if (!cabecera.startsWith("env") && !cabecera.startsWith("afi")) {
                        out.println("<li><b>" + cabecera + ": </b>" + request.getParameter(cabecera) + "</li>");
                        out.println("<li><input type='hidden' name='" + cabecera + "' value='" + request.getParameter(cabecera) + "'/>");
                    }
                }
                
                //Ponemos las aficiones, si no están marcadas, el criterio es dejarlas en blanco
                out.println("<li><b>Aficiones: </b> " + aficiones + "</li>");
                for (int i = 0; (afi != null) && (i < afi.length); i++) {
                    out.println("<input type='hidden' name='aficiones' value='" + afi[i] + "' />");
                }
                out.println("</ul>");
                out.println("<p><input type='submit' name='enviar' value='Atras' />");
                out.println("<input type='submit' name='enviar' value='Guardar' /></p>");
                out.println("</form>");
                
            //Si no hemos pasado por ninguna de las opciones anteriores es que hemos pulsado guardar los datos del usuario
            } else {
                cabeceras = request.getParameterNames();
                out.println("<h1>Los datos del usuario " + nombre.toUpperCase() + " han sido guardados</h1><ul>");
                
                //Recorremos todos los datos del usuario para mostrarlo
                while (cabeceras.hasMoreElements()) {
                    cabecera = cabeceras.nextElement();
                    if (!cabecera.startsWith("env") && !cabecera.startsWith("afi")) {
                        out.println("<li><b>" + cabecera + ": </b>" + request.getParameter(cabecera) + "</li>");
                    }
                }
                out.println("<li><b>Aficiones: </b>" + aficiones + "</li></ul>");

            }
            out.println(finPagina());
        }
    }

    //Inicio de la página en un método para no tener duplicidades
    private StringBuilder inicioPagina() {
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

    //Fin de la página en un método para no tener duplicidades
    private StringBuilder finPagina() {
        StringBuilder fin = new StringBuilder();
        fin.append("<p><a class='enlace' href='index.html'> -> Volver al Inicio <- </a></p>");
        fin.append("</div></body>");
        fin.append("</html>");

        return fin;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Si entramos por doGet redirigimos a doPost
        doPost(request, response);

    }

}
