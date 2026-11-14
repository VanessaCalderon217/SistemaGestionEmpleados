/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemaempleados.conexionba;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author BryanAlfaro
 */
public class PruebaConexion {
    public static void main(String[] args) {

        MongoDatabase bd = ConexionMongo.getBaseDatos();

        System.out.println("Conectado a: " + bd.getName());
        System.out.println("Colecciones:");

        for (String nombre : bd.listCollectionNames()) {
            System.out.println("- " + nombre);
        }

        ConexionMongo.cerrar();
    }
    
}
