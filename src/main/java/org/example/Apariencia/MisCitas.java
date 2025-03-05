package org.example.Apariencia;

import org.example.Database.DatabaseManagement;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

public class MisCitas extends JPanel {
    private JTable tablaCitas;
    private JRadioButton todasButton, filtrarButton;
    private JComboBox<String> ordenarComboBox,filtroComboBox;
    private ButtonGroup filtroGroup;
    private JButton buscarButton, anularCita;
    private DatabaseManagement dbm;
    private String dni;
    private DatosCita datosCita;
    private JDatePickerImpl datePickerFiltro;
    private JDatePanelImpl datePanel;

    public MisCitas(String dni) {
        this.dni = dni;
        this.dbm = new DatabaseManagement();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Panel principal de contenido (antes estaba en el JPanel principal)
        JPanel contenidoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcContenido = new GridBagConstraints();
        gbcContenido.fill = GridBagConstraints.HORIZONTAL;
        gbcContenido.weightx = 1;
        gbcContenido.insets = new Insets(10, 10, 10, 10);

        datosCita = new DatosCita(dni);
        // Obtener nombre del paciente
        String nombrePaciente = dbm.obtenerNombrePorDniPaciente(dni);
        String nombreTrabajador = dbm.obtenerNombrePorDniTrabajador(dni);
        if (dbm.obtenerTipoUsuario(dni).equalsIgnoreCase("Paciente")) {
            JLabel nombreLabel = new JLabel("Citas de " + nombrePaciente, SwingConstants.CENTER);
            nombreLabel.setFont(new Font("Arial", Font.BOLD, 30));
            gbcContenido.gridy = 0;
            contenidoPanel.add(nombreLabel, gbcContenido);
        } else {
            JLabel nombreLabel = new JLabel("Citas de " + nombreTrabajador, SwingConstants.CENTER);
            nombreLabel.setFont(new Font("Arial", Font.BOLD, 30));
            gbcContenido.gridy = 0;
            contenidoPanel.add(nombreLabel, gbcContenido);
        }


        // Panel de filtros
        JPanel filtroPanel = new JPanel();
        filtroPanel.setLayout(new FlowLayout());
        todasButton = new JRadioButton("Todas", true);
        filtrarButton = new JRadioButton("Filtrar");
        filtroGroup = new ButtonGroup();
        filtroGroup.add(todasButton);
        filtroGroup.add(filtrarButton);
        filtroPanel.add(todasButton);
        filtroPanel.add(filtrarButton);

        ordenarComboBox = new JComboBox<>(new String[]{"---", "Día", "Especialidad", "Dr/a"});
        ordenarComboBox.setEnabled(false);
        filtroPanel.add(ordenarComboBox);

        UtilDateModel dateModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        datePanel = new JDatePanelImpl(dateModel, p);
        datePickerFiltro = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datePickerFiltro.setEnabled(false);
        datePickerFiltro.setVisible(false); // Se mostrará solo si se elige "Día"
        filtroPanel.add(datePickerFiltro);

        filtroComboBox = new JComboBox<>();
        filtroComboBox.setEnabled(false);
        filtroComboBox.setVisible(false); // Se mostrará solo si se elige "Dr/a" o "Especialidad"
        filtroPanel.add(filtroComboBox);


        buscarButton = new JButton("Buscar");
        filtroPanel.add(buscarButton);

        gbcContenido.gridy = 1;
        contenidoPanel.add(filtroPanel, gbcContenido);

        // Tabla de citas con encabezado
        String[] columnNames = {"Dr/a", "Hora", "Día", "Especialidad"};
        tablaCitas = new JTable(new Object[][]{}, columnNames);
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        gbcContenido.gridy = 2;
        gbcContenido.gridheight = 2;
        gbcContenido.fill = GridBagConstraints.BOTH;
        gbcContenido.weighty = 1;
        gbcContenido.weightx = 0.5;
        contenidoPanel.add(scrollPane, gbcContenido);

        // Botón Ver resultado de cita (pequeño)

        anularCita = new JButton("Anular cita");
        anularCita.setEnabled(false);
        gbcContenido.gridy = 4;
        gbcContenido.gridheight = 1;
        gbcContenido.weighty = 0;
        gbcContenido.fill = GridBagConstraints.NONE;
        gbcContenido.anchor = GridBagConstraints.CENTER;
        contenidoPanel.add(anularCita, gbcContenido);

        gbc.gridx = 0;
        // Agregar contenidoPanel en y=0
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        add(contenidoPanel, gbc);

        // Agregar la nueva instancia de DatosCita en y=1
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 2;  // Igual que la tabla
        gbc.weighty = 1;  // Permitir que crezca proporcionalmente
        gbc.fill = GridBagConstraints.BOTH;  // Permitir que se expanda en ambas direcciones
        contenidoPanel.add(datosCita, gbc);

        // Listeners
        todasButton.addActionListener(e -> {
            ordenarComboBox.setEnabled(false);
            filtroComboBox.setVisible(false);
            datePickerFiltro.setVisible(false);
            ordenarComboBox.setSelectedIndex(0);

        });

        filtrarButton.addActionListener(e -> ordenarComboBox.setEnabled(true));

        ordenarComboBox.addActionListener(e -> {
            String criterio = (String) ordenarComboBox.getSelectedItem();
            if (criterio.equals("Día")) {
                datePickerFiltro.setEnabled(true);
                datePickerFiltro.setVisible(true);
                filtroComboBox.setEnabled(false);
                filtroComboBox.setVisible(false);
            } else if (criterio.equals("Dr/a")) {
                datePickerFiltro.setEnabled(false);
                datePickerFiltro.setVisible(false);
                filtroComboBox.setVisible(true);
                filtroComboBox.setEnabled(true);
                cargarDoctores(filtroComboBox); // método que llena el JComboBox con los doctores del paciente
            } else if (criterio.equals("Especialidad")) {
                datePickerFiltro.setVisible(false);
                datePickerFiltro.setEnabled(false);
                filtroComboBox.setEnabled(true);
                filtroComboBox.setVisible(true);
                cargarEspecialidades(filtroComboBox); // método que llena el JComboBox con las especialidades reales del paciente
            } else {
                datePickerFiltro.setEnabled(false);
                filtroComboBox.setVisible(false);
            }
        });

        buscarButton.addActionListener(e -> {
            if (dbm.obtenerTipoUsuario(dni).equalsIgnoreCase("Paciente")) {
                if (todasButton.isSelected()) {
                    cargarCitasPaciente();
                } else {
                    filtrarCitasPaciente();
                }
            } else {
                if (todasButton.isSelected()) {
                    cargarCitasTrabajador();
                } else {
                    filtrarCitasTrabajador();
                }
            }
        });

        tablaCitas.getSelectionModel().addListSelectionListener(event -> {
            if (dbm.obtenerTipoUsuario(dni).equalsIgnoreCase("Trabajador")) {
                datosCita.habilitarComentario(true);
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tablaCitas.getSelectedRow();
                    if (selectedRow != -1) {
                        String paciente = tablaCitas.getValueAt(selectedRow, 0).toString();
                        String horaStr = tablaCitas.getValueAt(selectedRow, 1).toString();
                        String fecha = tablaCitas.getValueAt(selectedRow, 2).toString();

                        LocalDateTime fechaHoraCita = LocalDateTime.parse(fecha + "T" + horaStr); // Formato correcto para LocalDateTime
                        anularCita.setEnabled(esFechaProximaTrabajador(fechaHoraCita));

                        if (!horaStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
                            horaStr += ":00"; // Agregar segundos si no están presentes
                        } // Agregar ":00" para que sea compatible con SQL TIME

                        Time hora = Time.valueOf(horaStr);
                        String dniPaciente = dbm.obtenerDniPorNombrePaciente(paciente);
                        int codigoCita = dbm.obtenerCodigoCitaTrabajador(dniPaciente, hora, fecha);

                        if (codigoCita != -1) {
                            // Actualizar el comentario en la instancia existente
                            datosCita.setCodigoCitaActual(codigoCita);
                            datosCita.muestraComentario(codigoCita);
                        }
                    }
                }
            } else {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tablaCitas.getSelectedRow();
                    if (selectedRow != -1) {
                        String doctor = tablaCitas.getValueAt(selectedRow, 0).toString();
                        String horaStr = tablaCitas.getValueAt(selectedRow, 1).toString();
                        String fecha = tablaCitas.getValueAt(selectedRow, 2).toString();

                        LocalDate fechaCita = LocalDate.parse(fecha);
                        anularCita.setEnabled(esFechaProximaPaciente(fechaCita));

                        if (!horaStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
                            horaStr += ":00"; // Agregar segundos si no están presentes
                        } // Agregar ":00" para que sea compatible con SQL TIME


                        Time hora = Time.valueOf(horaStr);
                        String dniDoctor = dbm.obtenerDniPorNombreTrabajador(doctor);
                        int codigoCita = dbm.obtenerCodigoCitaPaciente(dniDoctor, hora, fecha);

                        if (codigoCita != -1) {
                            // Actualizar el comentario en la instancia existente
                            datosCita.muestraComentario(codigoCita);
                        }
                    }
                }
            }
        });


        anularCita.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tablaCitas.getSelectedRow();
                if (dbm.obtenerTipoUsuario(dni).equalsIgnoreCase("Paciente")) {
                    if (selectedRow != -1) {
                        // Extraer valores de la fila seleccionada
                        String doctor = tablaCitas.getValueAt(selectedRow, 0).toString(); // Nombre del doctor
                        String horaStr = tablaCitas.getValueAt(selectedRow, 1).toString(); // Hora en formato "HH:mm"
                        String fechaStr = tablaCitas.getValueAt(selectedRow, 2).toString(); // Fecha en formato "YYYY-MM-DD"

                        // Convertir valores a los tipos adecuados
                        if (!horaStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
                            horaStr += ":00"; // Agregar segundos si no están presentes
                        } // Agregar ":00" para que sea compatible con SQL TIME
                        Time hora = Time.valueOf(horaStr); // Convertir a formato SQL TIME
                        Date fecha = Date.valueOf(fechaStr); // Convertir la fecha a formato SQL DATE

                        // Obtener el DNI del trabajador a partir de su nombre
                        String dniTrabajador = dbm.obtenerDniPorNombreTrabajador(doctor);
                        dbm.anularCitaPaciente(dniTrabajador, hora, fecha);
                        cargarCitasPaciente();
                        datosCita.setTextIntoComentarioTextArea("");
                    }
                } else {
                    String paciente = tablaCitas.getValueAt(selectedRow, 0).toString(); // Nombre del doctor
                    String horaStr = tablaCitas.getValueAt(selectedRow, 1).toString(); // Hora en formato "HH:mm"
                    String fechaStr = tablaCitas.getValueAt(selectedRow, 2).toString(); // Fecha en formato "YYYY-MM-DD"
                    // Convertir valores a los tipos adecuados
                    if (!horaStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
                        horaStr += ":00"; // Agregar segundos si no están presentes
                    } // Agregar ":00" para que sea compatible con SQL TIME
                    Time hora = Time.valueOf(horaStr); // Convertir a formato SQL TIME
                    Date fecha = Date.valueOf(fechaStr);

                    String dniPaciente = dbm.obtenerDniPorNombrePaciente(paciente);
                    dbm.anularCitaTrabajador(dni, dniPaciente, hora, fecha);
                    cargarCitasTrabajador();
                    datosCita.setTextIntoComentarioTextArea("");
                }
            }
        });
    }

    // Método para cargar en el JComboBox los doctores con citas del paciente
    private void cargarDoctores(JComboBox<String> combo) {
        combo.removeAllItems();
        // Obtenemos las citas con especialidad (el primer elemento es el nombre del doctor)
        List<String[]> citas = dbm.obtenerCitasPorDniConEspecialidad(dni);
        Set<String> doctores = new HashSet<>();
        for (String[] cita : citas) {
            doctores.add(cita[0]); // Se asume que el índice 0 contiene el nombre del doctor
        }
        if (doctores.isEmpty()) {
            combo.addItem("No hay doctores");
        } else {
            for (String doctor : doctores) {
                combo.addItem(doctor);
            }
        }
    }

    // Método para cargar en el JComboBox las especialidades con citas del paciente
    private void cargarEspecialidades(JComboBox<String> combo) {
        combo.removeAllItems();
        // Obtenemos las citas (el índice 3 se corresponde con la especialidad)
        List<String[]> citas = dbm.obtenerCitasPorDniConEspecialidad(dni);
        Set<String> especialidades = new HashSet<>();
        for (String[] cita : citas) {
            especialidades.add(cita[3]); // Se asume que el índice 3 contiene la especialidad
        }
        if (especialidades.isEmpty()) {
            combo.addItem("No hay especialidades");
        } else {
            for (String esp : especialidades) {
                combo.addItem(esp);
            }
        }
    }

    private void cargarCitasPaciente() {
        // Obtener citas y actualizar la tabla
        List<String[]> citas = dbm.obtenerCitasPorDniConEspecialidad(dni);
        String[][] data = citas.toArray(new String[0][]);
        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Dr/a", "Hora", "Día", "Especialidad"}));
    }

    // Dentro de la clase MisCitas, reemplaza el método filtrarCitasPaciente por el siguiente:
    private void filtrarCitasPaciente() {
        String criterio = (String) ordenarComboBox.getSelectedItem();
        List<String[]> citasFiltradas = new ArrayList<>();

        switch (criterio) {
            case "Día":
                // Se filtra por fecha. Se obtiene la fecha seleccionada del datePicker.
                java.util.Date fechaUtil = (java.util.Date) datePickerFiltro.getModel().getValue();
                if (fechaUtil != null) {
                    // Convertir a formato SQL (yyyy-MM-dd)
                    String fecha = new java.sql.Date(fechaUtil.getTime()).toString();
                    citasFiltradas = dbm.obtenerCitasPacientePorFecha(dni, fecha);
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione una fecha para filtrar.");
                    return;
                }
                break;
            case "Dr/a":
                // Se filtra por doctor
                String doctor = (String) filtroComboBox.getSelectedItem();
                citasFiltradas = dbm.obtenerCitasPacientePorDoctor(dni, doctor);
                break;
            case "Especialidad":
                // Se filtra por especialidad
                String especialidad = (String) filtroComboBox.getSelectedItem();
                citasFiltradas = dbm.obtenerCitasPacientePorEspecialidad(dni, especialidad);
                break;
            default:
                // Si no se selecciona un filtro válido, se cargan todas las citas
                citasFiltradas = dbm.obtenerCitasPorDniConEspecialidad(dni);
                break;
        }

        String[][] data = citasFiltradas.toArray(new String[0][]);
        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(
                data, new String[]{"Dr/a", "Hora", "Día", "Especialidad"}
        ));
    }


    public static boolean esFechaProximaPaciente(LocalDate fecha) {
        LocalDate hoy = LocalDate.now();
        long diasDiferencia = ChronoUnit.DAYS.between(hoy, fecha);
        return diasDiferencia > 1; // Por ejemplo, próxima dentro de 7 días
    }

    public static boolean esFechaProximaTrabajador(LocalDateTime fecha) {
        LocalDateTime ahora = LocalDateTime.now();
        long horasDiferencia = ChronoUnit.HOURS.between(ahora, fecha);
        return horasDiferencia > 1;
    }

    private void cargarCitasTrabajador() {
        // Obtener citas y actualizar la tabla
        List<String[]> citas = dbm.obtenerCitasPorDniSinEspecialidad(dni);
        String[][] data = citas.toArray(new String[0][]);
        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Paciente", "Hora", "Día"}));
    }

    private void filtrarCitasTrabajador() {
        String criterio = (String) ordenarComboBox.getSelectedItem();
        List<String[]> citasFiltradas = dbm.obtenerCitasFiltradasSinEspecialidad(dni, criterio);
        String[][] data = citasFiltradas.toArray(new String[0][]);
        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Paciente", "Hora", "Día"}));
    }


}
