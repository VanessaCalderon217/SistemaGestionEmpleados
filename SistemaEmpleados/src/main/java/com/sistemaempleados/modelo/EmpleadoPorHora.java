
package com.sistemaempleados.modelo;

public class EmpleadoPorHora extends Empleado {

    // Atributos propios del empleado por hora
    private double precioPorHora;
    private int horasTrabajadas;

    // Constructor
    public EmpleadoPorHora(String nombres, String apellidos,
            double precioPorHora, int horasTrabajadas) {

        super(nombres, apellidos, "Por Hora");
        this.precioPorHora = precioPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    // Obtener precio por hora
    public double getPrecioPorHora() {
        return precioPorHora;
    }

    // Modificar precio por hora
    public void setPrecioPorHora(double precioPorHora) {
        this.precioPorHora = precioPorHora;
    }

    // Obtener horas trabajadas
    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    // Modificar horas trabajadas
    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    // Calcular salario por hora
    @Override
    public double calcularSalario() {
        return precioPorHora * horasTrabajadas;
    }

    // Mostrar informacion del empleado
    @Override
    public void mostrarInformacion() {
        System.out.println("Tipo: Por Hora");
        System.out.println("Nombre: " + getNombreCompleto());
        System.out.println("Precio por hora: $" + precioPorHora);
        System.out.println("Horas trabajadas: " + horasTrabajadas);
        System.out.println("Remuneracion: $" + calcularSalario());
    }
}
