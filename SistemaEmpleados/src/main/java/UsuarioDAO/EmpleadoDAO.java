/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UsuarioDAO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sistemaempleados.conexionba.ConexionMongo;
import com.sistemaempleados.modelo.Empleado;
import com.sistemaempleados.modelo.EmpleadoAdministrativo;
import com.sistemaempleados.modelo.EmpleadoPorComision;
import com.sistemaempleados.modelo.EmpleadoPorHora;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BryanAlfaro
 */
public class EmpleadoDAO {

    // Coleccion de empleados
    private MongoCollection<Document> coleccion;

    // Constructor
    public EmpleadoDAO() {
        coleccion = ConexionMongo.getBaseDatos().getCollection("empleados");
    }

    // Guardar un empleado nuevo
    public void insertar(Empleado empleado) {
        coleccion.insertOne(convertirADocumento(empleado));
    }

    // Actualizar un empleado
    public void actualizar(Empleado empleado) {
        coleccion.replaceOne(Filters.eq("codigo", empleado.getCodigo()),
                convertirADocumento(empleado));
    }

    // Obtener todos los empleados
    public List<Empleado> listarTodos() {

        List<Empleado> lista = new ArrayList<>();

        for (Document documento : coleccion.find()) {
            lista.add(convertirAEmpleado(documento));
        }

        return lista;
    }

    // Convertir empleado a documento
    private Document convertirADocumento(Empleado empleado) {

        Document documento = new Document()
                .append("codigo", empleado.getCodigo())
                .append("nombres", empleado.getNombres())
                .append("apellidos", empleado.getApellidos())
                .append("documento", empleado.getDocumento())
                .append("cargo", empleado.getCargo())
                .append("tipo", empleado.getTipo())
                .append("fechaIngreso", empleado.getFechaIngreso().toString())
                .append("activo", empleado.isActivo());

        // Datos propios de cada tipo
        if (empleado instanceof EmpleadoAdministrativo) {

            EmpleadoAdministrativo administrativo = (EmpleadoAdministrativo) empleado;
            documento.append("salarioMensual", administrativo.getSalarioMensual());

        } else if (empleado instanceof EmpleadoPorHora) {

            EmpleadoPorHora porHora = (EmpleadoPorHora) empleado;
            documento.append("precioPorHora", porHora.getPrecioPorHora());
            documento.append("horasTrabajadas", porHora.getHorasTrabajadas());

        } else if (empleado instanceof EmpleadoPorComision) {

            EmpleadoPorComision porComision = (EmpleadoPorComision) empleado;
            documento.append("porcentajeComision", porComision.getPorcentajeComision());
            documento.append("ventasTotales", porComision.getVentasTotales());
        }

        return documento;
    }

    // Convertir documento a empleado
    private Empleado convertirAEmpleado(Document documento) {

        String codigo = documento.getString("codigo");
        String nombres = documento.getString("nombres");
        String apellidos = documento.getString("apellidos");
        String dui = documento.getString("documento");
        String cargo = documento.getString("cargo");
        String tipo = documento.getString("tipo");
        LocalDate fecha = LocalDate.parse(documento.getString("fechaIngreso"));

        Empleado empleado;

        if (tipo.equals("Administrativo")) {

            empleado = new EmpleadoAdministrativo(codigo, nombres, apellidos,
                    dui, cargo, fecha, documento.getDouble("salarioMensual"));

        } else if (tipo.equals("Por Hora")) {

            empleado = new EmpleadoPorHora(codigo, nombres, apellidos,
                    dui, cargo, fecha, documento.getDouble("precioPorHora"),
                    documento.getInteger("horasTrabajadas"));

        } else {

            empleado = new EmpleadoPorComision(codigo, nombres, apellidos,
                    dui, cargo, fecha, documento.getDouble("porcentajeComision"),
                    documento.getDouble("ventasTotales"));
        }

        if (!documento.getBoolean("activo", true)) {
            empleado.inactivar();
        }

        return empleado;
    }
}