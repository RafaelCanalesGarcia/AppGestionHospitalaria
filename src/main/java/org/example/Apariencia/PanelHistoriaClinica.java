package org.example.Apariencia;

import org.example.ClasesNativas.HistoriaClinica;
import org.example.Database.DatabaseManagement;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
public class PanelHistoriaClinica extends JPanel {
    private JLabel lblImagen, lblFechaPrueba, lblTipoPrueba, lblDiagnostico;
    private JLabel lblDescripcion, lblMotivoConsulta, lblExploracionFisica, lblTratamiento;
    private String dniPaciente;
    private int codigoHistoria;
    private DatabaseManagement dbm;

    public PanelHistoriaClinica(String dniPaciente, int codigoHistoria) {
        this.dniPaciente = dniPaciente;
        this.codigoHistoria = codigoHistoria;
        this.dbm = new DatabaseManagement();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Panel izquierdo (Imagen, Fecha de prueba, Tipo de prueba, Diagnóstico)
        JPanel panelIzquierdo = new JPanel(new GridBagLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Información General"));

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(5, 5, 5, 5);
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.anchor = GridBagConstraints.WEST;

        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(150, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelIzquierdo.add(lblImagen, gbcLeft);

        gbcLeft.gridy++;
        lblFechaPrueba = crearLabel("Fecha de prueba: ", "");
        panelIzquierdo.add(lblFechaPrueba, gbcLeft);

        gbcLeft.gridy++;
        lblTipoPrueba = crearLabel("Tipo de prueba: ", "");
        panelIzquierdo.add(lblTipoPrueba, gbcLeft);

        gbcLeft.gridy++;
        lblDiagnostico = crearLabel("Diagnóstico: ", "");
        panelIzquierdo.add(lblDiagnostico, gbcLeft);

        // Panel derecho (Descripción, Motivo de consulta, Exploración física, Tratamiento)
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Detalles de la Consulta"));

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 5);
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.WEST;

        lblDescripcion = crearLabel("Descripción: ", "");
        panelDerecho.add(lblDescripcion, gbcRight);

        gbcRight.gridy++;
        lblMotivoConsulta = crearLabel("Motivo de consulta: ", "");
        panelDerecho.add(lblMotivoConsulta, gbcRight);

        gbcRight.gridy++;
        lblExploracionFisica = crearLabel("Exploración física: ", "");
        panelDerecho.add(lblExploracionFisica, gbcRight);

        gbcRight.gridy++;
        lblTratamiento = crearLabel("Tratamiento: ", "");
        panelDerecho.add(lblTratamiento, gbcRight);

        // Agregar paneles al contenedor principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panelIzquierdo, gbc);

        gbc.gridy = 1;
        add(panelDerecho, gbc);

        // Cargar datos desde la base de datos
        cargarDatosHistoriaClinica();
    }

    private JLabel crearLabel(String titulo, String valor) {
        JLabel label = new JLabel("<html><b>" + titulo + "</b> <span style='font-size:12px;'>" + valor + "</span></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private void cargarDatosHistoriaClinica() {
        HistoriaClinica historia = dbm.obtenerHistoriaClinica(codigoHistoria);

        if (historia != null) {
            lblFechaPrueba.setText("<html><b>Fecha de prueba:</b> " + historia.getFecha() + "</html>");
            lblTipoPrueba.setText("<html><b>Tipo de prueba:</b> " + historia.getTipoPrueba() + "</html>");
            lblDiagnostico.setText("<html><b>Diagnóstico:</b> " + historia.getDiagnostico() + "</html>");
            lblDescripcion.setText("<html><b>Descripción:</b> " + historia.getDescripcion() + "</html>");
            lblMotivoConsulta.setText("<html><b>Motivo de consulta:</b> " + historia.getMotivoConsulta() + "</html>");
            lblExploracionFisica.setText("<html><b>Exploración física:</b> " + historia.getExploracionFisica() + "</html>");
            lblTratamiento.setText("<html><b>Tratamiento:</b> " + historia.getTratamiento() + "</html>");

            // Obtener el nombre del archivo desde la base de datos
            String nombreArchivo = dbm.obtenerRutaImagenHistoriaClinica(codigoHistoria);
            System.out.println("Nombre del archivo obtenido: " + nombreArchivo);

            if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                cargarFotoHistoriaClinica(lblImagen);
            } else {
                lblImagen.setIcon(null);
                lblImagen.setText("No hay imagen");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron datos de la historia clínica.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarFotoHistoriaClinica(JLabel labelFoto) {
        DatabaseManagement dbm = new DatabaseManagement();
        String rutaBD = dbm.obtenerRutaImagenHistoriaClinica(codigoHistoria);

        if (rutaBD != null && !rutaBD.isEmpty()) {
            java.net.URL imgURL = getClass().getClassLoader().getResource("images/" + rutaBD);
            if (imgURL != null) {
                ImageIcon icono = new ImageIcon(imgURL);
                // Obtener dimensiones del JLabel o usar sus dimensiones preferidas
                Dimension size = labelFoto.getSize();
                if (size.width == 0 || size.height == 0) {
                    size = labelFoto.getPreferredSize();
                }
                // Redimensionar la imagen para que se ajuste y se vea suave
                Image imagenRedimensionada = icono.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
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
