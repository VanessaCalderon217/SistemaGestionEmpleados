/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemaempleados;
import com.sistemaempleados.modelo.Usuario;
/**
 *
 * @author BryanAlfaro
 */
public class Sesion {
    private static Usuario usuarioActual;
    
    public static void iniciar(Usuario usuario) {
        usuarioActual = usuario;
    }
    
    public static void cerrar() {
        usuarioActual = null;
    }
    
    public static boolean haySesionActiva() {
        return usuarioActual != null;
    }
    
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }


}
