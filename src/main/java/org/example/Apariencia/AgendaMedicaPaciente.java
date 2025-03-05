package org.example.Apariencia;

import org.example.Database.DatabaseManagement;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AgendaMedicaPaciente extends JPanel {
    private JLabel dateLabel;
    private JLabel trabajadorLabel;
    private LocalDate selectedDate;
    private String trabajador;
    private JPanel agendaPanel;
    private List<JButton> botonesHorarios = new ArrayList<>();
    private JButton selectedButton = null; // Botón seleccionado actualmente
    DatabaseManagement dbm = new DatabaseManagement();

    public AgendaMedicaPaciente(LocalDate date, String trabajador) {
        setLayout(new GridLayout(3,1));
        this.selectedDate = date;
        this.trabajador = trabajador;
        trabajadorLabel = new JLabel(trabajador, SwingConstants.CENTER);

        trabajadorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(trabajadorLabel, BorderLayout.NORTH);
        // Encabezado con la fecha seleccionada
        dateLabel = new JLabel(getFormattedDate(selectedDate), SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(dateLabel, BorderLayout.NORTH);

        // Panel con cuadrícula de 3 filas y 4 columnas
        agendaPanel = new JPanel(new GridLayout(3, 4, 20, 20)); // 3 filas, 4 columnas, con espacios
        cargarAgenda(date,trabajador); // Cargar horarios disponibles y ocupados
        add(agendaPanel, BorderLayout.CENTER);
    }

    public void cargarAgenda(LocalDate localDate, String trabajador) {
        agendaPanel.removeAll(); // Limpiar el panel antes de cargar datos nuevos
        botonesHorarios.clear();
        dateLabel.setText(localDate.toString());
        trabajadorLabel.setText(trabajador);

        List<String> horariosOcupados = dbm.obtenerHorariosOcupados(selectedDate, trabajador);

        // Obtener la hora actual solo si la fecha seleccionada es la de hoy
        boolean esHoy = LocalDate.now().equals(localDate);
        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        for (String horario : obtenerTodosLosHorarios()) {
            JButton botonHorario = new JButton(horario);
            LocalTime horaBoton = LocalTime.parse(horario, formatter);

            if (horariosOcupados.contains(horario)) {
                botonHorario.setBackground(Color.RED); // Color rojo para ocupados
                botonHorario.setEnabled(false);// No se puede seleccionar


            } else if (esHoy && horaBoton.isBefore(horaActual)) {
                botonHorario.setBackground(Color.LIGHT_GRAY); // Color gris para horarios pasados
                botonHorario.setEnabled(false);
            } else {
                botonHorario.setBackground(new Color(144, 238, 144)); // Verde para disponibles
                botonHorario.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Borde normal
                botonHorario.addActionListener(e -> seleccionarHorario(botonHorario));
            }

            botonesHorarios.add(botonHorario);
            agendaPanel.add(botonHorario);
        }

        agendaPanel.revalidate();
        agendaPanel.repaint();
    }

    private void seleccionarHorario(JButton boton) {
        if (selectedButton != null) {
            selectedButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Restaurar el borde del anterior
        }
        boton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); // Borde azul al seleccionar
        selectedButton = boton;
    }

    private String getFormattedDate(LocalDate date) {
        return date.getDayOfWeek().toString() + " " + date.getDayOfMonth() + " de " + date.getMonth() + " de " + date.getYear();
    }

    private List<String> obtenerTodosLosHorarios() {
        return List.of(
                "08:00", "08:45", "09:30", "10:15",
                "11:00", "11:45", "12:30", "13:15",
                "14:00", "14:45", "15:30", "16:15"
        );
    }

    public void actualizarFechaYTrabajador(LocalDate nuevaFecha, String nuevoTrabajador) {
        this.selectedDate = nuevaFecha;
        this.trabajador = nuevoTrabajador;
        trabajadorLabel.setText(nuevoTrabajador); // Actualiza el JLabel con el nombre del trabajador
        dateLabel.setText(getFormattedDate(nuevaFecha));
        cargarAgenda(nuevaFecha,nuevoTrabajador);
    }

    public String getHorarioSeleccionado() {
        return (selectedButton != null) ? selectedButton.getText() : null;
    }
}
