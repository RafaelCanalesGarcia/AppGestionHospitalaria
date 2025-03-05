package org.example.Apariencia;

import org.example.Database.DatabaseManagement;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ListaPacientes extends JPanel {
    private JTextField filtroTextField;
    private JList<String> listaPacientes;
    private DefaultListModel<String> listModel;
    private DatabaseManagement dbm;
    private FiltrosCitaTrabajador filtrosCitaTrabajador;

    public ListaPacientes(FiltrosCitaTrabajador filtrosCitaTrabajador) {
        this.filtrosCitaTrabajador = filtrosCitaTrabajador;
        dbm = new DatabaseManagement();
        setLayout(new BorderLayout());

        // Campo de texto para filtrar
        filtroTextField = new JTextField(20);
        add(filtroTextField, BorderLayout.NORTH);

        // Modelo de la lista
        listModel = new DefaultListModel<>();
        listaPacientes = new JList<>(listModel);
        add(new JScrollPane(listaPacientes), BorderLayout.CENTER);

        // Cargar lista inicial de pacientes
        cargarPacientes("");

        // Evento para actualizar la lista conforme se escribe en el campo de texto
        filtroTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarLista();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarLista();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarLista();
            }
        });

        // Evento para seleccionar un paciente y pasarlo al TextField de FiltrosCitaTrabajador
        listaPacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic para seleccionar
                    String selectedValue = listaPacientes.getSelectedValue();
                    if (selectedValue != null) {
                        String[] datosPaciente = selectedValue.split(" - "); // Separar nombre y DNI
                        if (datosPaciente.length == 2) {
                            filtrosCitaTrabajador.getNombrePacienteField().setText(datosPaciente[0]); // Solo el nombre
                        }
                    }
                }
            }
        });
    }

    private void actualizarLista() {
        String filtro = filtroTextField.getText().trim().toLowerCase();
        cargarPacientes(filtro);
    }

    private void cargarPacientes(String filtro) {
        List<String[]> pacientes = dbm.obtenerListaPacientes();
        listModel.clear();
        List<String> filtrados = pacientes.stream()
                .filter(paciente -> paciente[0].toLowerCase().contains(filtro) || paciente[1].contains(filtro))
                .map(paciente -> paciente[0] + " - " + "(" + paciente[1]+")") // Mostrar "Nombre - DNI"
                .collect(Collectors.toList());
        filtrados.forEach(listModel::addElement);
    }

}
