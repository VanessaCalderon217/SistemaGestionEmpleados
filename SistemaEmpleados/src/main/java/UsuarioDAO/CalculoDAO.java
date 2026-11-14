/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UsuarioDAO;
import com.mongodb.client.MongoCollection;
import com.sistemaempleados.conexionba.ConexionMongo;
import com.sistemaempleados.modelo.RegistroCalculo;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author BryanAlfaro
 */
public class CalculoDAO {

    // Coleccion de calculos
    private MongoCollection<Document> coleccion;

    // Constructor
    public CalculoDAO() {
        coleccion = ConexionMongo.getBaseDatos().getCollection("calculos");
    }

    // Guardar un calculo
    public void insertar(RegistroCalculo registro) {

        Document documento = new Document()
                .append("codigoEmpleado", registro.getCodigoEmpleado())
                .append("nombreEmpleado", registro.getNombreEmpleado())
                .append("tipoEmpleado", registro.getTipoEmpleado())
                .append("periodo", registro.getPeriodo())
                .append("fechaCalculo", registro.getFechaCalculo().toString())
                .append("salarioMensual", registro.getSalarioMensual())
                .append("precioPorHora", registro.getPrecioPorHora())
                .append("horasTrabajadas", registro.getHorasTrabajadas())
                .append("porcentajeComision", registro.getPorcentajeComision())
                .append("ventasTotales", registro.getVentasTotales())
                .append("remuneracion", registro.getRemuneracion());

        coleccion.insertOne(documento);
    }

    // Obtener todo el historial
    public List<RegistroCalculo> listarTodos() {

        List<RegistroCalculo> lista = new ArrayList<>();

        for (Document documento : coleccion.find()) {

            RegistroCalculo registro = new RegistroCalculo(
                    documento.getString("codigoEmpleado"),
                    documento.getString("nombreEmpleado"),
                    documento.getString("tipoEmpleado"),
                    documento.getString("periodo"),
                    documento.getDouble("salarioMensual"),
                    documento.getDouble("precioPorHora"),
                    documento.getInteger("horasTrabajadas"),
                    documento.getDouble("porcentajeComision"),
                    documento.getDouble("ventasTotales"),
                    documento.getDouble("remuneracion"));

            registro.setFechaCalculo(LocalDate.parse(
                    documento.getString("fechaCalculo")));

            lista.add(registro);
        }

        return lista;
    }
}
