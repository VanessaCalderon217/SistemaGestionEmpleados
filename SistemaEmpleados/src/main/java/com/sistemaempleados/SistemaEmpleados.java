
package com.sistemaempleados;

import com.sistemaempleados.modelo.Empleado;
import com.sistemaempleados.modelo.EmpleadoAdministrativo;
import com.sistemaempleados.modelo.EmpleadoPorHora;
import com.sistemaempleados.modelo.EmpleadoPorComision;
import com.sistemaempleados.modelo.RegistroCalculo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class SistemaEmpleados {

    private Scanner teclado;
    private GestorEmpleados gestorEmpleados;
    private GestorCalculos gestorCalculos;
    private GestorReportes gestorReportes;
    private OrdenadorEmpleados ordenador;

    // Constructor
    public SistemaEmpleados() {
        teclado = new Scanner(System.in);

        gestorEmpleados = new GestorEmpleados();
        gestorCalculos = new GestorCalculos();

        gestorReportes = new GestorReportes(
                gestorEmpleados, gestorCalculos);

        ordenador = new OrdenadorEmpleados();
    }

    // Metodo principal
    public static void main(String[] args) {
        SistemaEmpleados sistema = new SistemaEmpleados();
        sistema.iniciar();
    }

    // Iniciar el sistema
    public void iniciar() {

        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion("Seleccione una opcion: ", 1, 11);

            switch (opcion) {

                case 1:
                    registrarEmpleado();
                    break;

                case 2:
                    consultarEmpleados();
                    break;

                case 3:
                    buscarEmpleado();
                    break;

                case 4:
                    modificarEmpleado();
                    break;

                case 5:
                    inactivarEmpleado();
                    break;

                case 6:
                    calcularRemuneracion();
                    break;

                case 7:
                    mostrarMenuReportes();
                    break;

                case 8:
                    consultarHistorial();
                    break;

                case 9:
                    consultarPorTipo();
                    break;

                case 10:
                    ordenarEmpleados();
                    break;

                case 11:
                    System.out.println("Gracias por utilizar el sistema.");
                    break;
            }

        } while (opcion != 11);
    }

    // Mostrar menu principal
    private void mostrarMenu() {

        System.out.println("\n==================================");
        System.out.println(" SISTEMA DE ADMINISTRACION DE EMPLEADOS");
        System.out.println("==================================");
        System.out.println("1. Registrar empleado");
        System.out.println("2. Consultar empleados");
        System.out.println("3. Buscar empleado");
        System.out.println("4. Modificar empleado");
        System.out.println("5. Inactivar empleado");
        System.out.println("6. Calcular remuneracion");
        System.out.println("7. Generar reportes");
        System.out.println("8. Consultar historial de calculos");
        System.out.println("9. Consultar empleados por tipo");
        System.out.println("10. Ordenar empleados");
        System.out.println("11. Salir");
        System.out.println("==================================");
    }

    // Registrar empleado
    private void registrarEmpleado() {

        System.out.println("\n===== REGISTRAR EMPLEADO =====");

        String codigo = leerTexto("Codigo: ");
        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");
        String documento = leerTexto("Documento: ");
        String cargo = leerTexto("Cargo: ");
        LocalDate fechaIngreso = leerFecha("Fecha de ingreso (AAAA-MM-DD): ");

        System.out.println("\nTipo de empleado:");
        System.out.println("1. Administrativo");
        System.out.println("2. Por hora");
        System.out.println("3. Por comision");

        int tipo = leerOpcion("Seleccione el tipo: ", 1, 3);

        Empleado nuevoEmpleado = null;

        switch (tipo) {

            case 1:

                double salario = leerDoubleNoNegativo(
                        "Salario mensual: $");

                nuevoEmpleado = new EmpleadoAdministrativo(
                        codigo, nombres, apellidos,
                        documento, cargo, fechaIngreso, salario);
                break;

            case 2:

                double precio = leerDoubleNoNegativo(
                        "Precio por hora: $");

                int horas = leerEnteroNoNegativo(
                        "Horas trabajadas: ");

                nuevoEmpleado = new EmpleadoPorHora(
                        codigo, nombres, apellidos,
                        documento, cargo, fechaIngreso,
                        precio, horas);
                break;

            case 3:

                double porcentaje = leerPorcentaje(
                        "Porcentaje de comision: ");

                double ventas = leerDoubleNoNegativo(
                        "Ventas totales: $");

                nuevoEmpleado = new EmpleadoPorComision(
                        codigo, nombres, apellidos,
                        documento, cargo, fechaIngreso,
                        porcentaje, ventas);
                break;
        }

        if (gestorEmpleados.registrarEmpleado(nuevoEmpleado)) {
            System.out.println("Empleado registrado correctamente.");
        } else {
            System.out.println(
                    "No se pudo registrar. Verifique los datos o los duplicados.");
        }
    }

    // Consultar todos los empleados
    private void consultarEmpleados() {

        System.out.println("\n===== LISTADO DE EMPLEADOS =====");

        List<Empleado> empleados =
                gestorEmpleados.consultarEmpleados();

        mostrarListaEmpleados(empleados);
    }

    // Buscar empleado
    private void buscarEmpleado() {

        System.out.println("\n===== BUSCAR EMPLEADO =====");
        System.out.println("1. Buscar por codigo");
        System.out.println("2. Buscar por documento");
        System.out.println("3. Buscar por nombre");
        System.out.println("4. Regresar");

        int opcion = leerOpcion("Seleccione una opcion: ", 1, 4);

        switch (opcion) {

            case 1:

                String codigo = leerTexto("Ingrese el codigo: ");

                Empleado porCodigo =
                        gestorEmpleados.buscarPorCodigo(codigo);

                if (porCodigo != null) {
                    porCodigo.mostrarInformacion();
                } else {
                    System.out.println("Empleado no encontrado.");
                }
                break;

            case 2:

                String documento = leerTexto("Ingrese el documento: ");

                Empleado porDocumento =
                        gestorEmpleados.buscarPorDocumento(documento);

                if (porDocumento != null) {
                    porDocumento.mostrarInformacion();
                } else {
                    System.out.println("Empleado no encontrado.");
                }
                break;

            case 3:

                String nombre = leerTexto("Ingrese el nombre: ");

                List<Empleado> encontrados =
                        gestorEmpleados.buscarPorNombre(nombre);

                mostrarListaEmpleados(encontrados);
                break;

            case 4:
                break;
        }
    }

    // Modificar empleado
    private void modificarEmpleado() {

        System.out.println("\n===== MODIFICAR EMPLEADO =====");

        String codigo = leerTexto("Codigo del empleado: ");

        Empleado empleado =
                gestorEmpleados.buscarPorCodigo(codigo);

        if (empleado == null || !empleado.estaActivo()) {
            System.out.println("Empleado inexistente o inactivo.");
            return;
        }

        System.out.println("Datos actuales:");
        empleado.mostrarInformacion();

        System.out.println("\nIngrese los nuevos datos:");

        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");
        String documento = leerTexto("Documento: ");
        String cargo = leerTexto("Cargo: ");

        LocalDate fecha = leerFecha(
                "Fecha de ingreso (AAAA-MM-DD): ");

        double salario = 0;
        double precio = 0;
        int horas = 0;
        double porcentaje = 0;
        double ventas = 0;

        // Solicitar los nuevos datos segun el tipo
        if (empleado instanceof EmpleadoAdministrativo) {

            salario = leerDoubleNoNegativo(
                    "Nuevo salario mensual: $");

        } else if (empleado instanceof EmpleadoPorHora) {

            precio = leerDoubleNoNegativo(
                    "Nuevo precio por hora: $");

            horas = leerEnteroNoNegativo(
                    "Nuevas horas trabajadas: ");

        } else if (empleado instanceof EmpleadoPorComision) {

            porcentaje = leerPorcentaje(
                    "Nuevo porcentaje de comision: ");

            ventas = leerDoubleNoNegativo(
                    "Nuevas ventas totales: $");
        }

        // Modificar datos generales
        boolean modificado = gestorEmpleados.modificarEmpleado(
                codigo, nombres, apellidos,
                documento, cargo, fecha);

        if (!modificado) {
            System.out.println(
                    "No se pudo modificar. Revise los datos ingresados.");
            return;
        }

        // Modificar datos propios de cada tipo
        if (empleado instanceof EmpleadoAdministrativo) {

            gestorEmpleados.modificarSalarioAdministrativo(
                    codigo, salario);

        } else if (empleado instanceof EmpleadoPorHora) {

            gestorEmpleados.modificarEmpleadoPorHora(
                    codigo, precio, horas);

        } else if (empleado instanceof EmpleadoPorComision) {

            gestorEmpleados.modificarEmpleadoPorComision(
                    codigo, porcentaje, ventas);
        }

        System.out.println("Empleado modificado correctamente.");
    }

    // Inactivar empleado
    private void inactivarEmpleado() {

        System.out.println("\n===== INACTIVAR EMPLEADO =====");

        String codigo = leerTexto("Codigo del empleado: ");

        Empleado empleado =
                gestorEmpleados.buscarPorCodigo(codigo);

        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        if (!empleado.estaActivo()) {
            System.out.println("El empleado ya esta inactivo.");
            return;
        }

        System.out.println("Empleado: " + empleado.getNombreCompleto());
        System.out.println("1. Confirmar inactivacion");
        System.out.println("2. Cancelar");

        int opcion = leerOpcion("Seleccione una opcion: ", 1, 2);

        if (opcion == 1) {

            if (gestorEmpleados.inactivarEmpleado(codigo)) {
                System.out.println(
                        "Empleado inactivado. Sus datos se conservaron.");
            } else {
                System.out.println("No se pudo inactivar el empleado.");
            }
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    // Calcular remuneracion
    private void calcularRemuneracion() {

        System.out.println("\n===== CALCULAR REMUNERACION =====");

        String codigo = leerTexto("Codigo del empleado: ");

        Empleado empleado =
                gestorEmpleados.buscarPorCodigo(codigo);

        if (empleado == null || !empleado.estaActivo()) {
            System.out.println("Empleado inexistente o inactivo.");
            return;
        }

        String periodo = leerTexto(
                "Periodo del calculo (por ejemplo, Noviembre 2026): ");

        double remuneracion =
                gestorCalculos.calcularRemuneracion(empleado, periodo);

        if (remuneracion < 0) {
            System.out.println("No fue posible realizar el calculo.");
        } else {
            System.out.println("Empleado: " + empleado.getNombreCompleto());
            System.out.println("Remuneracion: $" + remuneracion);
            System.out.println("Calculo guardado en el historial.");
        }
    }

    // Mostrar menu de reportes
    private void mostrarMenuReportes() {

        int opcion;

        do {
            System.out.println("\n===== MENU DE REPORTES =====");
            System.out.println("1. Reporte general");
            System.out.println("2. Reporte por tipo");
            System.out.println("3. Reporte de remuneraciones");
            System.out.println("4. Resumen general");
            System.out.println("5. Historial completo");
            System.out.println("6. Historial por empleado");
            System.out.println("7. Historial por periodo");
            System.out.println("8. Regresar");

            opcion = leerOpcion("Seleccione una opcion: ", 1, 8);

            switch (opcion) {

                case 1:
                    gestorReportes.generarReporteGeneral();
                    break;

                case 2:
                    consultarReportePorTipo();
                    break;

                case 3:
                    gestorReportes.generarReporteRemuneraciones();
                    break;

                case 4:
                    gestorReportes.generarResumen();
                    break;

                case 5:
                    gestorReportes.generarReporteHistorial();
                    break;

                case 6:
                    String codigo = leerTexto("Codigo del empleado: ");
                    gestorReportes.generarHistorialPorEmpleado(codigo);
                    break;

                case 7:
                    String periodo = leerTexto("Periodo: ");
                    gestorReportes.generarHistorialPorPeriodo(periodo);
                    break;
            }

        } while (opcion != 8);
    }

    // Reporte de empleados por tipo
    private void consultarReportePorTipo() {

        String tipo = seleccionarTipoEmpleado();

        gestorReportes.generarReportePorTipo(tipo);
    }

    // Consultar historial
    private void consultarHistorial() {

        System.out.println("\n===== HISTORIAL DE CALCULOS =====");

        List<RegistroCalculo> historial =
                gestorCalculos.consultarHistorial();

        if (historial.isEmpty()) {
            System.out.println("No existen calculos registrados.");
            return;
        }

        for (RegistroCalculo registro : historial) {
            registro.mostrarRegistro();
            System.out.println("-------------------------");
        }
    }

    // Consultar empleados por tipo
    private void consultarPorTipo() {

        System.out.println("\n===== CONSULTAR POR TIPO =====");

        String tipo = seleccionarTipoEmpleado();

        List<Empleado> empleados =
                gestorEmpleados.consultarPorTipo(tipo);

        mostrarListaEmpleados(empleados);
    }

    // Ordenar empleados
    private void ordenarEmpleados() {

        System.out.println("\n===== ORDENAR EMPLEADOS =====");
        System.out.println("1. Por codigo");
        System.out.println("2. Por nombre");
        System.out.println("3. Por tipo");

        int opcion = leerOpcion("Seleccione una opcion: ", 1, 3);

        List<Empleado> empleados =
                gestorEmpleados.consultarEmpleados();

        List<Empleado> ordenados;

        switch (opcion) {

            case 1:
                ordenados = ordenador.ordenarPorCodigo(empleados);
                break;

            case 2:
                ordenados = ordenador.ordenarPorNombre(empleados);
                break;

            default:
                ordenados = ordenador.ordenarPorTipo(empleados);
                break;
        }

        mostrarListaEmpleados(ordenados);
    }

    // Seleccionar tipo de empleado
    private String seleccionarTipoEmpleado() {

        System.out.println("1. Administrativo");
        System.out.println("2. Por hora");
        System.out.println("3. Por comision");

        int opcion = leerOpcion("Seleccione el tipo: ", 1, 3);

        switch (opcion) {

            case 1:
                return "Administrativo";

            case 2:
                return "Por Hora";

            default:
                return "Por Comision";
        }
    }

    // Mostrar una lista de empleados
    private void mostrarListaEmpleados(List<Empleado> empleados) {

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para mostrar.");
            return;
        }

        for (Empleado empleado : empleados) {
            empleado.mostrarInformacion();
            System.out.println("-------------------------");
        }
    }

    // Leer texto obligatorio
    private String leerTexto(String mensaje) {

        String texto;

        do {
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("Este campo es obligatorio.");
            }

        } while (texto.isEmpty());

        return texto;
    }

    // Leer una opcion numerica
    private int leerOpcion(String mensaje, int minimo, int maximo) {

        int opcion;

        while (true) {

            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();

            try {
                opcion = Integer.parseInt(entrada);

                if (opcion >= minimo && opcion <= maximo) {
                    return opcion;
                }

                System.out.println("Seleccione una opcion entre "
                        + minimo + " y " + maximo + ".");

            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    // Leer un numero decimal no negativo
    private double leerDoubleNoNegativo(String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim().replace(',', '.');

            try {
                double numero = Double.parseDouble(entrada);

                if (Double.isFinite(numero) && numero >= 0) {
                    return numero;
                }

                System.out.println("El valor no puede ser negativo.");

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    // Leer un numero entero no negativo
    private int leerEnteroNoNegativo(String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();

            try {
                int numero = Integer.parseInt(entrada);

                if (numero >= 0) {
                    return numero;
                }

                System.out.println("El valor no puede ser negativo.");

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    // Leer porcentaje entre cero y cien
    private double leerPorcentaje(String mensaje) {

        while (true) {

            double porcentaje = leerDoubleNoNegativo(mensaje);

            if (porcentaje <= 100) {
                return porcentaje;
            }

            System.out.println(
                    "El porcentaje debe estar entre 0 y 100.");
        }
    }

    // Leer fecha de ingreso
    private LocalDate leerFecha(String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();

            try {
                LocalDate fecha = LocalDate.parse(entrada);

                if (ValidadorEmpleados.validarFecha(fecha)) {
                    return fecha;
                }

                System.out.println(
                        "La fecha no puede ser posterior al dia actual.");

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Fecha incorrecta. Utilice el formato AAAA-MM-DD.");
            }
        }
    }
}
