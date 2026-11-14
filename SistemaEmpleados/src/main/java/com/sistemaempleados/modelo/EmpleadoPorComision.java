
package com.sistemaempleados.modelo;

import java.time.LocalDate;

public class EmpleadoPorComision extends Empleado {

    // Atributos propios del empleado por comision
    private double porcentajeComision;
    private double ventasTotales;

    // Constructor
    public EmpleadoPorComision(String codigo,
            String nombres,
            String apellidos,
            String documento,
            String cargo,
            LocalDate fechaIngreso,
            double porcentajeComision,
            double ventasTotales) {

        super(codigo, nombres, apellidos, documento,
                "Por Comision", cargo, fechaIngreso);

        this.porcentajeComision = porcentajeComision;
        this.ventasTotales = ventasTotales;
    }

    // Consultar porcentaje de comision
    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    // Modificar porcentaje de comision
    public void setPorcentajeComision(double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    // Consultar ventas totales
    public double getVentasTotales() {
        return ventasTotales;
    }

    // Modificar ventas totales
    public void setVentasTotales(double ventasTotales) {
        this.ventasTotales = ventasTotales;
    }

    // Calcular remuneracion
    @Override
    public double calcularSalario() {
        return ventasTotales * porcentajeComision / 100;
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
        System.out.println("Porcentaje de comision: "
                + porcentajeComision + "%");
        System.out.println("Ventas totales: $" + ventasTotales);
        System.out.println("Remuneracion: $" + calcularSalario());
        System.out.println("Estado: "
                + (isActivo() ? "Activo" : "Inactivo"));
    }
}
