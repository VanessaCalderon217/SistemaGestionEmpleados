
package com.sistemaempleados;

import com.sistemaempleados.modelo.Empleado;
import com.sistemaempleados.modelo.EmpleadoAdministrativo;
import com.sistemaempleados.modelo.EmpleadoPorHora;
import com.sistemaempleados.modelo.EmpleadoPorComision;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaEmpleados {

    private static Scanner entrada = new Scanner(System.in);
    private static List<Empleado> empleados = new ArrayList<>();

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n===== SISTEMA DE GESTION DE EMPLEADOS =====");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Consultar empleados");
            System.out.println("3. Calcular remuneracion");
            System.out.println("4. Generar reportes");
            System.out.println("5. Consultar por tipo");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarEmpleado();
                    break;

                case 2:
                    consultarEmpleados();
                    break;

                case 3:
                    calcularRemuneracion();
                    break;

                case 4:
                    generarReportes();
                    break;

                case 5:
                    consultarPorTipo();
                    break;

                case 6:
                    System.out.println("Gracias por utilizar el sistema.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);
    }

    // Registrar un nuevo empleado
    public static void registrarEmpleado() {

        System.out.println("\n===== REGISTRAR EMPLEADO =====");

        System.out.print("Ingrese los nombres: ");
        String nombres = entrada.nextLine();

        System.out.print("Ingrese los apellidos: ");
        String apellidos = entrada.nextLine();

        System.out.println("\nSeleccione el tipo de empleado:");
        System.out.println("1. Administrativo");
        System.out.println("2. Por hora");
        System.out.println("3. Por comision");
        System.out.print("Opcion: ");

        int tipo = leerEntero();

        switch (tipo) {
            case 1:
                System.out.print("Ingrese el salario mensual: $");
                double salarioMensual = leerDouble();

                empleados.add(new EmpleadoAdministrativo(
                        nombres, apellidos, salarioMensual));

                System.out.println("Empleado administrativo registrado.");
                break;

            case 2:
                System.out.print("Ingrese el precio por hora: $");
                double precioPorHora = leerDouble();

                System.out.print("Ingrese las horas trabajadas: ");
                int horasTrabajadas = leerEntero();

                empleados.add(new EmpleadoPorHora(
                        nombres, apellidos, precioPorHora, horasTrabajadas));

                System.out.println("Empleado por hora registrado.");
                break;

            case 3:
                System.out.print("Ingrese el porcentaje de comision: ");
                double porcentajeComision = leerDouble();

                System.out.print("Ingrese las ventas totales: $");
                double ventasTotales = leerDouble();

                empleados.add(new EmpleadoPorComision(
                        nombres, apellidos, porcentajeComision, ventasTotales));

                System.out.println("Empleado por comision registrado.");
                break;

            default:
                System.out.println("Tipo de empleado no valido.");
        }
    }

    // Consultar todos los empleados
    public static void consultarEmpleados() {

        System.out.println("\n===== LISTA DE EMPLEADOS =====");

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        for (Empleado empleado : empleados) {
            System.out.println("-----------------------------");
            empleado.mostrarInformacion();
        }
    }

    // Calcular la remuneracion de los empleados
    public static void calcularRemuneracion() {

        System.out.println("\n===== CALCULAR REMUNERACION =====");

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        for (Empleado empleado : empleados) {
            System.out.println("Empleado: " + empleado.getNombreCompleto());
            System.out.println("Tipo: " + empleado.getTipo());
            System.out.println("Remuneracion: $"
                    + empleado.calcularSalario());
            System.out.println("-----------------------------");
        }
    }

    // Generar reportes de empleados
    public static void generarReportes() {

        System.out.println("\n===== REPORTE DE EMPLEADOS =====");

        int administrativos = 0;
        int porHora = 0;
        int porComision = 0;

        double totalRemuneraciones = 0;

        for (Empleado empleado : empleados) {

            totalRemuneraciones += empleado.calcularSalario();

            if (empleado instanceof EmpleadoAdministrativo) {
                administrativos++;
            } else if (empleado instanceof EmpleadoPorHora) {
                porHora++;
            } else if (empleado instanceof EmpleadoPorComision) {
                porComision++;
            }
        }

        System.out.println("Total de empleados: " + empleados.size());
        System.out.println("Empleados administrativos: " + administrativos);
        System.out.println("Empleados por hora: " + porHora);
        System.out.println("Empleados por comision: " + porComision);
        System.out.println("Total de remuneraciones: $"
                + totalRemuneraciones);
    }

    // Consultar empleados segun su tipo
    public static void consultarPorTipo() {

        System.out.println("\n===== CONSULTAR POR TIPO =====");
        System.out.println("1. Administrativo");
        System.out.println("2. Por hora");
        System.out.println("3. Por comision");
        System.out.print("Seleccione el tipo: ");

        int tipo = leerEntero();

        boolean encontrado = false;

        for (Empleado empleado : empleados) {

            if ((tipo == 1 && empleado instanceof EmpleadoAdministrativo)
                    || (tipo == 2 && empleado instanceof EmpleadoPorHora)
                    || (tipo == 3 && empleado instanceof EmpleadoPorComision)) {

                empleado.mostrarInformacion();
                System.out.println("-----------------------------");
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron empleados de ese tipo.");
        }
    }

    // Leer un numero entero
    public static int leerEntero() {

        while (true) {
            try {
                int numero = Integer.parseInt(entrada.nextLine());
                return numero;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero entero valido: ");
            }
        }
    }

    // Leer un numero decimal
    public static double leerDouble() {

        while (true) {
            try {
                double numero = Double.parseDouble(entrada.nextLine());

                if (numero >= 0) {
                    return numero;
                }

                System.out.print("Ingrese un valor positivo: ");

            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }
}
