package org.example.Apariencia;

import org.example.ClasesNativas.FechaSeleccionadaListener;
import org.example.ClasesNativas.TrabajadorSeleccionadoListener;
import org.example.Database.DatabaseManagement;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PedirCitaPaciente extends JPanel implements FechaSeleccionadaListener, TrabajadorSeleccionadoListener {
    private JPanel leftContainer;
    private JPanel rightContainer;
    private Calendario calendario;
    private FiltrosCitaPaciente filtrosCitaPaciente;
    private AgendaMedicaPaciente agendaMedicaPaciente;
    private DatabaseManagement dbm;
    private LocalDate fechaSeleccionada;
    private String trabajadorSeleccionado;

    public PedirCitaPaciente(String dni) {
        dbm = new DatabaseManagement();
        setLayout(new GridBagLayout());

        // Panel izquierdo dividido en 2
        leftContainer = new JPanel(new GridLayout(2, 1));
        leftContainer.setPreferredSize(new Dimension(400, 600)); // Tamaño ajustable
        leftContainer.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        // Panel derecho dividido en 3
        rightContainer = new JPanel(new GridLayout(2, 1));
        rightContainer.setPreferredSize(new Dimension(400, 600)); // Tamaño ajustable
        rightContainer.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        filtrosCitaPaciente = new FiltrosCitaPaciente();
        calendario = new Calendario();
        agendaMedicaPaciente = new AgendaMedicaPaciente(LocalDate.now(), dbm.obtenerDniPorNombreTrabajador(filtrosCitaPaciente.getTrabajadorSeleccionado()));

        leftContainer.add(calendario);
        leftContainer.add(filtrosCitaPaciente);
        calendario.addFechaSeleccionadaListener(this); // Agregar listener

        rightContainer.add(agendaMedicaPaciente);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Lado izquierdo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Ocupa la mitad
        add(leftContainer, gbc);

        // Lado derecho
        gbc.gridx = 1;
        gbc.weightx = 0.5; // Ocupa la otra mitad
        add(rightContainer, gbc);

        filtrosCitaPaciente.addAgendarCitaListener(e -> {
            String nombreTrabajador = filtrosCitaPaciente.getTrabajadorSeleccionado();
            String dniTrabajador = dbm.obtenerDniPorNombreTrabajador(nombreTrabajador);
            String hora = agendaMedicaPaciente.getHorarioSeleccionado();
            String dia = fechaSeleccionada.toString();
            String comentario = null;

            if (dniTrabajador == null || hora == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un horario y un trabajador válido.");
                return;
            }

            dbm.pedirCita(dni, dniTrabajador, hora, dia, null);

        });
        filtrosCitaPaciente.addTrabajadorSeleccionadoListener(this);

        setSize(800, 600);
        setVisible(true);
    }

    @Override
    public void onFechaSeleccionada(LocalDate fecha) {
        this.fechaSeleccionada = fecha;
        String trabajadorSeleccionado = filtrosCitaPaciente.getTrabajadorSeleccionado();
        agendaMedicaPaciente.actualizarFechaYTrabajador(fecha, trabajadorSeleccionado);
    }

    //    private void cambiarContenido(String cardName) {
//        CardLayout cl = (CardLayout) centerPanel.getLayout();
//        cl.show(centerPanel, cardName);
//    }

    public static void main(String[] args) {
        PedirCitaPaciente v = new PedirCitaPaciente("PAC002");
    }

    @Override
    public void onTrabajadorSeleccionado(String trabajador) {
        this.trabajadorSeleccionado = trabajador;
        if (fechaSeleccionada == null) {
            fechaSeleccionada = LocalDate.now();
        }
        actualizarAgenda();
    }

    private void actualizarAgenda() {
        agendaMedicaPaciente.actualizarFechaYTrabajador(fechaSeleccionada, trabajadorSeleccionado);
    }
}

