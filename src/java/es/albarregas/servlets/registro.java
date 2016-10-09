/*  Programa en el que hay que subsanar errores al introducir datos en el formulario */

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
@WebServlet(name = "registro", urlPatterns = {"/registro"})
public class registro extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        //INICIALIZAMOS VARIABLES Y ASIGNAMOS VALORES POR DEFECTO
        String[] error = {"", "", "", ""}; //Inicializandolos a nada es más fácil evitar los NullPointerException
        boolean hayErrores = false;
        String nombre = request.getParameter("nombre");
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String dia = request.getParameter("dia");
        String mes = request.getParameter("mes");
        String anio = request.getParameter("anio");
        String apellidos = request.getParameter("apellidos");
        String sexo = request.getParameter("sexo");
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
            "Septiembre", "Octubre", "Noviembre", "Diciembre"}; //Meses del año para mostrar la fecha despues con formato
        //En aficiones recogemos el getParameterValues de aficiones
        String[] aficiones = null;
        //En afición recogemos que aficiones hay marcadas. 0 deporte, 1 cine, 2 lectura y 3 viaje
        String[] aficion = {"", "", "", ""};

        //Con este if nos garantizamos que no pete nada por recargar la página, ya que hacemos las cosas si venimos del post
        if (request.getParameter("nombre") != null) {
            if (request.getParameterValues("aficiones") != null) {
                aficiones = request.getParameterValues("aficiones");
            }

            //COMPROBAMOS SI ESTÁN PULSADOS LOS CHECKBOX
            for (int i = 0; (aficiones != null) && (i < aficiones.length); i++) {
                if (aficiones[i].equals("deporte")) {
                    aficion[0] = "checked";
                } else if (aficiones[i].equals("cine")) {
                    aficion[1] = "checked";
                } else if (aficiones[i].equals("lectura")) {
                    aficion[2] = "checked";
                } else if (aficiones[i].equals("viaje")) {
                    aficion[3] = "checked";
                }
            }

            //Recogemos los datos erroneos o sin introducir por el usuario
            for (int i = 0; i < error.length; i++) {
                error[i] = errorCampos(nombre, usuario, clave, dia, mes, anio)[i];
                if(!error[i].equals("")){
                    hayErrores = true;
                }
            }
        }

        //SI LE DAMOS AL BOTÓN LIMPIAR... nos lleva al registro.html
        if (request.getParameter("enviar") != null && request.getParameter("enviar").equals("Limpiar")) {
            response.sendRedirect("html/registro.html");
        } else {
            try (PrintWriter out = response.getWriter()) {
                //SI NO HAY ERRORES, MOSTRAMOS LOS DATOS DEL USUARIO
                out.println(inicio());
                if ((request.getParameter("enviar") != null && request.getParameter("enviar").equals("Enviar") && !hayErrores)) {

                    Enumeration<String> cabeceras = request.getParameterNames();
                    out.println("<div id='contenedor'>");
                    out.println("<h1>Datos del usuario registrado</h1>");
                    out.println("<ul>");
                    //Recorremos todos los datos del formulario para mostrarlos
                    while (cabeceras.hasMoreElements()) {
                        String cabecera = (String) cabeceras.nextElement();
                        //Si no es enviar ni aficiones mostramos en fila todo...
                        if (!cabecera.startsWith("env") && !cabecera.startsWith("afi")) {
                            if (cabecera.equals("dia")) {
                                out.println("<li><b>Fecha: </b> " + request.getParameter(cabecera));
                            } else if (cabecera.equals("mes")) {
                                /*Ponemos el més con el vector meses cuyo índice será el parametro mes pasado a entero
                                  Se le resta uno porque el primer indice de un vector es 0 */
                                out.println(" de " + meses[Integer.parseInt(request.getParameter(cabecera)) - 1]);
                            } else if (cabecera.equals("anio")) {
                                out.println(" de " + request.getParameter(cabecera) + "</li>");
                            } else if (!cabecera.equals("mes") && !cabecera.equals("anio")) {
                                String etiqueta = request.getParameter(cabecera);
                                out.println("<li><b>" + cabecera + ": </b>" + etiqueta + "</li>");
                            }

                        }
                    }
                    
                    //Las aficiones las mostramos aparte ya que tienen una naturaleza diferente (es una array)
                    out.println("<li><b>Aficiones: </b>");
                    for (int i = 0; aficiones != null && i < aficiones.length; i++) {
                        out.println(aficiones[i]);
                        if (i < aficiones.length - 1) {
                            out.println(", "); //añadimos coma al final de cada aficion
                        }
                    }
                    //Si el usuario no tiene aficiones...
                    if(aficiones == null || aficiones.length < 1){
                        out.println("El usuario no tiene aficiones.");
                    }
                    out.println("</li>");
                            
                    out.println("</ul>");
                    out.println("<p><a class='enlace' href='index.html'> -> Volver al inicio <- </a></p>");
                    out.println("</div>");
                    //COMENZAMOS A ESCRIBIR EL TEXTO DE LA PÁGINA DE ERROR DE FORMULARIO
                } else if (request.getParameter("enviar") != null && request.getParameter("enviar").equals("Volver al formulario")) {
                    out.println("<div id='contenedor'>");
                    out.println("<h1>Formulario registro</h1>");
                    out.println("<form id='formulario' method='post' action='registro'>");

                    //SI HAY ERROR MOSTRAMOS CUAL ES                 
                    for (int i = 0; i < error.length; i++) {
                        if (error[i].equals("fecha")) {
                            out.println("<h2>Fecha incorrecta</h2>");
                        } else if (!error[i].equals("")) {
                            out.println("<h2>Introduzca " + error[i] + "</h2>");
                        }
                    }

                    out.println("<fieldset>");
                    out.println("<legend>Informaci&oacute;n personal</legend>");
                    out.println("<ul>");
                    out.println("<li><span class='obligatorio'>*&nbsp;</span><label>Nombre</label><input type='text' name='nombre' value='" + nombre + "'/>");
                    //Si nombre es vacio ponemos una X al lado para dejarselo claro al usuario
                    if (error[0].equals("nombre")) {
                        out.println("<span class='marcado'>X</span>");
                    }

                    out.println("</li>");
                    out.println("<li><label>Apellidos</label><input type='text' name='apellidos' value='" + apellidos + "'/></li>");
                    if (sexo.equals("mujer")) {
                        out.println("<li><label>Sexo</label><input type='radio' name='sexo' value='hombre'  />Hombre");
                        out.println("<input type='radio' name='sexo' value='mujer' checked />Mujer");
                    } else {
                        out.println("<li><label>Sexo</label><input type='radio' name='sexo' value='hombre' checked />Hombre");
                        out.println("<input type='radio' name='sexo' value='mujer' />Mujer");
                    }
                    out.println("</li>");

                    //FECHA DE NACIMIENTO
                    out.println("<li>");
                    out.println("<label>Fecha nacimiento</label>");
                    out.println("<select name='dia' size='1' >");

                    //Introducimos los días. 
                    for (int i = 1; i < 32; i++) {
                        out.println("<option value='" + i + "'");
                        //Si el día guardado es el actual, lo checkeamos porque vendrá de atrás
                        if (Integer.parseInt(dia) == i) {
                            out.println("selected");
                        }
                        out.println(" >" + i + "</option>");
                    }
                    out.println("</select>&nbsp;/");

                    out.println("<select name='mes' size='1' >");

                    //Introducimos los meses
                    for (int i = 1; i < 13; i++) {
                        out.println("<option value='" + i + "'");
                        //Si el mes guardado es el actual, la checkeamos porque vendrá de atrás
                        if (Integer.parseInt(mes) == i) {
                            out.println("selected");
                        }
                        out.println(" >" + i + "</option>");
                    }
                    out.println("</select>&nbsp;/");
                    out.println("<select name='anio' size='1' >");

                    //Introducimos los años
                    for (int i = 1983; i < 1999; i++) {
                        out.println("<option value='" + i + "'");
                        //Si el año guardado es el actual, la checkeamos porque vendrá de atrás
                        if (Integer.parseInt(anio) == i) {
                            out.println("selected");
                        }
                        out.println(" >" + i + "</option>");
                    }
                    out.println("</select>");
                    //Si fecha es error ponemos una X al lado para dejarselo claro al usuario
                    if (error[1].equals("fecha")) {
                        out.println("<span class='marcado'>X</span>");
                    }

                    out.println("</li>");
                    out.println("</ul>");
                    out.println("</fieldset>");

                    out.println("<fieldset>");
                    out.println("<legend>Datos de acceso</legend>");
                    out.println("<ul>");
                    out.println("<li><span class='obligatorio'>*&nbsp;</span><label>Usuario</label><input type='text' name='usuario' value='" + usuario + "'/>");
                    //Si usuario es vacio ponemos una X al lado para dejarselo claro al usuario
                    if (error[2].equals("usuario")) {
                        out.println("<span class='marcado'>X</span>");
                    }

                    out.println("</li>");
                    out.println("<li><span class='obligatorio'>*&nbsp;</span><label>Clave</label><input type='password' name='clave'/>");
                    //Si clave es vacio ponemos una X al lado para dejarselo claro al usuario
                    if (error[3].equals("clave")) {
                        out.println("<span class='marcado'>X</span>");
                    }

                    out.println("</li>");

                    out.println("</ul>");
                    out.println("</fieldset>");
                    out.println("<fieldset>");
                    out.println("<legend>Informaci&oacute;n general</legend>");
                    out.println("");
                    //Checkeamos las aficiones que estuvieran marcadas gracias a nuestro vector que nos sirve de guía... recogiendo el checked o nada en cada posición
                    out.println("<label>Preferencias</label>");
                    out.println("<ul><li><input type='checkbox' name='aficiones' value='deporte' " + aficion[0] + " /> Deporte</li>");
                    out.println("<li><input type='checkbox' name='aficiones' value='lectura' " + aficion[1] + " /> Lectura</li>");
                    out.println("<li><input type='checkbox' name='aficiones' value='cine' " + aficion[2] + " /> Cine</li>");
                    out.println("<li><input type='checkbox' name='aficiones' value='viaje' " + aficion[3] + " /> Viaje</li></ul>");
                    out.println("</fieldset>");

                    out.println("<p><input type='submit' name='enviar' value='Enviar'>");
                    out.println("<input type='submit' name='enviar' value='Limpiar'> </p>");
                    out.println("</form>");

                    out.println("<p><a class='enlace' href='index.html'> -> Volver al inicio <- </a></p>");

                    out.println("</div>");

                    //Si no mostramos los datos ni vamos al formulario, es que se han enviado los datos y hay error
                } else {
                    out.println("<div id='contenedor'>");
                    out.println("<h1>Hay errores en el formulario</h1>");
                    out.println("<form id='formulario' method='post' action='registro'>");
                    out.println("<input");
                    
                    //Ponemos un formulario oculto con todos los datos que queremos devolver hacia atrás usando campos hidden
                    out.println("<input type='hidden' name='nombre' value='" + nombre + "'/>");
                    out.println("<input type='hidden' name='apellidos' value='" + apellidos + "'/>");
                    out.println("<input type='hidden' name='sexo' value='" + sexo + "'/>");
                    out.println("<input type='hidden' name='dia' value='" + dia + "'/>");
                    out.println("<input type='hidden' name='mes' value='" + mes + "'/>");
                    out.println("<input type='hidden' name='anio' value='" + anio + "'/>");
                    out.println("<input type='hidden' name='usuario' value='" + usuario + "'/>");
                    out.println("<input type='hidden' name='clave' value='" + clave + "'/>");

                    //Ponemos todas las aficiones marcadas, como las hemos recogidos con el request, será más fácil añadirlas
                    for (int i = 0; (aficiones != null) && (i < aficiones.length); i++) {
                        out.println("<input type='hidden' name='aficiones' value='" + aficiones[i] + "'/>");
                    }
                    out.println("<p><input type='submit' name='enviar' value='Volver al formulario'/></p>");
                    out.println("</form>");
                    out.println("</div>");
                }
                out.println(fin());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Si entramos mal, es decir, recargando la página, teniendo en cuenta que estamos en un servlet, redirigimos al html para evitar problemas.
        response.sendRedirect("html/registro.html");
    }

//Método  que saca el método de la cabecera de la página
    private StringBuilder inicio() {
        StringBuilder cabecera = new StringBuilder();
        cabecera.append("<!DOCTYPE html>");
        cabecera.append("<html>");
        cabecera.append("<head>");
        cabecera.append("<title>Registro</title>");
        cabecera.append("<meta charset='utf-8'>");
        cabecera.append("<link rel='stylesheet' href='estilos/estilo.css' />");
        cabecera.append("</head>");
        cabecera.append("<body>");
        return cabecera;
    }

//Método que saca el final de la cabecera de la página    
    private StringBuilder fin() {
        StringBuilder pie = new StringBuilder();
        pie.append("</body>");
        pie.append("</html>");
        return pie;
    }

//Método que devuelve si hay algún campo vacío o la fecha está mal
    private String[] errorCampos(String nombre, String usuario, String clave, String dia, String mes, String anio) {
        String[] fallo = {"", "", "", ""}; //Inicializandolo a nada es más fácil evitar los NullPointerException

        if (nombre != null && nombre.equals("")) {
            fallo[0] = "nombre";
        }
        if (usuario != null && usuario.equals("")) {
            fallo[2] = "usuario";
        }
        if (clave != null && clave.equals("")) {
            fallo[3] = "clave";
        }
        if (fecha(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(anio))) {
            fallo[1] = "fecha";
        }

        return fallo;
    }

// Método que devuelve si la fecha dada en día, mes y año es correcta o no mediante un booleano
    private boolean fecha(int dia, int mes, int anio) {
        boolean error = false;
        int bisiesto = 0;

        if (dia < 1 || dia > 31 || mes < 1 || mes > 12) {
            error = true;
        } else if (mes == 2) {
            if ((anio % 100 != 0 || anio % 400 == 0) && anio % 4 == 0) {
                bisiesto = 1;
            }

            if (dia > (28 + bisiesto)) {
                error = true;
            }
        } else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
            error = true;
        }

        return error;
    }
}
