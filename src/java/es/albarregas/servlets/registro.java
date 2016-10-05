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
@WebServlet(name = "registro", urlPatterns = {"/registro"})
public class registro extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        //INICIALIZAMOS VARIABLES Y ASIGNAMOS VALORES POR DEFECTO
        String error = "";
        String nombre = request.getParameter("nombre");
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String dia = request.getParameter("dia");
        String mes = request.getParameter("mes");
        String anio = request.getParameter("anio");
        String apellidos = request.getParameter("apellidos");
        String sexo = request.getParameter("sexo");
        String[] aficiones = null;
        if(request.getParameterValues("aficiones") != null){
            aficiones = request.getParameterValues("aficiones");
        }
        String deporte = "";
        String cine = "";
        String lectura = "";
        String viaje = "";
        
        //COMPROBAMOS SI ESTÁN PULSADOS LOS CHECKBOX
        
        for(int i = 0; (aficiones != null) && (i < aficiones.length); i++){
            if(aficiones[i].equals("deporte")){
                deporte = "checked";
            }else if(aficiones[i].equals("cine")){
                cine = "checked";
            }else if(aficiones[i].equals("lectura")){               
                lectura = "checked";   
            }
            else if(aficiones[i].equals("viaje")){
                viaje = "checked";
            }
        }
        
   
        //Comprobamos fecha
        error = errorCampos(nombre, usuario, clave, dia, mes, anio);
      
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        //SI LE DAMOS AL BOTÓN LIMPIAR... nos lleva al registro.html
        if(request.getParameter("enviar").equals("Limpiar")){
            response.sendRedirect("html/registro.html");
        }else{ 
            
            try (PrintWriter out = response.getWriter()) {
                
                if(error.equals("")){  //SI NO HAY ERRORES, MOSTRAMOS LOS DATOS DEL USUARIO
                    
                    Enumeration<String> cabeceras = request.getParameterNames();
                    
                    out.println(inicio());
                    out.println("<div id='contenedor'>");
                    out.println("<h1>Datos del usuario registrado</h1>");
                    out.println("<ul>");
                    while(cabeceras.hasMoreElements()){
                        String cabecera = (String) cabeceras.nextElement();
                        if(!cabecera.startsWith("env")){
                            
                            if(cabecera.startsWith("afi")){
                                out.println("<li><b>" +cabecera +": </b>");
                                for(int i = 0; i < aficiones.length; i++){
                                    out.println(aficiones[i]);
                                    if(i < aficiones.length -1 )
                                        out.println(", ");
                                }
                                out.println("</li>");
                            }else if(cabecera.equals("dia")){
                                    out.println("<li><b>Fecha: </b> " +request.getParameter(cabecera));
                            }else if(cabecera.equals("mes")){
                                    out.println(" / " +request.getParameter(cabecera));
                            }else if(cabecera.equals("anio")){
                                    out.println(" / " +request.getParameter(cabecera) + "</li>");
                            }else if(!cabecera.equals("mes") && !cabecera.equals("anio") ){
                                String etiqueta = request.getParameter(cabecera);
                                out.println("<li><b>" +cabecera +": </b>" +etiqueta +"</li>");
                            }
                            
                        }
                    }
                    out.println("</ul>");
                    out.println("<p><a class='enlace' href='index.html'> -> Volver al inicio <- </a></p>");                    
                    out.println("</div>");
                    out.println(fin());
                    
                }else{ //COMENZAMOS A ESCRIBIR EL TEXTO DE LA PÁGINA DE ERROR DE FORMULARIO

                    out.println(inicio());
                    
                    out.println("<div id='contenedor'>");
                        out.println("<h1>Formulario registro</h1>");
                        out.println("<form id='formulario' method='post' action='registro'>");
                        
                        //SI HAY ERROR MOSTRAMOS CUAL ES
                            if(error.equals("fecha")){
                                out.println("<h2>Error: Fecha incorrecta</h2>");
                            }else{
                                out.println("<h2>Error: Introduzca " +error +"</h2>");
                            }
                            
                        
                            out.println("<fieldset>");
                                out.println("<legend>Informaci&oacute;n personal</legend>");
                            out.println("<ul>");
                                out.println("<li><span class='obligatorio'>*&nbsp;</span><label>Nombre</label><input type='text' name='nombre' value='" +nombre +"'/>");
                                if(error.equals("nombre")){
                                    out.println("<span class='marcado'>X</span>");
                                }
   
                                out.println("</li>");
                                 out.println("<li><label>Apellidos</label><input type='text' name='apellidos' value='" + apellidos +"'/></li>");
                                 if(sexo.equals("mujer")){
                                    out.println("<li><label>Sexo</label><input type='radio' name='sexo' value='hombre'  />Hombre");
                                    out.println("<input type='radio' name='sexo' value='mujer' checked />Mujer");
                                 }else{
                                    out.println("<li><label>Sexo</label><input type='radio' name='sexo' value='hombre' checked />Hombre");
                                    out.println("<input type='radio' name='sexo' value='mujer' />Mujer");          
                                 }
                                  out.println("</li>");
                                  
                                    //FECHA DE NACIMIENTO
                                  out.println("<li>");
                                     out.println("<label>Fecha nacimiento</label>");
                                     out.println("<select name='dia' size='1' >");
                                         out.println("<option value='1' selected>1</option>");
                                         out.println("<option value='2'>2</option>");
                                        out.println("<option value='3'>3</option>");
                                         out.println("<option value='4'>4</option>");
                                         out.println("<option value='5'>5</option>");
                                         out.println("<option value='6'>6</option>");
                                         out.println("<option value='7'>7</option>");
                                         out.println("<option value='8'>8</option>");
                                         out.println("<option value='9'>9</option>");
                                         out.println("<option value='10'>10</option>");
                                         out.println("<option value='11'>11</option>");
                                         out.println("<option value='12'>12</option>");
                                         out.println("<option value='13'>13</option>");
                                         out.println("<option value='14'>14</option>");
                                         out.println("<option value='15'>15</option>");
                                         out.println("<option value='16'>16</option>");
                                         out.println("<option value='17'>17</option>");
                                         out.println("<option value='18'>18</option>");
                                         out.println("<option value='19'>19</option>");
                                         out.println("<option value='20'>20</option>");
                                         out.println("<option value='21'>21</option>");
                                         out.println("<option value='22'>22</option>");
                                         out.println("<option value='23'>23</option>");
                                         out.println("<option value='24'>24</option>");
                                         out.println("<option value='25'>25</option>");
                                         out.println("<option value='26'>26</option>");
                                         out.println("<option value='27'>27</option>");
                                         out.println("<option value='28'>28</option>");
                                         out.println("<option value='29'>29</option>");
                                         out.println("<option value='30'>30</option>");
                                         out.println("<option value='31'>31</option>");
                                     out.println("</select>&nbsp/");
                                     out.println("<select name='mes' size='1' >");
                                         out.println("<option value='1' selected>1</option>");
                                         out.println("<option value='2'>2</option>");
                                         out.println("<option value='3'>3</option>");
                                         out.println("<option value='4'>4</option>");
                                         out.println("<option value='5'>5</option>");
                                         out.println("<option value='6'>6</option>");
                                         out.println("<option value='7'>7</option>");
                                         out.println("<option value='8'>8</option>");
                                         out.println("<option value='9'>9</option>");
                                        out.println("<option value='10'>10</option>");
                                        out.println("<option value='11'>11</option>");
                                         out.println("<option value='12'>12</option>");
                                     out.println("</select>&nbsp;/");
                                     out.println("<select name='anio' size='1' >");
                                         out.println("<option value='1983'>1983</option>");
                                         out.println("<option value='1984'>1984</option>");
                                         out.println("<option value='1985'>1985</option>");
                                         out.println("<option value='1986'>1986</option>");
                                         out.println("<option value='1987'>1987</option>");
                                         out.println("<option value='1988' selected>1988</option>");
                                         out.println("<option value='1989'>1989</option>");
                                         out.println("<option value='1990'>1990</option>");
                                         out.println("<option value='1991'>1991</option>");
                                         out.println("<option value='1992'>1992</option>");
                                         out.println("<option value='1993'>1993</option>");
                                         out.println("<option value='1994'>1994</option>");
                                         out.println("<option value='1995'>1995</option>");
                                         out.println("<option value='1996'>1996</option>");
                                         out.println("<option value='1997'>1997</option>");
                                         out.println("<option value='1998'>1998</option>");
                                     out.println("</select>");
                                    if(error.equals("fecha")){
                                        out.println("<span class='marcado'>X</span>");
                                    } 
                                     
                                 out.println("</li>");
                            out.println("</ul>");
                            out.println("</fieldset>");

                            out.println("<fieldset>");
                                out.println("<legend>Datos de acceso</legend>");
                            out.println("<ul>");
                                out.println("<li><span class='obligatorio'>*&nbsp;</span><label>Usuario</label><input type='text' name='usuario' value='" +usuario +"'/>"); 
                                if(error.equals("usuario")){
                                    out.println("<span class='marcado'>X</span>");
                                }
                                
                                out.println("</li>");
                                 out.println("<li><span class='obligatorio'>*&nbsp;</span><label>Clave</label><input type='password' name='clave'/>"); 
                                if(error.equals("clave")){
                                    out.println("<span class='marcado'>X</span>");
                                }
                                 
                                 out.println("</li>");

                            out.println("</ul>");
                            out.println("</fieldset>");
                            out.println("<fieldset>");
                                out.println("<legend>Informaci&oacute;n general</legend>");
                                out.println("<ul>");
                                    
                                    out.println("<li><label>Preferencias</label>");              
                                    out.println("<input type='checkbox' name='aficiones' value='deporte' " +deporte +" /> Deporte");                                
                                    out.println("<input type='checkbox' name='aficiones' value='lectura' " +lectura +" /> Lectura");                               
                                    out.println("<input type='checkbox' name='aficiones' value='cine' " +cine + " /> Cine" );
                                    out.println("<input type='checkbox' name='aficiones' value='viaje' " +viaje +" /> Viaje</li>");
                                out.println("</ul>");
                            out.println("</fieldset>");

                            out.println("<p><input type='submit' name='enviar' value='Enviar'>");
                            out.println("<input type='submit' name='enviar' value='Limpiar'> </p>");
                        out.println("</form>");

                    out.println("<p><a class='enlace' href='index.html'> -> Volver al inicio <- </a></p>");

                    out.println("</div>");
                    
                    out.println(fin());
                
                }
                
            }    
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
//Función que saca el método de la cabecera de la página
    private StringBuilder inicio(){           
        StringBuilder cabecera = new StringBuilder();
        cabecera.append("<!DOCTYPE html>");
        cabecera.append("<html>");
        cabecera.append("<head>");
        cabecera.append("<title>Registro</title>");
        cabecera.append("<meta lang='es' charset='UTF-8'>");
        cabecera.append("<link rel='stylesheet' href='estilos/estilo.css' />");
        cabecera.append("</head>");
        cabecera.append("<body>");
        return cabecera;
    }
    
//Función que saca el final de la cabecera de la página    
    private StringBuilder fin(){             
        StringBuilder pie = new StringBuilder();
        pie.append("</body>");
        pie.append("</html>");
        return pie;
    }
 
//Método que devuelve si hay algún campo vacío o la fecha está mal
    private String errorCampos(String nombre, String usuario, String clave, String dia, String mes, String anio){
        String fallo = "";
        
        if(nombre != null && nombre.equals("")){
            fallo = "nombre";
        }else if(usuario != null && usuario.equals("")){
            fallo = "usuario";
        } else if(clave != null && clave.equals("")){
            fallo = "clave";
        }else if (fecha(Integer.parseInt(dia),Integer.parseInt(mes), Integer.parseInt(anio))){
            fallo = "fecha";
        }
    
        
        return fallo;
    }

// Método que devuelve si la fecha dada en día, mes y año es correcta o no mediante un booleano
    private boolean fecha(int dia, int mes, int anio){
        boolean error = false;
        int bisiesto = 0;

        if(dia < 1 || dia > 31 || mes < 1 || mes > 12){
                error = true;
        } else {
                if(mes == 2){
                        if (( anio%100!= 0  || anio%400==0 ) && anio%4==0)
                                bisiesto = 1;

                        if(dia > (28+bisiesto))
                                error = true;
                }
                else {
                        if((mes == 4 || mes == 6 || mes == 9 || mes == 11) && 
                                        dia > 30)
                                error = true;

                }
        }

        return error;
    }
}
