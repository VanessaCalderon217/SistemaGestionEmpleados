/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemaempleados;
import UsuarioDAO.UsuarioDAO;
import com.sistemaempleados.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

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
    
    // Iniciar sesion
    public boolean iniciarSesion(String nombreUsuario, String clave) {

        if (!ValidadorEmpleados.validarTexto(nombreUsuario)
                || !ValidadorEmpleados.validarTexto(clave)) {
            return false;
        }

        Usuario usuario = usuarioDAO.buscarPorUsuario(
                nombreUsuario.trim().toLowerCase());

        if (usuario == null || !usuario.isActivo()) {
            return false;
        }

        if (!BCrypt.checkpw(clave, usuario.getPasswordHash())) {
            return false;
        }

        Sesion.iniciar(usuario);
        return true;
    }

    // Modificar nombre y contrasena
    public boolean modificarUsuario(String nombreUsuario,
            String nombre, String claveNueva) {

        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);

        if (usuario == null || !ValidadorEmpleados.validarTexto(nombre)) {
            return false;
        }

        usuario.setNombre(nombre.trim());

        // La contrasena solo cambia si se escribe una nueva
        if (ValidadorEmpleados.validarTexto(claveNueva)) {

            if (!validarClave(claveNueva)) {
                return false;
            }

            usuario.setPasswordHash(generarHash(claveNueva));
        }

        usuarioDAO.actualizar(usuario);
        return true;
    }

    // Activar o inactivar usuario
    public boolean cambiarEstado(String nombreUsuario) {

        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);

        if (usuario == null) {
            return false;
        }

        usuario.setActivo(!usuario.isActivo());
        usuarioDAO.actualizar(usuario);
        return true;
    }

    // Buscar usuario
    public Usuario buscarUsuario(String nombreUsuario) {
        return usuarioDAO.buscarPorUsuario(nombreUsuario);
    }

    // Consultar usuarios
    public List<Usuario> consultarUsuarios() {
        return usuarioDAO.listarTodos();
    }
}
