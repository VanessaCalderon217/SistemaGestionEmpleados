
package com.sistemaempleados;

import com.sistemaempleados.modelo.Empleado;
import com.sistemaempleados.modelo.EmpleadoAdministrativo;
import com.sistemaempleados.modelo.EmpleadoPorHora;
import com.sistemaempleados.modelo.EmpleadoPorComision;
import com.sistemaempleados.modelo.RegistroCalculo;

import java.util.ArrayList;
import java.util.List;

public class GestorCalculos {

    // Lista para guardar el historial de calculos
    private List<RegistroCalculo> historialCalculos;

    // Constructor
    public GestorCalculos() {
        historialCalculos = new ArrayList<>();
    }

    // Calcular remuneracion de un empleado
    public double calcularRemuneracion(Empleado empleado,
            String periodo) {

        if (empleado == null || !empleado.estaActivo()) {
            return -1;
        }

        if (periodo == null || periodo.trim().isEmpty()) {
            return -1;
        }

        double salarioMensual = 0;
        double precioPorHora = 0;
        int horasTrabajadas = 0;
        double porcentajeComision = 0;
        double ventasTotales = 0;

        // Obtener los valores segun el tipo de empleado
        if (empleado instanceof EmpleadoAdministrativo) {

            EmpleadoAdministrativo administrativo =
                    (EmpleadoAdministrativo) empleado;

            salarioMensual = administrativo.getSalarioMensual();

        } else if (empleado instanceof EmpleadoPorHora) {

            EmpleadoPorHora empleadoHora =
                    (EmpleadoPorHora) empleado;

            precioPorHora = empleadoHora.getPrecioPorHora();
            horasTrabajadas = empleadoHora.getHorasTrabajadas();

        } else if (empleado instanceof EmpleadoPorComision) {

            EmpleadoPorComision empleadoComision =
                    (EmpleadoPorComision) empleado;

            porcentajeComision =
                    empleadoComision.getPorcentajeComision();

            ventasTotales = empleadoComision.getVentasTotales();
        }

        // Realizar el calculo
        double remuneracion = empleado.calcularSalario();

        // Crear el registro del calculo
        RegistroCalculo registro = new RegistroCalculo(
                empleado.getCodigo(),
                empleado.getNombreCompleto(),
                empleado.getTipo(),
                periodo,
                salarioMensual,
                precioPorHora,
                horasTrabajadas,
                porcentajeComision,
                ventasTotales,
                remuneracion
        );

        // Guardar el registro en el historial
        historialCalculos.add(registro);

        return remuneracion;
    }

    // Consultar todo el historial
    public List<RegistroCalculo> consultarHistorial() {
        return new ArrayList<>(historialCalculos);
    }

    // Consultar historial por codigo de empleado
    public List<RegistroCalculo> consultarHistorialPorEmpleado(
            String codigo) {

        List<RegistroCalculo> resultados = new ArrayList<>();

        for (RegistroCalculo registro : historialCalculos) {

            if (registro.getCodigoEmpleado()
                    .equalsIgnoreCase(codigo)) {

                resultados.add(registro);
            }
        }

        return resultados;
    }

    // Consultar historial por periodo
    public List<RegistroCalculo> consultarHistorialPorPeriodo(
            String periodo) {

        List<RegistroCalculo> resultados = new ArrayList<>();

        for (RegistroCalculo registro : historialCalculos) {

            if (registro.getPeriodo().equalsIgnoreCase(periodo)) {
                resultados.add(registro);
            }
        }

        return resultados;
    }

    // Mostrar historial de calculos
    public void mostrarHistorial() {

        for (RegistroCalculo registro : historialCalculos) {
            registro.mostrarRegistro();
            System.out.println("-------------------------");
        }
    }

    // Obtener cantidad de calculos realizados
    public int obtenerCantidadCalculos() {
        return historialCalculos.size();
    }
}
