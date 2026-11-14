package com.sistemaempleados;

import vista.LoginFrame;
import vista.PrimerUsuarioFrame;

import javax.swing.JOptionPane;

public class Principal {

    // Metodo principal
    public static void main(String[] args) {

        try {
            GestorUsuarios gestorUsuarios = new GestorUsuarios();

            // Si no hay usuarios se registra el primero, si hay se pide login
            if (gestorUsuarios.hayUsuarios()) {
                new LoginFrame().setVisible(true);
            } else {
                new PrimerUsuarioFrame().setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar con MongoDB.\n"
                    + "Verifique que MongoDB este encendido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
