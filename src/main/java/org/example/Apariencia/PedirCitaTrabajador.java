package org.example.Apariencia;

import org.example.ClasesNativas.FechaSeleccionadaListener;
import org.example.Database.DatabaseManagement;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PedirCitaTrabajador extends JPanel implements FechaSeleccionadaListener {
    private JPanel leftContainer;
    private JPanel rightContainer;
    private Calendario calendario;
    private FiltrosCitaTrabajador filtrosCitaTrabajador;
    private AgendaMedicaTrabajador agendaMedicaTrabajador;
    private DatabaseManagement dbm;
    private LocalDate fechaSeleccionada;
    private String trabajadorSeleccionado;

    public PedirCitaTrabajador(String dni) {
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

        filtrosCitaTrabajador = new FiltrosCitaTrabajador();
        calendario = new Calendario();
        agendaMedicaTrabajador = new AgendaMedicaTrabajador(LocalDate.now(), dbm.obtenerNombrePorDniTrabajador(dni));

        leftContainer.add(calendario);
        leftContainer.add(filtrosCitaTrabajador);
        calendario.addFechaSeleccionadaListener(this); // Agregar listener

        rightContainer.add(agendaMedicaTrabajador);
        ListaPacientes listaPacientes = new ListaPacientes(filtrosCitaTrabajador);
        listaPacientes.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        rightContainer.add(listaPacientes);
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

        filtrosCitaTrabajador.addAgendarCitaListener(e -> {
            String hora = agendaMedicaTrabajador.getHorarioSeleccionado();
            String dia = fechaSeleccionada.toString();
            String paciente = filtrosCitaTrabajador.getNombrePaciente();
            String dniPaciente = dbm.obtenerDniPorNombrePaciente(paciente);
            String comentario = filtrosCitaTrabajador.getComentario();
            if(hora == null || dia == null || dniPaciente == null){
                JOptionPane.showMessageDialog(PedirCitaTrabajador.this, "Faltan datos por seleccionar, recuerda que debes seleccionar dia y hora", "Faltan datos", JOptionPane.ERROR_MESSAGE);
            }
            if (comentario.isEmpty()) {
                dbm.pedirCita(dniPaciente, dni, hora, dia, null);
            } else {
                dbm.pedirCita(dniPaciente, dni, hora, dia, comentario);
            }
        });

        setSize(800, 600);
        setVisible(true);
    }

    @Override
    public void onFechaSeleccionada(LocalDate fecha) {
        this.fechaSeleccionada = fecha;
        agendaMedicaTrabajador.actualizarFechaYTrabajador(fecha);
    }
}


