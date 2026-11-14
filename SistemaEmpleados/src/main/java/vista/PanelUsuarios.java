package vista;

import com.sistemaempleados.GestorUsuarios;
import com.sistemaempleados.Sesion;
import com.sistemaempleados.modelo.Usuario;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PanelUsuarios extends JPanel {

    private GestorUsuarios gestorUsuarios;

    // Campos del formulario
    private JTextField txtUsuario;
    private JTextField txtNombre;
    private JPasswordField txtClave;
    private JPasswordField txtConfirmar;

    // Botones
    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnEstado;

    // Tabla de usuarios
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Constructor
    public PanelUsuarios() {

        gestorUsuarios = new GestorUsuarios();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearFormulario(), BorderLayout.WEST);
        add(crearTabla(), BorderLayout.CENTER);

        actualizar();
    }

    // Crear el formulario
    private JPanel crearFormulario() {

        JPanel panel = new JPanel(new BorderLayout(5, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del usuario"));

        JPanel campos = new JPanel(new GridLayout(4, 2, 5, 8));

        txtUsuario = new JTextField(12);
        txtNombre = new JTextField(12);
        txtClave = new JPasswordField(12);
        txtConfirmar = new JPasswordField(12);

        campos.add(new JLabel("Usuario:"));
        campos.add(txtUsuario);
        campos.add(new JLabel("Nombre completo:"));
        campos.add(txtNombre);
        campos.add(new JLabel("Contrasena:"));
        campos.add(txtClave);
        campos.add(new JLabel("Confirmar contrasena:"));
        campos.add(txtConfirmar);

        panel.add(campos, BorderLayout.NORTH);

        JLabel lblAyuda = new JLabel("<html>Al modificar, deje la contrasena<br>"
                + "en blanco para no cambiarla.</html>");
        panel.add(lblAyuda, BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(2, 2, 5, 5));

        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnEstado = new JButton("Activar / Inactivar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnModificar.addActionListener(e -> modificarUsuario());
        btnEstado.addActionListener(e -> cambiarEstado());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnEstado);
        botones.add(btnLimpiar);

        panel.add(botones, BorderLayout.SOUTH);

        btnModificar.setEnabled(false);
        btnEstado.setEnabled(false);

        return panel;
    }

    // Crear la tabla
    private JPanel crearTabla() {

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        String[] columnas = {"Usuario", "Nombre", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(22);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarUsuarioSeleccionado();
            }
        });

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(new JLabel("Seleccione un usuario para modificarlo."), BorderLayout.SOUTH);

        return panel;
    }

    // Actualizar la tabla
    public void actualizar() {

        modeloTabla.setRowCount(0);

        try {
            List<Usuario> usuarios = gestorUsuarios.consultarUsuarios();

            for (Usuario usuario : usuarios) {

                Object[] fila = {
                    usuario.getUsuario(),
                    usuario.getNombre(),
                    usuario.isActivo() ? "Activo" : "Inactivo"
                };

                modeloTabla.addRow(fila);
            }

        } catch (Exception e) {
            mostrarErrorBaseDatos(e);
        }
    }

    // Registrar usuario
    private void registrarUsuario() {

        String usuario = txtUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String clave = new String(txtClave.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (usuario.isEmpty() || nombre.isEmpty() || clave.isEmpty()) {
            mostrarAviso("Complete el usuario, el nombre y la contrasena.");
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
            if (gestorUsuarios.buscarUsuario(usuario.toLowerCase()) != null) {
                mostrarAviso("El usuario " + usuario + " ya existe.");
                txtUsuario.requestFocus();
                return;
            }

            if (gestorUsuarios.registrarUsuario(usuario, nombre, clave)) {
                mostrarMensaje("Usuario registrado correctamente.");
                limpiarFormulario();
                actualizar();
            } else {
                mostrarAviso("No se pudo registrar el usuario.");
            }

        } catch (Exception e) {
            mostrarErrorBaseDatos(e);
        }
    }

    // Modificar usuario
    private void modificarUsuario() {

        String usuario = txtUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String clave = new String(txtClave.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (nombre.isEmpty()) {
            mostrarAviso("El nombre es obligatorio.");
            txtNombre.requestFocus();
            return;
        }

        if (!clave.isEmpty()) {

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
        }

        try {
            if (gestorUsuarios.modificarUsuario(usuario, nombre, clave)) {
                mostrarMensaje("Usuario modificado correctamente.");
                limpiarFormulario();
                actualizar();
            } else {
                mostrarAviso("No se pudo modificar el usuario.");
            }

        } catch (Exception e) {
            mostrarErrorBaseDatos(e);
        }
    }

    // Activar o inactivar usuario
    private void cambiarEstado() {

        String usuario = txtUsuario.getText().trim();

        try {
            Usuario seleccionado = gestorUsuarios.buscarUsuario(usuario);

            if (seleccionado == null) {
                mostrarAviso("El usuario no existe.");
                return;
            }

            // No se puede inactivar el usuario que tiene la sesion abierta
            if (usuario.equals(Sesion.getUsuarioActual().getUsuario())) {
                mostrarAviso("No puede inactivar su propio usuario.");
                return;
            }

            String accion = seleccionado.isActivo() ? "inactivar" : "activar";

            int respuesta = JOptionPane.showConfirmDialog(this,
                    "Desea " + accion + " al usuario " + usuario + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            if (gestorUsuarios.cambiarEstado(usuario)) {
                mostrarMensaje("Estado actualizado correctamente.");
                limpiarFormulario();
                actualizar();
            }

        } catch (Exception e) {
            mostrarErrorBaseDatos(e);
        }
    }

    // Cargar el usuario seleccionado
    private void cargarUsuarioSeleccionado() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            return;
        }

        txtUsuario.setText((String) modeloTabla.getValueAt(fila, 0));
        txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
        txtClave.setText("");
        txtConfirmar.setText("");

        txtUsuario.setEditable(false);
        btnRegistrar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEstado.setEnabled(true);
    }

    // Limpiar formulario
    private void limpiarFormulario() {

        txtUsuario.setText("");
        txtNombre.setText("");
        txtClave.setText("");
        txtConfirmar.setText("");

        txtUsuario.setEditable(true);
        btnRegistrar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEstado.setEnabled(false);

        tabla.clearSelection();
    }

    // Mensajes para el usuario
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,
                "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,
                "Validacion", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErrorBaseDatos(Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error en la base de datos.\n" + e.getMessage(),
                "Error de MongoDB", JOptionPane.ERROR_MESSAGE);
    }
}
