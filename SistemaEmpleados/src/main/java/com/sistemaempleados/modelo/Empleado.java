
package com.sistemaempleados.modelo;

import java.time.LocalDate;

public abstract class Empleado {

    // Atributos del empleado
    private String codigo;
    private String nombres;
    private String apellidos;
    private String documento;
    private String tipo;
    private String cargo;
    private LocalDate fechaIngreso;
    private boolean activo;

    // Constructor original
    public Empleado(String nombres, String apellidos, String tipo) {
        this.codigo = "";
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = "";
        this.tipo = tipo;
        this.cargo = "";
        this.fechaIngreso = LocalDate.now();
        this.activo = true;
    }

    // Constructor completo
    public Empleado(String codigo, String nombres,
            String apellidos, String documento,
            String tipo, String cargo,
            LocalDate fechaIngreso) {

        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.tipo = tipo;
        this.cargo = cargo;
        this.fechaIngreso = fechaIngreso;
        this.activo = true;
    }

    // Metodos para consultar datos

    public String getCodigo() {
        return codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCargo() {
        return cargo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    // Metodos para modificar datos

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    // Modificar informacion del empleado
    public void modificarDatos(String nombres,
            String apellidos, String documento,
            String cargo, LocalDate fechaIngreso) {

        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.cargo = cargo;
        this.fechaIngreso = fechaIngreso;
    }

    // Inactivar empleado
    public void inactivar() {
        this.activo = false;
    }

    // Activar empleado
    public void activar() {
        this.activo = true;
    }

    // Consultar si esta activo
    public boolean estaActivo() {
        return activo;
    }

    // Metodos abstractos
    public abstract double calcularSalario();

    public abstract void mostrarInformacion();
}
