
package com.sistemaempleados.modelo;

import java.time.LocalDate;

public class EmpleadoAdministrativo extends Empleado {

    // Atributo propio del empleado administrativo
    private double salarioMensual;

    // Constructor
    public EmpleadoAdministrativo(String codigo,
            String nombres,
            String apellidos,
            String documento,
            String cargo,
            LocalDate fechaIngreso,
            double salarioMensual) {

        super(codigo, nombres, apellidos, documento,
                "Administrativo", cargo, fechaIngreso);

        this.salarioMensual = salarioMensual;
    }

    // Consultar salario mensual
    public double getSalarioMensual() {
        return salarioMensual;
    }

    // Modificar salario mensual
    public void setSalarioMensual(double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    // Calcular remuneracion
    @Override
    public double calcularSalario() {
        return salarioMensual;
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
        System.out.println("Salario mensual: $" + salarioMensual);
        System.out.println("Estado: " +
                (isActivo() ? "Activo" : "Inactivo"));
    }
}
