/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemaempleados;
import UsuarioDAO.UsuarioDAO;
import com.sistemaempleados.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author BryanAlfaro
 */
public class GestorUsuarios {
    private UsuarioDAO usuarioDAO;
    
    public GestorUsuarios() {
        usuarioDAO = new UsuarioDAO();
    }
    
    public boolean registrarUsuario(String nombreUsuario,
            String nombre, String clave) {

        if (!validarNombreUsuario(nombreUsuario)
                || !ValidadorEmpleados.validarTexto(nombre)
                || !validarClave(clave)) {
            return false;
        }

        String usuarioMinusculas = nombreUsuario.trim().toLowerCase();

        // El usuario no puede estar repetido
        if (usuarioDAO.buscarPorUsuario(usuarioMinusculas) != null) {
            return false;
        }

        Usuario usuario = new Usuario(usuarioMinusculas,
                nombre.trim(), generarHash(clave), true);

        usuarioDAO.insertar(usuario);
        return true;
    }
    
    public static boolean validarNombreUsuario(String nombreUsuario) {

        return nombreUsuario != null
                && nombreUsuario.trim().matches("[a-zA-Z0-9_]{4,20}");
    }
    
    public static boolean validarClave(String clave) {

        return clave != null && clave.length() >= 6;
    }
    
    public static String generarHash(String clave) {
        return BCrypt.hashpw(clave, BCrypt.gensalt(10));
    }

    // Saber si existe al menos un usuario
    public boolean hayUsuarios() {
        return usuarioDAO.hayUsuarios();
    }
}
