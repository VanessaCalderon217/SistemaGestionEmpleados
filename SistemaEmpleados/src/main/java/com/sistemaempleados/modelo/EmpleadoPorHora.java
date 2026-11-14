
package com.sistemaempleados.modelo;

import java.time.LocalDate;

public class EmpleadoPorHora extends Empleado {

    // Atributos propios del empleado por hora
    private double precioPorHora;
    private int horasTrabajadas;

    // Constructor
    public EmpleadoPorHora(String codigo,
            String nombres,
            String apellidos,
            String documento,
            String cargo,
            LocalDate fechaIngreso,
            double precioPorHora,
            int horasTrabajadas) {

        super(codigo, nombres, apellidos, documento,
                "Por Hora", cargo, fechaIngreso);

        this.precioPorHora = precioPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    // Consultar precio por hora
    public double getPrecioPorHora() {
        return precioPorHora;
    }

    // Modificar precio por hora
    public void setPrecioPorHora(double precioPorHora) {
        this.precioPorHora = precioPorHora;
    }

    // Consultar horas trabajadas
    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    // Modificar horas trabajadas
    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    // Calcular remuneracion
    @Override
    public double calcularSalario() {
        return precioPorHora * horasTrabajadas;
    }

    // Mostrar informacion del empleado
    @Override
    public void mostrarInformacion() {

        System.out.println("Codigo: " + getCodigo());
        System.out.println("Nombre: " + getNombreCompleto());
        System.out.println("Documento: " + getDocumento());
        System.out.println("Cargo: " + getCargo());
        System.out.println("Tipo: " + getTipo());
        System.out.println("Fecha de ingreso: " + getFechaIngreso());
        System.out.println("Precio por hora: $" + precioPorHora);
        System.out.println("Horas trabajadas: " + horasTrabajadas);
        System.out.println("Remuneracion: $" + calcularSalario());
        System.out.println("Estado: " +
                (isActivo() ? "Activo" : "Inactivo"));
    }
}
