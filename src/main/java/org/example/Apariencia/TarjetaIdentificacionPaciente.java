package org.example.Apariencia;

import org.example.ClasesNativas.TarjetaIdentificacion;
import org.example.Database.DatabaseConnection;
import org.example.Database.DatabaseManagement;
import org.example.ClasesNativas.Enums.Cobertura;
import org.example.ClasesNativas.Enums.Compania;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class TarjetaIdentificacionPaciente extends JPanel {
    private File nuevaImagen = null;
    private JLabel lblNombre, lblSexo, lblFoto, lblEmail, lblTelefono, lblFechaNacimiento, lblDomicilio, lblPaisNacimiento, lblSeguro, lblCompania, lblCobertura;
    private JTextField txtNombre, txtSexo, txtEmail, txtTelefono, txtFechaNacimiento, txtDomicilio, txtPaisNacimiento, txtSeguro;
    private JComboBox<Compania> cmbCompania;
    private JComboBox<Cobertura> cmbCobertura;
    private JButton btnModificar, btnGuardar, btnAnadirImagen;
    private String dni, numeroPolizaOriginal;
    private DatabaseManagement dbm = new DatabaseManagement();


    public TarjetaIdentificacionPaciente(String dni) {
        this.dni = dni;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre encima de la imagen
        lblNombre = new JLabel("", SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        add(lblNombre, gbc);

        // Foto
        lblFoto = new JLabel("Foto", SwingConstants.CENTER);
        lblFoto.setPreferredSize(new Dimension(120, 120));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 5;
        gbc.gridwidth = 2;
        add(lblFoto, gbc);

        // Botón Añadir Imagen
        btnAnadirImagen = new JButton("Añadir Imagen");
        btnAnadirImagen.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(btnAnadirImagen, gbc);
        btnAnadirImagen.addActionListener(e -> seleccionarImagen());

        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        Dimension textFieldSize = new Dimension(150, 25);

        // Primera columna debajo de la imagen
        lblSexo = new JLabel("Sexo");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(lblSexo, gbc);
        txtSexo = new JTextField();
        txtSexo.setEditable(false);
        txtSexo.setPreferredSize(textFieldSize);
        txtSexo.setMaximumSize(textFieldSize);
        txtSexo.setMinimumSize(textFieldSize);
        gbc.gridx = 1;
        add(txtSexo, gbc);

        lblPaisNacimiento = new JLabel("País Nacimiento");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(lblPaisNacimiento, gbc);
        txtPaisNacimiento = new JTextField();
        txtPaisNacimiento.setEditable(false);
        txtPaisNacimiento.setPreferredSize(textFieldSize);
        txtPaisNacimiento.setMaximumSize(textFieldSize);
        txtPaisNacimiento.setMinimumSize(textFieldSize);
        gbc.gridx = 1;
        add(txtPaisNacimiento, gbc);

        lblDomicilio = new JLabel("Domicilio");
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(lblDomicilio, gbc);
        txtDomicilio = new JTextField();
        txtDomicilio.setEditable(false);
        txtDomicilio.setPreferredSize(textFieldSize);
        txtDomicilio.setMaximumSize(textFieldSize);
        txtDomicilio.setMinimumSize(textFieldSize);

        gbc.gridx = 1;
        add(txtDomicilio, gbc);

        lblTelefono = new JLabel("Teléfono");
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(lblTelefono, gbc);
        txtTelefono = new JTextField();
        txtTelefono.setEditable(false);
        txtTelefono.setPreferredSize(textFieldSize);
        txtTelefono.setMaximumSize(textFieldSize);
        txtTelefono.setMinimumSize(textFieldSize);
        gbc.gridx = 1;
        add(txtTelefono, gbc);

        // Segunda columna
        lblEmail = new JLabel("Email");
        gbc.gridx = 2;
        gbc.gridy = 6;
        add(lblEmail, gbc);
        txtEmail = new JTextField();
        txtEmail.setEditable(false);
        txtEmail.setPreferredSize(textFieldSize);
        txtEmail.setMaximumSize(textFieldSize);
        txtEmail.setMinimumSize(textFieldSize);
        gbc.gridx = 3;
        add(txtEmail, gbc);

        lblSeguro = new JLabel("Seguro");
        gbc.gridx = 2;
        gbc.gridy = 7;
        add(lblSeguro, gbc);
        txtSeguro = new JTextField();
        txtSeguro.setEditable(false);
        txtSeguro.setPreferredSize(textFieldSize);
        txtSeguro.setMaximumSize(textFieldSize);
        txtSeguro.setMinimumSize(textFieldSize);
        gbc.gridx = 3;
        add(txtSeguro, gbc);

        lblCompania = new JLabel("Compañía");
        gbc.gridx = 2;
        gbc.gridy = 8;
        add(lblCompania, gbc);
        cmbCompania = new JComboBox<>(Compania.values());
        cmbCompania.setEnabled(false);
        cmbCompania.setPreferredSize(textFieldSize);
        gbc.gridx = 3;
        add(cmbCompania, gbc);

        lblCobertura = new JLabel("Cobertura");
        gbc.gridx = 2;
        gbc.gridy = 9;
        add(lblCobertura, gbc);
        cmbCobertura = new JComboBox<>(Cobertura.values());
        cmbCobertura.setEnabled(false);
        cmbCobertura.setPreferredSize(textFieldSize);
        gbc.gridx = 3;
        add(cmbCobertura, gbc);

        // Botón modificar
        btnModificar = new JButton("Modificar datos");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 4;
        add(btnModificar, gbc);

        // Botón guardar debajo del botón modificar
        btnGuardar = new JButton("Guardar");
        btnGuardar.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 4;
        add(btnGuardar, gbc);

        btnModificar.addActionListener(e -> alternarEdicion(true));
        btnGuardar.addActionListener(e -> {
            alternarEdicion(false);
            // Aquí se actualizan otros datos del paciente (correo, teléfono, etc.)
            actualizarDatos();

            // Si se seleccionó una nueva imagen
            if (nuevaImagen != null) {
                // Directorio destino dentro de resources/images
                File directorioDestino = new File("resources/images");
                if (!directorioDestino.exists()) {
                    directorioDestino.mkdirs(); // Crear directorio si no existe
                }
                String nombreArchivo = nuevaImagen.getName();
                File destino = new File(directorioDestino, nombreArchivo);

                try {
                    // Borrar la imagen anterior para liberar espacio
                    String rutaAnterior = dbm.obtenerRutaImagenPaciente(dni);
                    if (rutaAnterior != null && !rutaAnterior.isEmpty()) {
                        File imagenAnterior = new File(rutaAnterior);
                        if (imagenAnterior.exists()) {
                            imagenAnterior.delete();
                        }
                    }
                    // Copiar la nueva imagen al directorio destino
                    Files.copy(nuevaImagen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    // Actualizar la ruta de la imagen en la base de datos
                    dbm.guardarRutaImagenPaciente(dni, destino.getPath());
                    // Actualizar la interfaz con la nueva imagen
                    lblFoto.setIcon(new ImageIcon(destino.getAbsolutePath()));
                    lblFoto.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al guardar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        cargarDatosTarjetaIdentificacionPaciente();
    }

    private void cargarDatosTarjetaIdentificacionPaciente() {
        TarjetaIdentificacion tarjeta = dbm.obtenerDatosTarjetaIdentificacion(dni);

        if (tarjeta != null) {
            lblNombre.setText(tarjeta.getNombre());
            txtEmail.setText(tarjeta.getEmail());
            txtSexo.setText(tarjeta.getSexo());
            txtTelefono.setText(tarjeta.getTelefono());
            txtSeguro.setText(tarjeta.getSeguro());
            this.numeroPolizaOriginal = tarjeta.getSeguro();
            txtDomicilio.setText(tarjeta.getDomicilio());
            txtPaisNacimiento.setText(tarjeta.getPaisNacimiento());

            cmbCompania.setSelectedItem(Compania.valueOf(tarjeta.getCompania()));
            cmbCobertura.setSelectedItem(Cobertura.valueOf(tarjeta.getCobertura()));

            cargarFotoPerfilPaciente(dni, lblFoto);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarFotoPerfilPaciente(String dni, JLabel labelFoto) {
        DatabaseManagement dbm = new DatabaseManagement();
        String rutaBD = dbm.obtenerRutaImagenPaciente(dni);

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


    private void alternarEdicion(boolean editable) {
        txtDomicilio.setEditable(editable);
        txtTelefono.setEditable(editable);
        txtEmail.setEditable(editable);
        txtSeguro.setEditable(editable);
        cmbCompania.setEnabled(editable);
        cmbCobertura.setEnabled(editable);
        btnAnadirImagen.setVisible(editable);
        btnGuardar.setVisible(editable);
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            nuevaImagen = fileChooser.getSelectedFile();
            // Se muestra la imagen seleccionada de forma temporal en el JLabel
            lblFoto.setIcon(new ImageIcon(nuevaImagen.getAbsolutePath()));
            lblFoto.setText("");
        }
    }

    private void actualizarDatos() {
        // Recuperar datos actualizados de los campos editables
        String nuevoEmail = txtEmail.getText().trim();
        String nuevoTelefono = txtTelefono.getText().trim();
        String nuevoDomicilio = txtDomicilio.getText().trim();
        String nuevoSeguro = txtSeguro.getText().trim();
        Compania nuevaCompania = (Compania) cmbCompania.getSelectedItem();
        Cobertura nuevaCobertura = (Cobertura) cmbCobertura.getSelectedItem();

        try {
            Connection conn = DatabaseConnection.getConnection();

            // Actualizar datos del paciente
            String sqlPaciente = "UPDATE Paciente SET Email = ?, Telefono = ?, Domicilio = ?, Seguro = ? WHERE DNI = ?";
            PreparedStatement psPaciente = conn.prepareStatement(sqlPaciente);
            psPaciente.setString(1, nuevoEmail);
            psPaciente.setString(2, nuevoTelefono);
            psPaciente.setString(3, nuevoDomicilio);
            psPaciente.setString(4, nuevoSeguro);
            psPaciente.setString(5, dni);
            int filasPaciente = psPaciente.executeUpdate();

            if (filasPaciente > 0) {
                // Si el número de póliza ha cambiado, se elimina el registro antiguo y se crea uno nuevo
                if (!nuevoSeguro.equals(numeroPolizaOriginal)) {
                    // Eliminar el registro de seguro con el número de póliza antiguo
                    String sqlDeleteSeguro = "DELETE FROM Seguro WHERE Numero = ?";
                    PreparedStatement psDeleteSeguro = conn.prepareStatement(sqlDeleteSeguro);
                    System.out.println(numeroPolizaOriginal);
                    psDeleteSeguro.setString(1, numeroPolizaOriginal);
                    psDeleteSeguro.executeUpdate();

                    // Insertar el nuevo registro de seguro
                    String sqlInsertSeguro = "INSERT INTO Seguro (Paciente, Numero, Compañia, Cobertura) VALUES (?, ?, ?, ?)";
                    PreparedStatement psInsertSeguro = conn.prepareStatement(sqlInsertSeguro);
                    // Se asume que el Titular es el nombre del paciente (lblNombre)
                    psInsertSeguro.setString(1, dni);
                    psInsertSeguro.setString(2, nuevoSeguro);
                    psInsertSeguro.setString(3, nuevaCompania.toString());
                    psInsertSeguro.setString(4, nuevaCobertura.toString());
                    int filasInsertSeguro = psInsertSeguro.executeUpdate();

                    if (filasInsertSeguro > 0) {
                        JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        // Actualizamos el valor original para futuras modificaciones
                        numeroPolizaOriginal = nuevoSeguro;
                    } else {
                        JOptionPane.showMessageDialog(this, "Datos del paciente actualizados, pero no se pudo crear el nuevo seguro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    // Si el número de póliza no cambió, se actualizan los datos del seguro
                    String sqlUpdateSeguro = "UPDATE Seguro SET Compañia = ?, Cobertura = ? WHERE Numero = ?";
                    PreparedStatement psUpdateSeguro = conn.prepareStatement(sqlUpdateSeguro);
                    psUpdateSeguro.setString(1, nuevaCompania.toString());
                    psUpdateSeguro.setString(2, nuevaCobertura.toString());
                    psUpdateSeguro.setString(3, nuevoSeguro);
                    int filasUpdateSeguro = psUpdateSeguro.executeUpdate();

                    if (filasUpdateSeguro > 0) {
                        JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Datos del paciente actualizados, pero no se pudo actualizar el seguro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar los datos del paciente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}