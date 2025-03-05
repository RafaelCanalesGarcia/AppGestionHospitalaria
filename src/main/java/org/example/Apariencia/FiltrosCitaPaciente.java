package org.example.Apariencia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.example.ClasesNativas.Enums.Especialidad;
import org.example.ClasesNativas.TrabajadorSeleccionadoListener;
import org.example.Database.DatabaseConnection;

public class FiltrosCitaPaciente extends JPanel {
    private JComboBox<Especialidad> especialidadComboBox;
    private JButton agendarCitaButton;
    private JComboBox<String> trabajadorComboBox;
    private Connection conn = DatabaseConnection.getConnection();
    private List<TrabajadorSeleccionadoListener> listeners = new ArrayList<TrabajadorSeleccionadoListener>();

    public FiltrosCitaPaciente() {
        setLayout(new GridBagLayout()); // Usamos GridBagLayout para mejor organización
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // JLabel "Nombre" en la parte superior
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(nombreLabel, gbc);

        // JLabel "Especialidad"
        JLabel especialidadLabel = new JLabel("Especialidad:");
        especialidadLabel.setPreferredSize(new Dimension(200, 30));
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        add(especialidadLabel, gbc);

        // JComboBox con las especialidades del enum
        especialidadComboBox = new JComboBox<>(Especialidad.values());
        especialidadComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(especialidadComboBox, gbc);

        // JLabel "Trabajador"
        JLabel trabajadorLabel = new JLabel("Seleccionar Trabajador:");
        trabajadorLabel.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(trabajadorLabel, gbc);

        // JComboBox para mostrar los trabajadores según la especialidad
        trabajadorComboBox = new JComboBox<>();
        trabajadorComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(trabajadorComboBox, gbc);

        // Botón "Agendar Cita" en la parte inferior derecha
        agendarCitaButton = new JButton("Agendar cita");
        agendarCitaButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Alineación en la parte inferior derecha
        add(agendarCitaButton, gbc);
        especialidadComboBox.addActionListener(e -> actualizarTrabajadores());

        // Cargar trabajadores de la especialidad inicial
        actualizarTrabajadores();
        // Agregar listener para el cambio de trabajador
        trabajadorComboBox.addActionListener(e -> notificarCambioTrabajador());

    }
    public void addAgendarCitaListener(ActionListener listener) {
        agendarCitaButton.addActionListener(listener);
    }

    private void actualizarTrabajadores() {
        trabajadorComboBox.removeAllItems(); // Limpiar lista anterior
        Especialidad especialidadSeleccionada = (Especialidad) especialidadComboBox.getSelectedItem();

        if (especialidadSeleccionada == null) return;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT Nombre FROM Trabajador WHERE Especialidad = ?")) {

            stmt.setString(1, especialidadSeleccionada.name()); // Filtrar por especialidad
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> trabajadores = new ArrayList<>();
            while (rs.next()) {
                trabajadores.add(rs.getString("Nombre"));
            }

            if (trabajadores.isEmpty()) {
                trabajadorComboBox.addItem("No hay trabajadores disponibles");
            } else {
                for (String trabajador : trabajadores) {
                    trabajadorComboBox.addItem(trabajador);
                }
            }
            trabajadorComboBox.revalidate();
            trabajadorComboBox.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Especialidad getEspecialidadSeleccionada() {
        return (Especialidad) especialidadComboBox.getSelectedItem();
    }

    public String getTrabajadorSeleccionado() {
        return (String) trabajadorComboBox.getSelectedItem();
    }

    public JButton getAgendarCitaButton() {
        return agendarCitaButton;
    }
    public void addTrabajadorSeleccionadoListener(TrabajadorSeleccionadoListener listener) {
        listeners.add(listener);
    }

    // Notificar a los listeners cuando se seleccione un trabajador
    private void notificarCambioTrabajador() {
        String trabajadorSeleccionado = getTrabajadorSeleccionado();
        for (TrabajadorSeleccionadoListener listener : listeners) {
            listener.onTrabajadorSeleccionado(trabajadorSeleccionado);
        }
    }
}
