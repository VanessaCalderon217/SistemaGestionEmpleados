/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.sistemaempleados.GestorCalculos;
import com.sistemaempleados.GestorEmpleados;
import com.sistemaempleados.modelo.Empleado;
import com.sistemaempleados.modelo.EmpleadoAdministrativo;
import com.sistemaempleados.modelo.EmpleadoPorComision;
import com.sistemaempleados.modelo.EmpleadoPorHora;
import com.sistemaempleados.modelo.RegistroCalculo;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author BryanAlfaro
 */
public class PanelCalculos extends JPanel {

    private static final String[] MESES = {"Enero", "Febrero", "Marzo",
        "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
        "Octubre", "Noviembre", "Diciembre"};

    private GestorEmpleados gestorEmpleados;
    private GestorCalculos gestorCalculos;

    // Empleados activos
    private List<Empleado> empleadosActivos;

    private JComboBox<String> cmbEmpleado;
    private JComboBox<String> cmbMes;
    private JComboBox<String> cmbAnio;
    private JTextArea txtResultado;

    // Historial
    private JComboBox<String> cmbFiltro;
    private JTextField txtFiltro;
    private JTable tablaHistorial;
    private DefaultTableModel modeloHistorial;

    // Constructor
    public PanelCalculos(GestorEmpleados gestorEmpleados,
            GestorCalculos gestorCalculos) {

        this.gestorEmpleados = gestorEmpleados;
        this.gestorCalculos = gestorCalculos;
        this.empleadosActivos = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearPanelCalculo(), BorderLayout.NORTH);
        add(crearPanelHistorial(), BorderLayout.CENTER);

        actualizar();
    }

    // Panel para seleccionar el empleado y calcular
    private JPanel crearPanelCalculo() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Calcular remuneracion"));

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cmbEmpleado = new JComboBox<>();
        cmbMes = new JComboBox<>(MESES);
        cmbAnio = new JComboBox<>();

        int anioActual = LocalDate.now().getYear();

        for (int anio = anioActual; anio >= anioActual - 4; anio--) {
            cmbAnio.addItem(String.valueOf(anio));
        }

        cmbMes.setSelectedIndex(LocalDate.now().getMonthValue() - 1);

        JButton btnCalcular = new JButton("Calcular y guardar");
        btnCalcular.addActionListener(e -> calcular());

        controles.add(new JLabel("Empleado:"));
        controles.add(cmbEmpleado);
        controles.add(new JLabel("  Periodo:"));
        controles.add(cmbMes);
        controles.add(cmbAnio);
        controles.add(btnCalcular);

        panel.add(controles, BorderLayout.NORTH);

        txtResultado = new JTextArea(6, 40);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);

        return panel;
    }

    // Panel con el historial de calculos
    private JPanel crearPanelHistorial() {

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Historial de calculos"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cmbFiltro = new JComboBox<>(new String[]{
            "Todos", "Por codigo de empleado", "Por periodo"});
        txtFiltro = new JTextField(15);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> filtrarHistorial());

        filtros.add(new JLabel("Consultar:"));
        filtros.add(cmbFiltro);
        filtros.add(txtFiltro);
        filtros.add(btnFiltrar);

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"Fecha", "Periodo", "Codigo", "Empleado",
            "Tipo", "Valores utilizados", "Remuneracion"};

        modeloHistorial = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.setRowHeight(22);
        panel.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);

        return panel;
    }

    // Recargar los empleados activos y el historial
    public void actualizar() {

        cmbEmpleado.removeAllItems();
        empleadosActivos = gestorEmpleados.consultarActivos();

        for (Empleado empleado : empleadosActivos) {
            cmbEmpleado.addItem(empleado.getCodigo() + " - "
                    + empleado.getNombreCompleto()
                    + " (" + empleado.getTipo() + ")");
        }

        llenarHistorial(gestorCalculos.consultarHistorial());
    }

    // Calcular la remuneracion del empleado seleccionado
    private void calcular() {

        int indice = cmbEmpleado.getSelectedIndex();

        if (indice == -1) {
            mostrarAviso("No hay empleados activos para calcular.");
            return;
        }

        Empleado empleado = empleadosActivos.get(indice);

        if (!empleado.isActivo()) {
            mostrarAviso("El empleado esta inactivo.");
            return;
        }

        String periodo = cmbMes.getSelectedItem() + " " + cmbAnio.getSelectedItem();

        // Verificar si ya existe el calculo
        if (existeCalculo(empleado.getCodigo(), periodo)) {

            int respuesta = JOptionPane.showConfirmDialog(this,
                    "Ya existe un calculo de este empleado para " + periodo
                    + ".\nDesea registrar otro?", "Calculo existente",
                    JOptionPane.YES_NO_OPTION);

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            double remuneracion = gestorCalculos.calcularRemuneracion(
                    empleado, periodo);

            if (remuneracion < 0) {
                mostrarAviso("No fue posible realizar el calculo. Verifique los datos del empleado.");
                return;
            }

            mostrarResultado(empleado, periodo, remuneracion);
            llenarHistorial(gestorCalculos.consultarHistorial());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el calculo en la base de datos.\n"
                    + e.getMessage(), "Error de MongoDB",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Revisar si el empleado ya tiene un calculo en el periodo
    private boolean existeCalculo(String codigo, String periodo) {

        List<RegistroCalculo> historial =
                gestorCalculos.consultarHistorialPorEmpleado(codigo);

        for (RegistroCalculo registro : historial) {
            if (registro.getPeriodo().equalsIgnoreCase(periodo)) {
                return true;
            }
        }

        return false;
    }

    // Mostrar resultado
    private void mostrarResultado(Empleado empleado, String periodo,
            double remuneracion) {

        String texto = "Empleado:      " + empleado.getNombreCompleto()
                + "\nTipo:          " + empleado.getTipo()
                + "\nPeriodo:       " + periodo;

        if (empleado instanceof EmpleadoAdministrativo) {

            EmpleadoAdministrativo administrativo =
                    (EmpleadoAdministrativo) empleado;

            texto += "\nFormula:       salario base mensual"
                    + "\nSalario base:  $" + String.format("%.2f",
                            administrativo.getSalarioMensual());

        } else if (empleado instanceof EmpleadoPorHora) {

            EmpleadoPorHora empleadoHora = (EmpleadoPorHora) empleado;

            texto += "\nFormula:       tarifa por hora x horas trabajadas"
                    + "\nTarifa:        $" + String.format("%.2f",
                            empleadoHora.getPrecioPorHora())
                    + "\nHoras:         " + empleadoHora.getHorasTrabajadas();

        } else if (empleado instanceof EmpleadoPorComision) {

            EmpleadoPorComision empleadoComision =
                    (EmpleadoPorComision) empleado;

            texto += "\nFormula:       ventas x porcentaje / 100"
                    + "\nVentas:        $" + String.format("%.2f",
                            empleadoComision.getVentasTotales())
                    + "\nPorcentaje:    " + empleadoComision.getPorcentajeComision() + "%";
        }

        texto += "\nREMUNERACION:  $" + String.format("%.2f", remuneracion)
                + "\n\nEl calculo se guardo en el historial.";

        txtResultado.setText(texto);
    }

    // Filtrar el historial por empleado o periodo
    private void filtrarHistorial() {

        String opcion = (String) cmbFiltro.getSelectedItem();
        String texto = txtFiltro.getText().trim();

        if (opcion.equals("Todos")) {
            llenarHistorial(gestorCalculos.consultarHistorial());
            return;
        }

        if (texto.isEmpty()) {
            mostrarAviso("Ingrese el codigo o el periodo a consultar.");
            txtFiltro.requestFocus();
            return;
        }

        List<RegistroCalculo> resultado;

        if (opcion.equals("Por codigo de empleado")) {
            resultado = gestorCalculos.consultarHistorialPorEmpleado(texto);
        } else {
            resultado = gestorCalculos.consultarHistorialPorPeriodo(texto);
        }

        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron calculos con ese criterio.",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }

        llenarHistorial(resultado);
    }

    // Llenar la tabla del historial
    private void llenarHistorial(List<RegistroCalculo> lista) {

        modeloHistorial.setRowCount(0);

        for (RegistroCalculo registro : lista) {

            Object[] fila = {
                registro.getFechaCalculo().toString(),
                registro.getPeriodo(),
                registro.getCodigoEmpleado(),
                registro.getNombreEmpleado(),
                registro.getTipoEmpleado(),
                obtenerValores(registro),
                "$" + String.format("%.2f", registro.getRemuneracion())
            };

            modeloHistorial.addRow(fila);
        }
    }

    // Valores usados en el calculo
    private String obtenerValores(RegistroCalculo registro) {

        String tipo = registro.getTipoEmpleado();

        if (tipo.equalsIgnoreCase("Administrativo")) {
            return "Salario: $" + String.format("%.2f", registro.getSalarioMensual());

        } else if (tipo.equalsIgnoreCase("Por Hora")) {
            return "$" + String.format("%.2f", registro.getPrecioPorHora())
                    + " x " + registro.getHorasTrabajadas() + " horas";

        } else {
            return registro.getPorcentajeComision() + "% de $"
                    + String.format("%.2f", registro.getVentasTotales());
        }
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,
                "Validacion", JOptionPane.WARNING_MESSAGE);
    }
}

