package org.example.Apariencia;

import org.example.Database.DatabaseManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListaHistorialClinico extends JPanel {
    private JTable tablaHistorial;
    private DatabaseManagement dbm;
    private String dni;
    private HistorialSelectionListener listener; // Listener para notificar a Perfil


    public ListaHistorialClinico(String dni,HistorialSelectionListener listener) {
        this.dni = dni;
        this.dbm = new DatabaseManagement();
        this.listener = listener;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Panel principal de contenido
        JPanel contenidoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcContenido = new GridBagConstraints();
        gbcContenido.fill = GridBagConstraints.HORIZONTAL;
        gbcContenido.weightx = 1;
        gbcContenido.insets = new Insets(10, 10, 10, 10);

        JLabel tituloLabel = new JLabel("HISTORIAL CLÍNICO", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbcContenido.gridy = 0;
        contenidoPanel.add(tituloLabel, gbcContenido);

        // Modelo de tabla con celdas no editables
        String[] columnNames = {"Doctor/a", "Diagnóstico"};
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que las celdas sean editables
            }
        };

        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Selección de solo una fila
        tablaHistorial.setRowSelectionAllowed(true); // Permite seleccionar toda la fila

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        gbcContenido.gridy = 1;
        gbcContenido.gridheight = 2;
        gbcContenido.fill = GridBagConstraints.BOTH;
        gbcContenido.weighty = 1;
        gbcContenido.weightx = 0.5;
        contenidoPanel.add(scrollPane, gbcContenido);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        add(contenidoPanel, gbc);

        // Cargar el historial clínico
        cargarHistorialClinico();

        // Evento para seleccionar la fila con doble clic
        tablaHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic
                    int filaSeleccionada = tablaHistorial.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String trabajador = tablaHistorial.getValueAt(filaSeleccionada, 0).toString();
                        String diagnostico = tablaHistorial.getValueAt(filaSeleccionada, 1).toString();

                        // Obtener DNI del trabajador
                        String dniTrabajador = dbm.obtenerDniPorNombreTrabajador(trabajador);

                        // Notificar al listener (Perfil) sobre la selección
                        if (listener != null) {
                            listener.onHistorialSeleccionado(dni, dniTrabajador, diagnostico);
                        }
                    }
                }
            }
        });


    }

    private void cargarHistorialClinico() {
        // Obtener historial clínico de la base de datos
        List<String[]> historial = dbm.obtenerHistorialClinicoPorDni(dni);
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaHistorial.getModel();
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos

        for (String[] fila : historial) {
            modeloTabla.addRow(fila);
        }
    }
}
