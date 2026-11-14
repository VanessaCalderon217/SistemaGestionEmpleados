/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import com.sistemaempleados.GestorUsuarios;

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
public class PrimerUsuarioFrame extends JFrame {
    private JTextField txtUsuario;
    private JTextField txtNombre;
    private JPasswordField txtClave;
    private JPasswordField txtConfirmar;
    private GestorUsuarios gestorUsuarios;
    
    public PrimerUsuarioFrame() {

        gestorUsuarios = new GestorUsuarios();

        setTitle("Registrar primer usuario");
        setSize(420, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        crearComponentes();
    }
    
    private void crearComponentes() {

        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("No hay usuarios. Registre el primero",
                SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(4, 2, 5, 10));

        txtUsuario = new JTextField();
        txtNombre = new JTextField();
        txtClave = new JPasswordField();
        txtConfirmar = new JPasswordField();

        campos.add(new JLabel("Usuario:"));
        campos.add(txtUsuario);
        campos.add(new JLabel("Nombre completo:"));
        campos.add(txtNombre);
        campos.add(new JLabel("Contrasena:"));
        campos.add(txtClave);
        campos.add(new JLabel("Confirmar contrasena:"));
        campos.add(txtConfirmar);

        panel.add(campos, BorderLayout.CENTER);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());
        panel.add(btnRegistrar, BorderLayout.SOUTH);

        add(panel);
    }
     // Validar y registrar el usuario
    private void registrar() {

        String usuario = txtUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String clave = new String(txtClave.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (usuario.isEmpty() || nombre.isEmpty() || clave.isEmpty()) {
            mostrarAviso("Complete todos los campos.");
            return;
        }

        if (!GestorUsuarios.validarNombreUsuario(usuario)) {
            mostrarAviso("El usuario debe tener de 4 a 20 letras o numeros, sin espacios.");
            txtUsuario.requestFocus();
            return;
        }

        if (!GestorUsuarios.validarClave(clave)) {
            mostrarAviso("La contrasena debe tener al menos 6 caracteres.");
            txtClave.requestFocus();
            return;
        }

        if (!clave.equals(confirmar)) {
            mostrarAviso("Las contrasenas no coinciden.");
            txtConfirmar.requestFocus();
            return;
        }

        try {
            if (gestorUsuarios.registrarUsuario(usuario, nombre, clave)) {
                JOptionPane.showMessageDialog(this,
                        "Usuario registrado. Ahora puede iniciar sesion.",
                        "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginFrame().setVisible(true);
            } else {
                mostrarAviso("No se pudo registrar el usuario.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar con MongoDB.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mostrar un mensaje de advertencia
    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,
                "Validacion", JOptionPane.WARNING_MESSAGE);
    }

 

}
