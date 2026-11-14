/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemaempleados.conexionba;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
/**
 *
 * @author BryanAlfaro
 */
public class ConexionMongo {
    private static final String URL = "mongodb://localhost:27017";
    private static final String NOMBRE_BD = "sistema_empleados";
    private static MongoClient cliente;
    
    public static MongoDatabase getBaseDatos() {

        if (cliente == null) {
            cliente = MongoClients.create(URL);
        }

        return cliente.getDatabase(NOMBRE_BD);
    }
    public static void cerrar() {

        if (cliente != null) {
            cliente.close();
            cliente = null;
        }
    }
}
