
package com.sistemaempleados;

import java.time.LocalDate;

import com.sistemaempleados.modelo.Empleado;

public class ValidadorEmpleados {

    // Validar que un campo de texto no este vacio
    public static boolean validarTexto(String texto) {

        return texto != null && !texto.trim().isEmpty();
    }

    // Validar codigo y comprobar que no este repetido
    public static boolean validarCodigo(String codigo,
            GestorEmpleados gestor) {

        if (!validarTexto(codigo) || gestor == null) {
            return false;
        }

        return gestor.buscarPorCodigo(codigo) == null;
    }

    // Validar documento y comprobar que no este repetido
    public static boolean validarDocumento(String documento,
            GestorEmpleados gestor) {

        if (!validarTexto(documento) || gestor == null) {
            return false;
        }

        return gestor.buscarPorDocumento(documento) == null;
    }

    // Validar documento al modificar un empleado
    public static boolean validarDocumentoModificado(
            String documento, String codigoEmpleado,
            GestorEmpleados gestor) {

        if (!validarTexto(documento) || gestor == null) {
            return false;
        }

        Empleado encontrado =
                gestor.buscarPorDocumento(documento);

        // Permitir que conserve su propio documento
        if (encontrado == null) {
            return true;
        }

        return encontrado.getCodigo().equalsIgnoreCase(
                codigoEmpleado);
    }

    // Validar fecha de ingreso
    public static boolean validarFecha(LocalDate fecha) {

        return fecha != null && !fecha.isAfter(LocalDate.now());
    }

    // Validar salario mensual
    public static boolean validarSalario(double salario) {

        return salario >= 0;
    }

    // Validar precio por hora
    public static boolean validarPrecioPorHora(double precio) {

        return precio >= 0;
    }

    // Validar horas trabajadas
    public static boolean validarHoras(int horas) {

        return horas >= 0;
    }

    // Validar porcentaje de comision
    public static boolean validarPorcentajeComision(
            double porcentaje) {

        return porcentaje >= 0 && porcentaje <= 100;
    }

    // Validar ventas totales
    public static boolean validarVentas(double ventas) {

        return ventas >= 0;
    }
}
