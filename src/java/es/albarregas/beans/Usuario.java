
package es.albarregas.beans;

public class Usuario {
    
    private int id;
    private String nombre;
    private int edad;
    private double sueldo;
    private String[] aficiones;

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }   
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String[] getAficiones() {
        return aficiones;
    }

    public void setAficiones(String[] aficiones) {
        this.aficiones = aficiones;
    }
    
    
    
}
