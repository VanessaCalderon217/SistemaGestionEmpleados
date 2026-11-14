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
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

public class PanelReportes extends JPanel {

    private GestorEmpleados gestorEmpleados;
    private GestorCalculos gestorCalculos;

    private JComboBox<String> cmbReporte;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Etiquetas del resumen
    private JLabel lblTotal;
    private JLabel lblActivos;
    private JLabel lblInactivos;
    private JLabel lblMonto;

    // Constructor
    public PanelReportes(GestorEmpleados gestorEmpleados,
            GestorCalculos gestorCalculos) {

        this.gestorEmpleados = gestorEmpleados;
        this.gestorCalculos = gestorCalculos;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        crearComponentes();
        generarReporte();
    }

    // Crear los componentes del panel
    private void crearComponentes() {

        // Seleccion del reporte
        JPanel opciones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cmbReporte = new JComboBox<>(new String[]{
            "Listado general de empleados",
            "Empleados administrativos",
            "Empleados por hora",
            "Empleados por comision",
            "Remuneraciones de empleados activos",
            "Historial de calculos"});

        JButton btnGenerar = new JButton("Generar reporte");
        btnGenerar.addActionListener(e -> generarReporte());

        opciones.add(new JLabel("Reporte:"));
        opciones.add(cmbReporte);
        opciones.add(btnGenerar);

        add(opciones, BorderLayout.NORTH);

        // Tabla del reporte
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(22);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Resumen
        JPanel resumen = new JPanel(new GridLayout(2, 2, 10, 5));
        resumen.setBorder(BorderFactory.createTitledBorder("Resumen"));

        lblTotal = new JLabel();
        lblActivos = new JLabel();
        lblInactivos = new JLabel();
        lblMonto = new JLabel();
        lblMonto.setFont(new Font("Arial", Font.BOLD, 13));

        resumen.add(lblTotal);
        resumen.add(lblActivos);
        resumen.add(lblInactivos);
        resumen.add(lblMonto);

        add(resumen, BorderLayout.SOUTH);
    }

    public void actualizar() {
        generarReporte();
    }

    // Generar el reporte seleccionado
    private void generarReporte() {

        int opcion = cmbReporte.getSelectedIndex();

        switch (opcion) {

            case 0:
                reporteEmpleados(gestorEmpleados.consultarEmpleados());
                break;

            case 1:
                reporteEmpleados(gestorEmpleados.consultarPorTipo("Administrativo"));
                break;

            case 2:
                reporteEmpleados(gestorEmpleados.consultarPorTipo("Por Hora"));
                break;

            case 3:
                reporteEmpleados(gestorEmpleados.consultarPorTipo("Por Comision"));
                break;

            case 4:
                reporteEmpleados(gestorEmpleados.consultarActivos());
                break;

            default:
                reporteHistorial();
                break;
        }
    }

    // Reporte con una lista de empleados
    private void reporteEmpleados(List<Empleado> lista) {

        String[] columnas = {"Codigo", "Nombre", "Tipo", "Cargo",
            "Salario base / Tarifa", "Datos del periodo",
            "Remuneracion calculada", "Estado"};

        modeloTabla.setColumnIdentifiers(columnas);
        modeloTabla.setRowCount(0);

        int activos = 0;
        int inactivos = 0;
        double totalRemuneraciones = 0;

        for (Empleado empleado : lista) {

            String base = "";
            String datos = "";

            if (empleado instanceof EmpleadoAdministrativo) {

                EmpleadoAdministrativo administrativo =
                        (EmpleadoAdministrativo) empleado;

                base = "$" + String.format("%.2f",
                        administrativo.getSalarioMensual()) + " mensual";
                datos = "-";

            } else if (empleado instanceof EmpleadoPorHora) {

                EmpleadoPorHora empleadoHora = (EmpleadoPorHora) empleado;

                base = "$" + String.format("%.2f",
                        empleadoHora.getPrecioPorHora()) + " por hora";
                datos = empleadoHora.getHorasTrabajadas() + " horas";

            } else if (empleado instanceof EmpleadoPorComision) {

                EmpleadoPorComision empleadoComision =
                        (EmpleadoPorComision) empleado;

                base = empleadoComision.getPorcentajeComision() + "% comision";
                datos = "Ventas: $" + String.format("%.2f",
                        empleadoComision.getVentasTotales());
            }

            Object[] fila = {
                empleado.getCodigo(),
                empleado.getNombreCompleto(),
                empleado.getTipo(),
                empleado.getCargo(),
                base,
                datos,
                "$" + String.format("%.2f", empleado.calcularSalario()),
                empleado.isActivo() ? "Activo" : "Inactivo"
            };

            modeloTabla.addRow(fila);

            if (empleado.isActivo()) {
                activos++;
                totalRemuneraciones += empleado.calcularSalario();
            } else {
                inactivos++;
            }
        }

        lblTotal.setText("Empleados: " + lista.size());
        lblActivos.setText("Activos: " + activos);
        lblInactivos.setText("Inactivos: " + inactivos);
        lblMonto.setText("Total remuneraciones (activos): $"
                + String.format("%.2f", totalRemuneraciones));

        avisarSinResultados(lista.isEmpty());
    }

    // Reporte del historial
    private void reporteHistorial() {

        List<RegistroCalculo> historial = gestorCalculos.consultarHistorial();

        String[] columnas = {"Fecha", "Periodo", "Codigo", "Empleado",
            "Tipo", "Remuneracion"};

        modeloTabla.setColumnIdentifiers(columnas);
        modeloTabla.setRowCount(0);

        double total = 0;

        for (RegistroCalculo registro : historial) {

            Object[] fila = {
                registro.getFechaCalculo().toString(),
                registro.getPeriodo(),
                registro.getCodigoEmpleado(),
                registro.getNombreEmpleado(),
                registro.getTipoEmpleado(),
                "$" + String.format("%.2f", registro.getRemuneracion())
            };

            modeloTabla.addRow(fila);
            total += registro.getRemuneracion();
        }

        lblTotal.setText("Calculos registrados: " + historial.size());
        lblActivos.setText("");
        lblInactivos.setText("");
        lblMonto.setText("Total calculado: $" + String.format("%.2f", total));

        avisarSinResultados(historial.isEmpty());
    }

    // Avisar si no hay datos
    private void avisarSinResultados(boolean vacio) {

        if (vacio && isShowing()) {
            JOptionPane.showMessageDialog(this,
                    "El reporte no tiene registros para mostrar.",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
