/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import com.sistemaempleados.GestorUsuarios;
import com.sistemaempleados.Sesion;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
/**
 *
 * @author BryanAlfaro
 */
public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;

    private GestorUsuarios gestorUsuarios;

    // Constructor
    public LoginFrame() {

        gestorUsuarios = new GestorUsuarios();

        setTitle("Inicio de sesion");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        crearComponentes();
    }

    // Crear los componentes de la ventana
    private void crearComponentes() {

        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

        JLabel lblTitulo = new JLabel("Sistema de Administracion de Empleados",
                SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(4, 1, 5, 5));

        txtUsuario = new JTextField();
        txtClave = new JPasswordField();

        campos.add(new JLabel("Usuario:"));
        campos.add(txtUsuario);
        campos.add(new JLabel("Contrasena:"));
        campos.add(txtClave);

        panel.add(campos, BorderLayout.CENTER);

        JButton btnIngresar = new JButton("Iniciar sesion");
        btnIngresar.addActionListener(e -> iniciarSesion());
        panel.add(btnIngresar, BorderLayout.SOUTH);

        // Iniciar sesion con Enter
        getRootPane().setDefaultButton(btnIngresar);

        add(panel);
    }

    // Validar los datos e iniciar sesion
    private void iniciarSesion() {

        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());

        // Validar campos vacios
        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese su usuario y contrasena.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean accesoCorrecto;

        try {
            accesoCorrecto = gestorUsuarios.iniciarSesion(usuario, clave);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar con MongoDB.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (accesoCorrecto) {
            JOptionPane.showMessageDialog(this,
                    "Bienvenido " + Sesion.getUsuarioActual().getNombre());
            dispose();
            return;
        }

        // Credenciales incorrectas
        txtClave.setText("");
        JOptionPane.showMessageDialog(this,
                "Usuario o contrasena incorrectos.",
                "Acceso denegado", JOptionPane.ERROR_MESSAGE);
    }

    // Para probar la pantalla
    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}
