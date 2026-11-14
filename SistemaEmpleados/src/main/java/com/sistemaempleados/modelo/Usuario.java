/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemaempleados.modelo;

/**
 *
 * @author BryanAlfaro
 */
public class Usuario {
    private String usuario;
    private String nombre;
    private String passwordHash;
    private boolean activo;
    
    public Usuario(String usuario, String nombre,
            String passwordHash, boolean activo) {

        this.usuario = usuario;
        this.nombre = nombre;
        this.passwordHash = passwordHash;
        this.activo = activo;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
