package vista;

import com.sistemaempleados.GestorEmpleados;
import com.sistemaempleados.OrdenadorEmpleados;
import com.sistemaempleados.ValidadorEmpleados;
import com.sistemaempleados.modelo.Empleado;
import com.sistemaempleados.modelo.EmpleadoAdministrativo;
import com.sistemaempleados.modelo.EmpleadoPorComision;
import com.sistemaempleados.modelo.EmpleadoPorHora;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PanelEmpleados extends JPanel {

    private GestorEmpleados gestorEmpleados;
    private OrdenadorEmpleados ordenador;

    // Campos del formulario
    private JTextField txtCodigo;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtDocumento;
    private JTextField txtCargo;
    private JTextField txtFecha;
    private JComboBox<String> cmbTipo;
    private JLabel lblDato1;
    private JLabel lblDato2;
    private JTextField txtDato1;
    private JTextField txtDato2;

    // Botones del formulario
    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnInactivar;
    private JButton btnLimpiar;

    // Controles de busqueda y filtros
    private JComboBox<String> cmbBuscarPor;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltroTipo;
    private JComboBox<String> cmbOrden;

    // Tabla de empleados
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Datos leidos del formulario
    private LocalDate fechaIngreso;
    private double dato1;
    private double dato2;

    // Constructor
    public PanelEmpleados(GestorEmpleados gestorEmpleados) {

        this.gestorEmpleados = gestorEmpleados;
        this.ordenador = new OrdenadorEmpleados();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearFormulario(), BorderLayout.WEST);
        add(crearPanelTabla(), BorderLayout.CENTER);

        cambiarCamposPorTipo();
        actualizar();
    }

    // Crear el formulario de datos del empleado
    private JPanel crearFormulario() {

        JPanel panel = new JPanel(new BorderLayout(5, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del empleado"));

        JPanel campos = new JPanel(new GridLayout(9, 2, 5, 8));

        txtCodigo = new JTextField(12);
        txtNombres = new JTextField(12);
        txtApellidos = new JTextField(12);
        txtDocumento = new JTextField(12);
        txtCargo = new JTextField(12);
        txtFecha = new JTextField(12);
        txtFecha.setToolTipText("Formato AAAA-MM-DD");

        cmbTipo = new JComboBox<>(new String[]{
            "Administrativo", "Por Hora", "Por Comision"});
        cmbTipo.addActionListener(e -> cambiarCamposPorTipo());

        lblDato1 = new JLabel();
        lblDato2 = new JLabel();
        txtDato1 = new JTextField(12);
        txtDato2 = new JTextField(12);

        campos.add(new JLabel("Codigo:"));
        campos.add(txtCodigo);
        campos.add(new JLabel("Nombres:"));
        campos.add(txtNombres);
        campos.add(new JLabel("Apellidos:"));
        campos.add(txtApellidos);
        campos.add(new JLabel("Documento (DUI):"));
        campos.add(txtDocumento);
        campos.add(new JLabel("Cargo:"));
        campos.add(txtCargo);
        campos.add(new JLabel("Fecha ingreso (AAAA-MM-DD):"));
        campos.add(txtFecha);
        campos.add(new JLabel("Tipo de empleado:"));
        campos.add(cmbTipo);
        campos.add(lblDato1);
        campos.add(txtDato1);
        campos.add(lblDato2);
        campos.add(txtDato2);

        panel.add(campos, BorderLayout.NORTH);

        // Botones
        JPanel botones = new JPanel(new GridLayout(2, 2, 5, 5));

        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnInactivar = new JButton("Inactivar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e -> registrarEmpleado());
        btnModificar.addActionListener(e -> modificarEmpleado());
        btnInactivar.addActionListener(e -> inactivarEmpleado());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnInactivar);
        botones.add(btnLimpiar);

        panel.add(botones, BorderLayout.SOUTH);

        btnModificar.setEnabled(false);
        btnInactivar.setEnabled(false);

        return panel;
    }

    // Crear la tabla con los controles de busqueda
    private JPanel crearPanelTabla() {

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        // Busqueda y filtros
        JPanel busqueda = new JPanel(new GridLayout(2, 1));
        JPanel filaBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel filaFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cmbBuscarPor = new JComboBox<>(new String[]{
            "Codigo", "Documento", "Nombre"});
        txtBuscar = new JTextField(15);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarEmpleado());

        JButton btnMostrarTodos = new JButton("Mostrar todos");
        btnMostrarTodos.addActionListener(e -> mostrarTodos());

        cmbFiltroTipo = new JComboBox<>(new String[]{
            "Todos", "Administrativo", "Por Hora", "Por Comision"});
        cmbFiltroTipo.addActionListener(e -> actualizar());

        cmbOrden = new JComboBox<>(new String[]{
            "Sin orden", "Codigo", "Nombre", "Tipo"});
        cmbOrden.addActionListener(e -> actualizar());

        filaBuscar.add(new JLabel("Buscar por:"));
        filaBuscar.add(cmbBuscarPor);
        filaBuscar.add(txtBuscar);
        filaBuscar.add(btnBuscar);
        filaBuscar.add(btnMostrarTodos);

        filaFiltros.add(new JLabel("Filtrar por tipo:"));
        filaFiltros.add(cmbFiltroTipo);
        filaFiltros.add(new JLabel("  Ordenar por:"));
        filaFiltros.add(cmbOrden);

        busqueda.add(filaBuscar);
        busqueda.add(filaFiltros);

        panel.add(busqueda, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Codigo", "Nombre completo", "Documento",
            "Cargo", "Tipo", "Fecha ingreso", "Datos de pago",
            "Remuneracion", "Estado"};

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
                if (e.getClickCount() == 2) {
                    mostrarDetalle();
                } else {
                    cargarEmpleadoSeleccionado();
                }
            }
        });

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JLabel lblAyuda = new JLabel(
                "Seleccione un empleado para modificarlo. Doble clic para ver el detalle.");
        panel.add(lblAyuda, BorderLayout.SOUTH);

        return panel;
    }

    // Cambiar las etiquetas segun el tipo de empleado
    private void cambiarCamposPorTipo() {

        String tipo = (String) cmbTipo.getSelectedItem();

        if (tipo.equals("Administrativo")) {
            lblDato1.setText("Salario mensual ($):");
            lblDato2.setText("");
            txtDato2.setText("");
            txtDato2.setVisible(false);

        } else if (tipo.equals("Por Hora")) {
            lblDato1.setText("Tarifa por hora ($):");
            lblDato2.setText("Horas trabajadas:");
            txtDato2.setVisible(true);

        } else {
            lblDato1.setText("Porcentaje comision (%):");
            lblDato2.setText("Monto de ventas ($):");
            txtDato2.setVisible(true);
        }
    }

    // Actualizar la tabla
    public void actualizar() {

        List<Empleado> lista;

        String tipo = (String) cmbFiltroTipo.getSelectedItem();

        if (tipo.equals("Todos")) {
            lista = gestorEmpleados.consultarEmpleados();
        } else {
            lista = gestorEmpleados.consultarPorTipo(tipo);
        }

        String orden = (String) cmbOrden.getSelectedItem();

        if (orden.equals("Codigo")) {
            lista = ordenador.ordenarPorCodigo(lista);
        } else if (orden.equals("Nombre")) {
            lista = ordenador.ordenarPorNombre(lista);
        } else if (orden.equals("Tipo")) {
            lista = ordenador.ordenarPorTipo(lista);
        }

        llenarTabla(lista);
    }

    // Quitar filtros y mostrar todos los empleados
    private void mostrarTodos() {

        txtBuscar.setText("");
        cmbFiltroTipo.setSelectedIndex(0);
        cmbOrden.setSelectedIndex(0);
        actualizar();
    }

    // Llenar la tabla con una lista de empleados
    private void llenarTabla(List<Empleado> lista) {

        modeloTabla.setRowCount(0);

        for (Empleado empleado : lista) {

            Object[] fila = {
                empleado.getCodigo(),
                empleado.getNombreCompleto(),
                empleado.getDocumento(),
                empleado.getCargo(),
                empleado.getTipo(),
                empleado.getFechaIngreso().toString(),
                obtenerDatosPago(empleado),
                "$" + String.format("%.2f", empleado.calcularSalario()),
                empleado.isActivo() ? "Activo" : "Inactivo"
            };

            modeloTabla.addRow(fila);
        }
    }

    // Datos de pago segun el tipo
    private String obtenerDatosPago(Empleado empleado) {

        if (empleado instanceof EmpleadoAdministrativo) {

            EmpleadoAdministrativo administrativo =
                    (EmpleadoAdministrativo) empleado;

            return "Salario: $" + String.format("%.2f",
                    administrativo.getSalarioMensual());

        } else if (empleado instanceof EmpleadoPorHora) {

            EmpleadoPorHora empleadoHora = (EmpleadoPorHora) empleado;

            return "$" + String.format("%.2f", empleadoHora.getPrecioPorHora())
                    + " x " + empleadoHora.getHorasTrabajadas() + " horas";

        } else if (empleado instanceof EmpleadoPorComision) {

            EmpleadoPorComision empleadoComision =
                    (EmpleadoPorComision) empleado;

            return empleadoComision.getPorcentajeComision() + "% de $"
                    + String.format("%.2f", empleadoComision.getVentasTotales());
        }

        return "";
    }

    // Buscar empleados por codigo, documento o nombre
    private void buscarEmpleado() {

        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            mostrarAviso("Ingrese el texto que desea buscar.");
            txtBuscar.requestFocus();
            return;
        }

        List<Empleado> resultado = new ArrayList<>();
        String opcion = (String) cmbBuscarPor.getSelectedItem();

        if (opcion.equals("Codigo")) {

            Empleado empleado = gestorEmpleados.buscarPorCodigo(texto);

            if (empleado != null) {
                resultado.add(empleado);
            }

        } else if (opcion.equals("Documento")) {

            Empleado empleado = gestorEmpleados.buscarPorDocumento(texto);

            if (empleado != null) {
                resultado.add(empleado);
            }

        } else {
            resultado = gestorEmpleados.buscarPorNombre(texto);
        }

        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron empleados con ese criterio.",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }

        llenarTabla(resultado);
    }

    // Registrar un empleado nuevo
    private void registrarEmpleado() {

        if (!validarFormulario()) {
            return;
        }

        String codigo = txtCodigo.getText().trim();
        String documento = txtDocumento.getText().trim();

        // Validar duplicados
        if (gestorEmpleados.buscarPorCodigo(codigo) != null) {
            mostrarAviso("Ya existe un empleado con el codigo " + codigo + ".");
            txtCodigo.requestFocus();
            return;
        }

        if (gestorEmpleados.buscarPorDocumento(documento) != null) {
            mostrarAviso("Ya existe un empleado con el documento " + documento + ".");
            txtDocumento.requestFocus();
            return;
        }

        Empleado nuevo = crearEmpleado();

        try {
            if (gestorEmpleados.registrarEmpleado(nuevo)) {
                mostrarMensaje("Empleado registrado correctamente.");
                limpiarFormulario();
                actualizar();
            } else {
                mostrarAviso("No se pudo registrar. Revise los datos ingresados.");
            }
        } catch (Exception e) {
            mostrarErrorBaseDatos(e);
        }
    }

    // Crear el objeto segun el tipo seleccionado
    private Empleado crearEmpleado() {

        String codigo = txtCodigo.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String documento = txtDocumento.getText().trim();
        String cargo = txtCargo.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        if (tipo.equals("Administrativo")) {
            return new EmpleadoAdministrativo(codigo, nombres, apellidos,
                    documento, cargo, fechaIngreso, dato1);

        } else if (tipo.equals("Por Hora")) {
            return new EmpleadoPorHora(codigo, nombres, apellidos,
                    documento, cargo, fechaIngreso, dato1, (int) dato2);

        } else {
            return new EmpleadoPorComision(codigo, nombres, apellidos,
                    documento, cargo, fechaIngreso, dato1, dato2);
        }
    }

    // Modificar el empleado seleccionado
    private void modificarEmpleado() {

        String codigo = txtCodigo.getText().trim();
        Empleado empleado = gestorEmpleados.buscarPorCodigo(codigo);

        if (empleado == null) {
            mostrarAviso("El empleado no existe.");
            return;
        }

        if (!empleado.isActivo()) {
            mostrarAviso("No se puede modificar un empleado inactivo.");
            return;
        }

        if (!validarFormulario()) {
            return;
        }

        String documento = txtDocumento.getText().trim();

        if (!ValidadorEmpleados.validarDocumentoModificado(
                documento, codigo, gestorEmpleados)) {
            mostrarAviso("El documento ya pertenece a otro empleado.");
            txtDocumento.requestFocus();
            return;
        }

        try {
            boolean modificado = gestorEmpleados.modificarEmpleado(codigo,
                    txtNombres.getText().trim(),
                    txtApellidos.getText().trim(),
                    documento,
                    txtCargo.getText().trim(),
                    fechaIngreso);

            // Modificar los datos propios de cada tipo
            if (modificado) {

                if (empleado instanceof EmpleadoAdministrativo) {
                    modificado = gestorEmpleados.modificarSalarioAdministrativo(
                            codigo, dato1);

                } else if (empleado instanceof EmpleadoPorHora) {
                    modificado = gestorEmpleados.modificarEmpleadoPorHora(
                            codigo, dato1, (int) dato2);

                } else if (empleado instanceof EmpleadoPorComision) {
                    modificado = gestorEmpleados.modificarEmpleadoPorComision(
                            codigo, dato1, dato2);
                }
            }

            if (modificado) {
                mostrarMensaje("Empleado modificado correctamente.");
                limpiarFormulario();
                actualizar();
            } else {
                mostrarAviso("No se pudo modificar. Revise los datos ingresados.");
            }

        } catch (Exception e) {
            mostrarErrorBaseDatos(e);
        }
    }

    // Inactivar el empleado seleccionado
    private void inactivarEmpleado() {

        String codigo = txtCodigo.getText().trim();
        Empleado empleado = gestorEmpleados.buscarPorCodigo(codigo);

        if (empleado == null) {
            mostrarAviso("El empleado no existe.");
            return;
        }

        if (!empleado.isActivo()) {
            mostrarAviso("El empleado ya esta inactivo.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "Desea inactivar a " + empleado.getNombreCompleto() + "?\n"
                + "Sus datos y su historial se conservaran.",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            if (gestorEmpleados.inactivarEmpleado(codigo)) {
                mostrarMensaje("Empleado inactivado correctamente.");
                limpiarFormulario();
                actualizar();
            } else {
                mostrarAviso("No se pudo inactivar el empleado.");
            }
        } catch (Exception e) {
            mostrarErrorBaseDatos(e);
        }
    }

    // Cargar en el formulario el empleado seleccionado en la tabla
    private void cargarEmpleadoSeleccionado() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(fila, 0);
        Empleado empleado = gestorEmpleados.buscarPorCodigo(codigo);

        if (empleado == null) {
            return;
        }

        txtCodigo.setText(empleado.getCodigo());
        txtNombres.setText(empleado.getNombres());
        txtApellidos.setText(empleado.getApellidos());
        txtDocumento.setText(empleado.getDocumento());
        txtCargo.setText(empleado.getCargo());
        txtFecha.setText(empleado.getFechaIngreso().toString());
        cmbTipo.setSelectedItem(empleado.getTipo());

        if (empleado instanceof EmpleadoAdministrativo) {

            EmpleadoAdministrativo administrativo =
                    (EmpleadoAdministrativo) empleado;
            txtDato1.setText(String.valueOf(administrativo.getSalarioMensual()));

        } else if (empleado instanceof EmpleadoPorHora) {

            EmpleadoPorHora empleadoHora = (EmpleadoPorHora) empleado;
            txtDato1.setText(String.valueOf(empleadoHora.getPrecioPorHora()));
            txtDato2.setText(String.valueOf(empleadoHora.getHorasTrabajadas()));

        } else if (empleado instanceof EmpleadoPorComision) {

            EmpleadoPorComision empleadoComision =
                    (EmpleadoPorComision) empleado;
            txtDato1.setText(String.valueOf(
                    empleadoComision.getPorcentajeComision()));
            txtDato2.setText(String.valueOf(
                    empleadoComision.getVentasTotales()));
        }

        txtCodigo.setEditable(false);
        cmbTipo.setEnabled(false);

        btnRegistrar.setEnabled(false);
        btnModificar.setEnabled(empleado.isActivo());
        btnInactivar.setEnabled(empleado.isActivo());
    }

    // Mostrar todos los datos del empleado seleccionado
    private void mostrarDetalle() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            return;
        }

        Empleado empleado = gestorEmpleados.buscarPorCodigo(
                (String) modeloTabla.getValueAt(fila, 0));

        if (empleado == null) {
            return;
        }

        String detalle = "Codigo: " + empleado.getCodigo()
                + "\nNombre: " + empleado.getNombreCompleto()
                + "\nDocumento: " + empleado.getDocumento()
                + "\nCargo: " + empleado.getCargo()
                + "\nFecha de ingreso: " + empleado.getFechaIngreso()
                + "\nTipo: " + empleado.getTipo()
                + "\nDatos de pago: " + obtenerDatosPago(empleado)
                + "\nRemuneracion: $" + String.format("%.2f", empleado.calcularSalario())
                + "\nEstado: " + (empleado.isActivo() ? "Activo" : "Inactivo");

        JOptionPane.showMessageDialog(this, detalle,
                "Detalle del empleado", JOptionPane.INFORMATION_MESSAGE);
    }

    // Validar los datos del formulario
    private boolean validarFormulario() {

        // Campos obligatorios
        if (campoVacio(txtCodigo, "Codigo")
                || campoVacio(txtNombres, "Nombres")
                || campoVacio(txtApellidos, "Apellidos")
                || campoVacio(txtDocumento, "Documento")
                || campoVacio(txtCargo, "Cargo")
                || campoVacio(txtFecha, "Fecha de ingreso")
                || campoVacio(txtDato1, lblDato1.getText())) {
            return false;
        }

        if (txtDato2.isVisible() && campoVacio(txtDato2, lblDato2.getText())) {
            return false;
        }

        // Formato del documento
        if (!txtDocumento.getText().trim().matches("\\d{8}-\\d")) {
            mostrarAviso("El documento debe tener el formato 00000000-0.");
            txtDocumento.requestFocus();
            return false;
        }

        // Fecha de ingreso
        try {
            fechaIngreso = LocalDate.parse(txtFecha.getText().trim());
        } catch (DateTimeParseException e) {
            mostrarAviso("La fecha debe tener el formato AAAA-MM-DD. Ejemplo: 2024-03-15");
            txtFecha.requestFocus();
            return false;
        }

        if (!ValidadorEmpleados.validarFecha(fechaIngreso)) {
            mostrarAviso("La fecha de ingreso no puede ser posterior a hoy.");
            txtFecha.requestFocus();
            return false;
        }

        // Datos numericos segun el tipo
        String tipo = (String) cmbTipo.getSelectedItem();

        dato1 = leerNumero(txtDato1, lblDato1.getText());

        if (dato1 < 0) {
            return false;
        }

        if (tipo.equals("Por Hora")) {

            dato2 = leerNumero(txtDato2, lblDato2.getText());

            if (dato2 < 0) {
                return false;
            }

            if (dato2 != Math.floor(dato2)) {
                mostrarAviso("Las horas trabajadas deben ser un numero entero.");
                txtDato2.requestFocus();
                return false;
            }

        } else if (tipo.equals("Por Comision")) {

            if (!ValidadorEmpleados.validarPorcentajeComision(dato1)) {
                mostrarAviso("El porcentaje de comision debe estar entre 0 y 100.");
                txtDato1.requestFocus();
                return false;
            }

            dato2 = leerNumero(txtDato2, lblDato2.getText());

            if (dato2 < 0) {
                return false;
            }
        }

        return true;
    }

    // Validar campo vacio
    private boolean campoVacio(JTextField campo, String nombre) {

        if (campo.getText().trim().isEmpty()) {
            mostrarAviso("El campo " + nombre.replace(":", "") + " es obligatorio.");
            campo.requestFocus();
            return true;
        }

        return false;
    }

    // Leer un numero
    private double leerNumero(JTextField campo, String nombre) {

        String nombreCampo = nombre.replace(":", "");

        try {
            double numero = Double.parseDouble(
                    campo.getText().trim().replace(',', '.'));

            if (Double.isNaN(numero) || Double.isInfinite(numero)) {
                mostrarAviso("El campo " + nombreCampo + " debe ser un numero valido.");
                campo.requestFocus();
                return -1;
            }

            if (numero < 0) {
                mostrarAviso("El campo " + nombreCampo + " no puede ser negativo.");
                campo.requestFocus();
                return -1;
            }

            return numero;

        } catch (NumberFormatException e) {
            mostrarAviso("El campo " + nombreCampo + " debe ser numerico.");
            campo.requestFocus();
            return -1;
        }
    }

    // Limpiar formulario
    private void limpiarFormulario() {

        txtCodigo.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDocumento.setText("");
        txtCargo.setText("");
        txtFecha.setText("");
        txtDato1.setText("");
        txtDato2.setText("");

        txtCodigo.setEditable(true);
        cmbTipo.setEnabled(true);
        cmbTipo.setSelectedIndex(0);

        btnRegistrar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnInactivar.setEnabled(false);

        tabla.clearSelection();
        txtCodigo.requestFocus();
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
                "Error al guardar en la base de datos.\n" + e.getMessage(),
                "Error de MongoDB", JOptionPane.ERROR_MESSAGE);
    }
}
