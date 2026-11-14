
package com.sistemaempleados.modelo;

import java.time.LocalDate;

public class RegistroCalculo {

    // Datos del empleado
    private String codigoEmpleado;
    private String nombreEmpleado;
    private String tipoEmpleado;

    // Periodo del calculo
    private String periodo;
    private LocalDate fechaCalculo;

    // Valores utilizados en los calculos
    private double salarioMensual;
    private double precioPorHora;
    private int horasTrabajadas;
    private double porcentajeComision;
    private double ventasTotales;

    // Resultado del calculo
    private double remuneracion;

    // Constructor
    public RegistroCalculo(String codigoEmpleado,
            String nombreEmpleado,
            String tipoEmpleado,
            String periodo,
            double salarioMensual,
            double precioPorHora,
            int horasTrabajadas,
            double porcentajeComision,
            double ventasTotales,
            double remuneracion) {

        this.codigoEmpleado = codigoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.tipoEmpleado = tipoEmpleado;
        this.periodo = periodo;
        this.fechaCalculo = LocalDate.now();

        this.salarioMensual = salarioMensual;
        this.precioPorHora = precioPorHora;
        this.horasTrabajadas = horasTrabajadas;
        this.porcentajeComision = porcentajeComision;
        this.ventasTotales = ventasTotales;

        this.remuneracion = remuneracion;
    }

    // Consultar datos del empleado
    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    // Consultar periodo y fecha
    public String getPeriodo() {
        return periodo;
    }

    public LocalDate getFechaCalculo() {
        return fechaCalculo;
    }
    
    public void setFechaCalculo(LocalDate fechaCalculo) {
        this.fechaCalculo = fechaCalculo;
    }

    // Consultar valores utilizados
    public double getSalarioMensual() {
        return salarioMensual;
    }

    public double getPrecioPorHora() {
        return precioPorHora;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    public double getVentasTotales() {
        return ventasTotales;
    }

    // Consultar remuneracion calculada
    public double getRemuneracion() {
        return remuneracion;
    }

    // Mostrar informacion del calculo
    public void mostrarRegistro() {

        System.out.println("Codigo: " + codigoEmpleado);
        System.out.println("Empleado: " + nombreEmpleado);
        System.out.println("Tipo: " + tipoEmpleado);
        System.out.println("Periodo: " + periodo);
        System.out.println("Fecha del calculo: " + fechaCalculo);

        if (tipoEmpleado.equalsIgnoreCase("Administrativo")) {
            System.out.println("Salario mensual: $" + salarioMensual);
        } else if (tipoEmpleado.equalsIgnoreCase("Por Hora")) {
            System.out.println("Precio por hora: $" + precioPorHora);
            System.out.println("Horas trabajadas: " + horasTrabajadas);
        } else if (tipoEmpleado.equalsIgnoreCase("Por Comision")) {
            System.out.println("Porcentaje: " + porcentajeComision + "%");
            System.out.println("Ventas totales: $" + ventasTotales);
        }

        System.out.println("Remuneracion: $" + remuneracion);
    }
}
