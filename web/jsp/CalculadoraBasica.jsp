<%-- 
    Document   : CalculadoraBasica
    Created on : 10-oct-2016, 18:14:02
    Author     : Ricardo
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.time.LocalTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../estilos/estilo.css" />        
        <title>Calculadora Basica</title>
    </head>
    <body>
        <div id="contenedor">
            <h1>Calculadora Básica</h1>
            <%
                String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Julio", "Agosto", "Septiembre",
                    "Octubre", "Noviembre", "Diciembre"};
                String[] dias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
                Calendar fecha = Calendar.getInstance();
                String operacion = "";
                String operando1 = "";
                String operando2 = "";
                String operador = "";
            %>

            <p>
                <%= dias[fecha.get(Calendar.DAY_OF_WEEK) - 1]%>, <%= fecha.get(Calendar.DAY_OF_MONTH)%> de 
                <%= meses[fecha.get(Calendar.MONTH) - 1]%> de <%= fecha.get(Calendar.YEAR)%> 
            </p>
            <p><%= request.getHeader("user-agent")%></p>
            <form id="formulario" method="post" action="CalculadoraBasica.jsp">
                <% if (request.getParameter("enviar") != null && request.getParameter("enviar").equals("Enviar")) {
                        operando1 = request.getParameter("operando1");
                        operando2 = request.getParameter("operando2");
                        operador = request.getParameter("operador");
                        if (calcular(operando1, operando2, operador).startsWith("Los oper")
                                || calcular(operando1, operando2, operador).startsWith("El oper")) {
                %>

                <h2><%= calcular(operando1, operando2, operador)%></h2>
                <% } else {%>
                <h2><%= operando1%> <%= operador%> <%= operando2%> = <%= calcular(operando1, operando2, operador)%></h2>
                <% }
                    }%>
                <ol>
                    <li><label>Primer operando</label><input type="text" name="operando1" size="5" />
                        <label>Segundo operando</label> <input type="text" name="operando2" size="5" />
                    </li>
                    <li>
                        <input type="radio" name="operador" value="+" checked="" /> Sumar
                        <input type="radio" name="operador" value="-" /> Restar
                        <input type="radio" name="operador" value="*" /> Multiplicar
                        <input type="radio" name="operador" value="/" /> Dividir
                    </li>
                </ol>
                <p>
                    <input type="submit" name="enviar" value="Enviar" />
                    <input type="submit" name="enviar" value="Limpiar" />
                </p>
            </form>
            <p><a class="enlace" href="../index.html">-> Volver a inicio <-</a></p>
        </div>
    </body>
</html>


<%! public String calcular(String operando1, String operando2, String operador) {
        String resultado = "";
        float operacion = 0;
        if (operando2.trim().equals("0") && operador.equals("/")) {
            resultado = "No se puede dividir un número entre 0";
            //Float.isNaN comprueba si los números pasados son numeros realmente o no son números...
        } else {
            try {
                switch (operador) {
                    case "+":
                        operacion = (Float.parseFloat(operando1) + Float.parseFloat(operando2));
                        break;
                    case "-":
                        operacion = (Float.parseFloat(operando1) - Float.parseFloat(operando2));
                        break;
                    case "*":
                        operacion = (Float.parseFloat(operando1) * Float.parseFloat(operando2));
                        break;
                    case "/":
                        operacion = (Float.parseFloat(operando1) / Float.parseFloat(operando2));
                        break;
                    default:
                        resultado = "El operador pasado es incorrecto";
                }
                resultado = (resultado == "") ? String.valueOf(operacion) : resultado;
            } catch (NumberFormatException ex) {
                resultado = "Los operandos deben ser números";
            }

        }
        return resultado;
    }
%>

