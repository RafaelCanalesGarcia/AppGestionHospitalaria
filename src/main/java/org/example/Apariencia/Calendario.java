package org.example.Apariencia;

import org.example.ClasesNativas.FechaSeleccionadaListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Calendario extends JPanel {
    private JPanel calendarPanel;
    private JLabel monthLabel;
    private LocalDate currentDate;
    private JButton selectedButton = null; // Botón seleccionado actualmente
    private List<JButton> dayButtons = new ArrayList<>(); // Lista de botones de los días
    private List<FechaSeleccionadaListener> listeners = new ArrayList<>();

    public void addFechaSeleccionadaListener(FechaSeleccionadaListener listener) {
        listeners.add(listener);
    }
    public Calendario() {
        setLayout(new BorderLayout());

        currentDate = LocalDate.now(); // Fecha actual

        // Panel superior con flechas de navegación y mes/año
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        monthLabel = new JLabel("", SwingConstants.CENTER);

        prevButton.addActionListener(e -> cambiarMes(-1));
        nextButton.addActionListener(e -> cambiarMes(1));

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(monthLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Panel del calendario
        calendarPanel = new JPanel(new GridLayout(0, 7)); // 7 columnas (días de la semana)
        add(calendarPanel, BorderLayout.CENTER);

        cargarCalendario(currentDate); // Cargar mes actual
    }

    private void cargarCalendario(LocalDate fecha) {
        calendarPanel.removeAll(); // Limpiar el panel
        dayButtons.clear(); // Limpiar lista de botones de días
        YearMonth mesActual = YearMonth.of(fecha.getYear(), fecha.getMonthValue());
        monthLabel.setText(fecha.getMonth() + " " + fecha.getYear());

        // Obtener la fecha actual
        LocalDate hoy = LocalDate.now();

        // Encabezado de días de la semana
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : diasSemana) {
            calendarPanel.add(new JLabel(dia, SwingConstants.CENTER));
        }

        // Obtener información del mes actual
        LocalDate primerDia = mesActual.atDay(1);
        int primerDiaSemana = primerDia.getDayOfWeek().getValue(); // 1 = Lunes, 7 = Domingo
        int diasEnMes = mesActual.lengthOfMonth();

        // Obtener información del mes anterior
        YearMonth mesAnterior = mesActual.minusMonths(1);
        int diasMesAnterior = mesAnterior.lengthOfMonth();

        // Agregar los días del mes anterior (en gris y deshabilitados)
        for (int i = primerDiaSemana - 1; i > 0; i--) {
            JButton diaPrevio = new JButton(String.valueOf(diasMesAnterior - i + 1));
            diaPrevio.setEnabled(false);
            diaPrevio.setForeground(Color.GRAY);
            diaPrevio.setBackground(Color.LIGHT_GRAY);
            calendarPanel.add(diaPrevio);
        }

        // Agregar los días del mes actual como botones seleccionables
        for (int dia = 1; dia <= diasEnMes; dia++) {
            JButton dayButton = new JButton(String.valueOf(dia));
            LocalDate fechaBoton = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dia);

            if (fechaBoton.isBefore(hoy)) {
                // Deshabilitar días anteriores al día actual
                dayButton.setEnabled(false);
                dayButton.setForeground(Color.GRAY);
                dayButton.setBackground(Color.LIGHT_GRAY);
            } else {
                dayButton.setBackground(Color.WHITE);
                dayButton.addActionListener(e -> seleccionarDia(dayButton));
            }

            calendarPanel.add(dayButton);
            dayButtons.add(dayButton);
        }

        // Calcular cuántos días del siguiente mes se necesitan para completar la última fila
        int totalDias = primerDiaSemana - 1 + diasEnMes;
        int diasProximoMes = (totalDias % 7 == 0) ? 0 : 7 - (totalDias % 7);

        // Agregar los días del próximo mes (en gris y deshabilitados)
        for (int i = 1; i <= diasProximoMes; i++) {
            JButton diaProximo = new JButton(String.valueOf(i));
            diaProximo.setEnabled(false);
            diaProximo.setForeground(Color.GRAY);
            diaProximo.setBackground(Color.LIGHT_GRAY);
            calendarPanel.add(diaProximo);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }


    private void seleccionarDia(JButton button) {
        if (selectedButton != null) {
            selectedButton.setBackground(Color.WHITE);
        }
        button.setBackground(Color.CYAN);
        selectedButton = button;

        // Obtener la fecha seleccionada
        LocalDate fechaSeleccionada = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), Integer.parseInt(button.getText()));

        // Notificar a los listeners
        for (FechaSeleccionadaListener listener : listeners) {
            listener.onFechaSeleccionada(fechaSeleccionada);
        }
    }

    private void cambiarMes(int cambio) {
        currentDate = currentDate.plusMonths(cambio); // Cambiar el mes
        selectedButton = null; // Resetear selección al cambiar de mes
        cargarCalendario(currentDate); // Actualizar el calendario
    }
}
