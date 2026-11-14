package vista;

import com.sistemaempleados.Sesion;
import com.sistemaempleados.conexionba.ConexionMongo;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuPrincipal extends JFrame {

    private JTabbedPane pestanas;

    // Constructor
    public MenuPrincipal() {

        setTitle("Sistema de Administracion de Empleados");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Preguntar antes de salir
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir();
            }
        });

        crearComponentes();
    }

    // Crear los componentes de la ventana
    private void crearComponentes() {

        setLayout(new BorderLayout());

        // Barra superior
        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel lblUsuario = new JLabel("Usuario: "
                + Sesion.getUsuarioActual().getNombre());
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 13));
        barraSuperior.add(lblUsuario, BorderLayout.WEST);

        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        barraSuperior.add(btnCerrarSesion, BorderLayout.EAST);

        add(barraSuperior, BorderLayout.NORTH);

        // Pestanas
        pestanas = new JTabbedPane();
        pestanas.addTab("Empleados", crearPanelVacio("Empleados"));
        pestanas.addTab("Calculos", crearPanelVacio("Calculos"));
        pestanas.addTab("Reportes", crearPanelVacio("Reportes"));
        pestanas.addTab("Usuarios", crearPanelVacio("Usuarios"));

        add(pestanas, BorderLayout.CENTER);
    }

    // Panel temporal hasta crear cada pantalla
    private JPanel crearPanelVacio(String nombre) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Modulo de " + nombre, SwingConstants.CENTER),
                BorderLayout.CENTER);
        return panel;
    }

    // Cerrar la sesion y regresar al login
    private void cerrarSesion() {

        int respuesta = JOptionPane.showConfirmDialog(this,
                "Desea cerrar la sesion?", "Cerrar sesion",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            Sesion.cerrar();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    // Salir del sistema
    private void salir() {

        int respuesta = JOptionPane.showConfirmDialog(this,
                "Desea salir del sistema?", "Salir",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            Sesion.cerrar();
            ConexionMongo.cerrar();
            System.exit(0);
        }
    }
}
