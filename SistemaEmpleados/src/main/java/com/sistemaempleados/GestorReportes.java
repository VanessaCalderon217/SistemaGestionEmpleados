
package com.sistemaempleados;

import com.sistemaempleados.modelo.Empleado;
import com.sistemaempleados.modelo.RegistroCalculo;
import java.util.List;

public class GestorReportes {

    private GestorEmpleados gestorEmpleados;
    private GestorCalculos gestorCalculos;

    // Constructor
    public GestorReportes(GestorEmpleados gestorEmpleados,
            GestorCalculos gestorCalculos) {

        this.gestorEmpleados = gestorEmpleados;
        this.gestorCalculos = gestorCalculos;
    }

    // Reporte general de empleados
    public void generarReporteGeneral() {

        List<Empleado> empleados =
                gestorEmpleados.consultarEmpleados();

        System.out.println("===== REPORTE GENERAL =====");

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        for (Empleado empleado : empleados) {
            empleado.mostrarInformacion();
            System.out.println("-------------------------");
        }
    }

    // Reporte por tipo de empleado
    public void generarReportePorTipo(String tipo) {

        List<Empleado> empleados =
                gestorEmpleados.consultarPorTipo(tipo);

        System.out.println("===== REPORTE POR TIPO =====");

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados de este tipo.");
            return;
        }

        for (Empleado empleado : empleados) {
            empleado.mostrarInformacion();
            System.out.println("-------------------------");
        }
    }

    // Reporte de remuneraciones individuales
    public void generarReporteRemuneraciones() {

        List<Empleado> empleados =
                gestorEmpleados.consultarActivos();

        System.out.println("===== REMUNERACIONES =====");

        for (Empleado empleado : empleados) {

            System.out.println("Empleado: "
                    + empleado.getNombreCompleto());

            System.out.println("Codigo: "
                    + empleado.getCodigo());

            System.out.println("Remuneracion: $"
                    + empleado.calcularSalario());

            System.out.println("-------------------------");
        }
    }

    // Resumen general
    public void generarResumen() {

        List<Empleado> empleados =
                gestorEmpleados.consultarEmpleados();

        int administrativos = 0;
        int porHora = 0;
        int porComision = 0;
        int activos = 0;
        int inactivos = 0;

        for (Empleado empleado : empleados) {

            if (empleado.getTipo().equalsIgnoreCase("Administrativo")) {
                administrativos++;
            } else if (empleado.getTipo().equalsIgnoreCase("Por Hora")) {
                porHora++;
            } else if (empleado.getTipo().equalsIgnoreCase("Por Comision")) {
                porComision++;
            }

            if (empleado.estaActivo()) {
                activos++;
            } else {
                inactivos++;
            }
        }

        System.out.println("===== RESUMEN GENERAL =====");
        System.out.println("Total de empleados: " + empleados.size());
        System.out.println("Administrativos: " + administrativos);
        System.out.println("Por hora: " + porHora);
        System.out.println("Por comision: " + porComision);
        System.out.println("Activos: " + activos);
        System.out.println("Inactivos: " + inactivos);

        System.out.println("Total de remuneraciones: $"
                + gestorEmpleados.calcularTotalRemuneraciones());
    }

    // Reporte del historial completo
    public void generarReporteHistorial() {

        List<RegistroCalculo> historial =
                gestorCalculos.consultarHistorial();

        System.out.println("===== HISTORIAL DE CALCULOS =====");

        if (historial.isEmpty()) {
            System.out.println("No hay calculos registrados.");
            return;
        }

        for (RegistroCalculo registro : historial) {
            registro.mostrarRegistro();
            System.out.println("-------------------------");
        }
    }

    // Reporte del historial por empleado
    public void generarHistorialPorEmpleado(String codigo) {

        List<RegistroCalculo> historial =
                gestorCalculos.consultarHistorialPorEmpleado(codigo);

        System.out.println("===== HISTORIAL POR EMPLEADO =====");

        for (RegistroCalculo registro : historial) {
            registro.mostrarRegistro();
            System.out.println("-------------------------");
        }

        if (historial.isEmpty()) {
            System.out.println("No hay calculos para este empleado.");
        }
    }

    // Reporte del historial por periodo
    public void generarHistorialPorPeriodo(String periodo) {

        List<RegistroCalculo> historial =
                gestorCalculos.consultarHistorialPorPeriodo(periodo);

        System.out.println("===== HISTORIAL POR PERIODO =====");

        for (RegistroCalculo registro : historial) {
            registro.mostrarRegistro();
            System.out.println("-------------------------");
        }

        if (historial.isEmpty()) {
            System.out.println("No hay calculos para este periodo.");
        }
    }
}
