
package com.sistemaempleados.modelo;

public abstract class Empleado {

    // Atributos comunes
    private String nombres;
    private String apellidos;
    private String tipo;

    // Constructor
    public Empleado(String nombres, String apellidos, String tipo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipo = tipo;
    }

    // Métodos para obtener información
    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public String getTipo() {
        return tipo;
    }

    // Métodos para modificar información
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // Métodos abstractos
    public abstract double calcularSalario();

    public abstract void mostrarInformacion();
}
