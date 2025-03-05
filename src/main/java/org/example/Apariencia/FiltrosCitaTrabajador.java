package org.example.Apariencia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FiltrosCitaTrabajador extends JPanel {
    private JTextField nombrePacienteField;
    private JTextArea comentarioTextArea;
    private JButton agendarCitaButton;

    public FiltrosCitaTrabajador() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre del paciente
        JLabel nombreLabel = new JLabel("Nombre Paciente:");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        add(nombreLabel, gbc);

        nombrePacienteField = new JTextField(20);
        nombrePacienteField.setEditable(false);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        add(nombrePacienteField, gbc);

        // Comentario
        JLabel comentarioLabel = new JLabel("Comentario:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        add(comentarioLabel, gbc);

        comentarioTextArea = new JTextArea(5, 20);
        comentarioTextArea.setLineWrap(true);
        comentarioTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(comentarioTextArea);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        add(scrollPane, gbc);

        // Botón Agendar Cita
        agendarCitaButton = new JButton("Agendar Cita");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(agendarCitaButton, gbc);
    }

    public JTextField getNombrePacienteField() {
        return nombrePacienteField;
    }

    public void setNombrePacienteField(JTextField nombrePacienteField) {
        this.nombrePacienteField = nombrePacienteField;
    }

    public String getNombrePaciente() {
        return nombrePacienteField.getText().trim();
    }

    public String getComentario() {
        return comentarioTextArea.getText().trim();
    }

    public JButton getAgendarCitaButton() {
        return agendarCitaButton;
    }

    public void addAgendarCitaListener(ActionListener listener) {
        agendarCitaButton.addActionListener(listener);
    }



}

