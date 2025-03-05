package org.example.Apariencia;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.example.ClasesNativas.Trabajador;
import org.example.Database.DatabaseManagement;

public class Especialistas extends JPanel {

    public Especialistas(List<Trabajador> trabajadores) {
        // Organizar las tarjetas verticalmente y alinearlas a la izquierda
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        // Crear una tarjeta por cada trabajador
        for (Trabajador trabajador : trabajadores) {
            JPanel tarjeta = crearTarjeta(trabajador);
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160)); // Ocupa todo el ancho disponible
            tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT); // Asegura alineación izquierda
            add(tarjeta);
            add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre tarjetas
        }
    }

    private JPanel crearTarjeta(Trabajador trabajador) {
        JPanel tarjeta = new JPanel(new GridBagLayout());
        tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT); // Asegurar que la tarjeta esté alineada a la izquierda

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen de 10 píxeles en todos los lados

        // Label para la foto
        JLabel lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(150, 150));
        cargarFotoPerfilTrabajador(trabajador, lblFoto);

        // Ubicar la foto en la columna 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        tarjeta.add(lblFoto, gbc);

        // Panel para contener el texto
        JPanel panelTexto = new JPanel(new GridBagLayout());
        GridBagConstraints gbcTexto = new GridBagConstraints();
        gbcTexto.gridx = 0;
        gbcTexto.gridy = 0;
        gbcTexto.anchor = GridBagConstraints.WEST;
        gbcTexto.fill = GridBagConstraints.HORIZONTAL;
        gbcTexto.weightx = 1.0; // Permite que el texto se expanda

        // Label para el nombre del especialista
        JLabel lblNombre = new JLabel(trabajador.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        panelTexto.add(lblNombre, gbcTexto);

        // Label para la especialidad
        gbcTexto.gridy = 1;
        JLabel lblEspecialidad = new JLabel("<html><b>Especialidad: </b>" + trabajador.getEspecialidad() + "</html>");
        lblEspecialidad.setFont(new Font("Arial", Font.PLAIN, 16));
        panelTexto.add(lblEspecialidad, gbcTexto);

        // Ubicar el panel de texto en la columna 1
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Permite que el texto se expanda y quede alineado correctamente
        tarjeta.add(panelTexto, gbc);

        return tarjeta;
    }

    public void cargarFotoPerfilTrabajador(Trabajador t, JLabel labelFoto) {
        DatabaseManagement dbm = new DatabaseManagement();
        String rutaBD = dbm.obtenerRutaImagenTrabajador(t.getDNI());
        if (rutaBD != null && !rutaBD.isEmpty()) {
            java.net.URL imgURL = getClass().getClassLoader().getResource("images/" + rutaBD);
            if (imgURL != null) {
                ImageIcon icono = new ImageIcon(imgURL);
                Image imagenRedimensionada = icono.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                labelFoto.setIcon(new ImageIcon(imagenRedimensionada));
                labelFoto.setText("");
                labelFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY, 0));
            } else {
                labelFoto.setText("Imagen no encontrada");
            }
        } else {
            labelFoto.setText("Sin imagen");
        }
    }
}
