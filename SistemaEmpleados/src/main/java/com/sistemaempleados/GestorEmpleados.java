
package com.sistemaempleados;

import com.sistemaempleados.modelo.Empleado;
import com.sistemaempleados.modelo.EmpleadoAdministrativo;
import com.sistemaempleados.modelo.EmpleadoPorHora;
import com.sistemaempleados.modelo.EmpleadoPorComision;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import UsuarioDAO.EmpleadoDAO;

public class GestorEmpleados {

    // Lista de empleados
    private List<Empleado> empleados;
    
    // Acceso a la base de datos
    private EmpleadoDAO empleadoDAO;

    // Constructor
    public GestorEmpleados() {
        empleadoDAO = new EmpleadoDAO();
        empleados = empleadoDAO.listarTodos();
    }

    // Validar los datos de cada tipo de empleado
    private boolean validarDatosEspecificos(Empleado empleado) {

        if (empleado instanceof EmpleadoAdministrativo) {

            EmpleadoAdministrativo administrativo =
                    (EmpleadoAdministrativo) empleado;

            return ValidadorEmpleados.validarSalario(
                    administrativo.getSalarioMensual());

        } else if (empleado instanceof EmpleadoPorHora) {

            EmpleadoPorHora empleadoHora =
                    (EmpleadoPorHora) empleado;

            return ValidadorEmpleados.validarPrecioPorHora(
                    empleadoHora.getPrecioPorHora())
                    && ValidadorEmpleados.validarHoras(
                    empleadoHora.getHorasTrabajadas());

        } else if (empleado instanceof EmpleadoPorComision) {

            EmpleadoPorComision empleadoComision =
                    (EmpleadoPorComision) empleado;

            return ValidadorEmpleados.validarPorcentajeComision(
                    empleadoComision.getPorcentajeComision())
                    && ValidadorEmpleados.validarVentas(
                    empleadoComision.getVentasTotales());
        }

        return false;
    }

    // Registrar empleado
    public boolean registrarEmpleado(Empleado empleado) {

        if (empleado == null) {
            return false;
        }

        if (!ValidadorEmpleados.validarTexto(empleado.getCodigo())
                || !ValidadorEmpleados.validarTexto(empleado.getNombres())
                || !ValidadorEmpleados.validarTexto(empleado.getApellidos())
                || !ValidadorEmpleados.validarTexto(empleado.getDocumento())
                || !ValidadorEmpleados.validarTexto(empleado.getCargo())
                || !ValidadorEmpleados.validarFecha(
                        empleado.getFechaIngreso())) {
            return false;
        }

        if (!ValidadorEmpleados.validarCodigo(
                empleado.getCodigo(), this)) {
            return false;
        }

        if (!ValidadorEmpleados.validarDocumento(
                empleado.getDocumento(), this)) {
            return false;
        }

        if (!validarDatosEspecificos(empleado)) {
            return false;
        }

        empleadoDAO.insertar(empleado);
        empleados.add(empleado);
        return true;
    }

    // Buscar empleado por codigo
    public Empleado buscarPorCodigo(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (Empleado empleado : empleados) {

            if (empleado.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return empleado;
            }
        }

        return null;
    }

    // Buscar empleado por documento
    public Empleado buscarPorDocumento(String documento) {

        if (documento == null) {
            return null;
        }

        for (Empleado empleado : empleados) {

            if (empleado.getDocumento().equalsIgnoreCase(
                    documento.trim())) {
                return empleado;
            }
        }

        return null;
    }

    // Buscar empleados por nombre
    public List<Empleado> buscarPorNombre(String nombre) {

        List<Empleado> encontrados = new ArrayList<>();

        if (nombre == null) {
            return encontrados;
        }

        for (Empleado empleado : empleados) {

            if (empleado.getNombreCompleto().toLowerCase()
                    .contains(nombre.trim().toLowerCase())) {
                encontrados.add(empleado);
            }
        }

        return encontrados;
    }

    // Consultar todos los empleados
    public List<Empleado> consultarEmpleados() {
        return new ArrayList<>(empleados);
    }

    // Modificar datos generales del empleado
    public boolean modificarEmpleado(String codigo,
            String nombres, String apellidos,
            String documento, String cargo,
            LocalDate fechaIngreso) {

        Empleado empleado = buscarPorCodigo(codigo);

        if (empleado == null || !empleado.estaActivo()) {
            return false;
        }

        if (!ValidadorEmpleados.validarTexto(nombres)
                || !ValidadorEmpleados.validarTexto(apellidos)
                || !ValidadorEmpleados.validarTexto(documento)
                || !ValidadorEmpleados.validarTexto(cargo)
                || !ValidadorEmpleados.validarFecha(fechaIngreso)) {
            return false;
        }

        if (!ValidadorEmpleados.validarDocumentoModificado(
                documento, codigo, this)) {
            return false;
        }

        empleado.modificarDatos(nombres, apellidos,
                documento, cargo, fechaIngreso);

        empleadoDAO.actualizar(empleado);
        return true;
    }

    // Modificar salario de empleado administrativo
    public boolean modificarSalarioAdministrativo(
            String codigo, double salario) {

        Empleado empleado = buscarPorCodigo(codigo);

        if (empleado == null || !empleado.estaActivo()
                || !(empleado instanceof EmpleadoAdministrativo)) {
            return false;
        }

        if (!ValidadorEmpleados.validarSalario(salario)) {
            return false;
        }

        EmpleadoAdministrativo administrativo =
                (EmpleadoAdministrativo) empleado;

        administrativo.setSalarioMensual(salario);
        empleadoDAO.actualizar(administrativo);
        return true;
    }

    // Modificar datos de empleado por hora
    public boolean modificarEmpleadoPorHora(
            String codigo, double precio, int horas) {

        Empleado empleado = buscarPorCodigo(codigo);

        if (empleado == null || !empleado.estaActivo()
                || !(empleado instanceof EmpleadoPorHora)) {
            return false;
        }

        if (!ValidadorEmpleados.validarPrecioPorHora(precio)
                || !ValidadorEmpleados.validarHoras(horas)) {
            return false;
        }

        EmpleadoPorHora empleadoHora = (EmpleadoPorHora) empleado;

        empleadoHora.setPrecioPorHora(precio);
        empleadoHora.setHorasTrabajadas(horas);

        empleadoDAO.actualizar(empleadoHora);
        return true;
    }

    // Modificar datos de empleado por comision
    public boolean modificarEmpleadoPorComision(
            String codigo, double porcentaje, double ventas) {

        Empleado empleado = buscarPorCodigo(codigo);

        if (empleado == null || !empleado.estaActivo()
                || !(empleado instanceof EmpleadoPorComision)) {
            return false;
        }

        if (!ValidadorEmpleados.validarPorcentajeComision(porcentaje)
                || !ValidadorEmpleados.validarVentas(ventas)) {
            return false;
        }

        EmpleadoPorComision empleadoComision =
                (EmpleadoPorComision) empleado;

        empleadoComision.setPorcentajeComision(porcentaje);
        empleadoComision.setVentasTotales(ventas);

        empleadoDAO.actualizar(empleadoComision);
        return true;
    }

    // Inactivar empleado sin eliminar sus datos
    public boolean inactivarEmpleado(String codigo) {

        Empleado empleado = buscarPorCodigo(codigo);

        if (empleado == null || !empleado.estaActivo()) {
            return false;
        }

        empleado.inactivar();
        empleadoDAO.actualizar(empleado);
        return true;
    }

    // Consultar empleados por tipo
    public List<Empleado> consultarPorTipo(String tipo) {

        List<Empleado> encontrados = new ArrayList<>();

        if (tipo == null) {
            return encontrados;
        }

        for (Empleado empleado : empleados) {

            if (empleado.getTipo().equalsIgnoreCase(tipo.trim())) {
                encontrados.add(empleado);
            }
        }

        return encontrados;
    }

    // Consultar solamente empleados activos
    public List<Empleado> consultarActivos() {

        List<Empleado> activos = new ArrayList<>();

        for (Empleado empleado : empleados) {

            if (empleado.estaActivo()) {
                activos.add(empleado);
            }
        }

        return activos;
    }

    // Calcular remuneracion de un empleado
    public double calcularRemuneracion(String codigo) {

        Empleado empleado = buscarPorCodigo(codigo);

        if (empleado == null || !empleado.estaActivo()) {
            return -1;
        }

        return empleado.calcularSalario();
    }

    // Obtener cantidad de empleados
    public int obtenerCantidadEmpleados() {
        return empleados.size();
    }

    // Calcular total de remuneraciones de empleados activos
    public double calcularTotalRemuneraciones() {

        double total = 0;

        for (Empleado empleado : empleados) {

            if (empleado.estaActivo()) {
                total += empleado.calcularSalario();
            }
        }

        return total;
    }
}
