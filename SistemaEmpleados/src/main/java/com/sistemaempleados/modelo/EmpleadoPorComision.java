package com.sistemaempleados.modelo;

public class EmpleadoPorComision extends Empleado {

    // Atributos propios del empleado por comision
    private double porcentajeComision;
    private double ventasTotales;

    // Constructor
    public EmpleadoPorComision(String nombres, String apellidos,
            double porcentajeComision, double ventasTotales) {

        super(nombres, apellidos, "Por Comision");
        this.porcentajeComision = porcentajeComision;
        this.ventasTotales = ventasTotales;
    }

    // Obtener porcentaje de comision
    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    // Modificar porcentaje de comision
    public void setPorcentajeComision(double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    // Obtener ventas totales
    public double getVentasTotales() {
        return ventasTotales;
    }

    // Modificar ventas totales
    public void setVentasTotales(double ventasTotales) {
        this.ventasTotales = ventasTotales;
    }

    // Calcular salario por comision
    @Override
    public double calcularSalario() {
        return ventasTotales * porcentajeComision / 100;
    }

    // Mostrar informacion del empleado
    @Override
    public void mostrarInformacion() {
        System.out.println("Tipo: Por Comision");
        System.out.println("Nombre: " + getNombreCompleto());
        System.out.println("Porcentaje de comision: " + porcentajeComision + "%");
        System.out.println("Ventas totales: $" + ventasTotales);
        System.out.println("Remuneracion: $" + calcularSalario());
    }
}