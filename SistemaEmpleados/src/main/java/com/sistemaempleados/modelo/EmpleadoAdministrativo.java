
package com.sistemaempleados.modelo;

public class EmpleadoAdministrativo extends Empleado {

    // Atributo propio del empleado administrativo
    private double salarioMensual;

    // Constructor
    public EmpleadoAdministrativo(String nombres, String apellidos,
            double salarioMensual) {

        super(nombres, apellidos, "Administrativo");
        this.salarioMensual = salarioMensual;
    }

    // Obtener salario mensual
    public double getSalarioMensual() {
        return salarioMensual;
    }

    // Modificar salario mensual
    public void setSalarioMensual(double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    // Calcular salario
    @Override
    public double calcularSalario() {
        return salarioMensual;
    }

    // Mostrar información
    @Override
    public void mostrarInformacion() {
        System.out.println("Tipo: Administrativo");
        System.out.println("Nombre: " + getNombreCompleto());
        System.out.println("Salario mensual: $" + calcularSalario());
    }
}
