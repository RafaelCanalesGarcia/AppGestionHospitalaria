package org.example.Registro;

import org.example.Database.DatabaseManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CambioContraseña extends JFrame {
    private JLabel lblCodigo;
    private JLabel lblNuevaContraseña;
    private JLabel lblRepiteContraseña;
    private JTextField txtCodigo;
    private JTextField txtNuevaContraseña;
    private JTextField txtRepiteContraseña;
    private JButton btnConfirmar;
    private DatabaseManagement dbm = new DatabaseManagement();

    public CambioContraseña(String email,String codigo) {
        setTitle("Cambio de Contraseña");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Fila 0: Etiqueta "Codigo" y campo de texto
        lblCodigo = new JLabel("Codigo:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblCodigo, gbc);

        txtCodigo = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtCodigo, gbc);

        // Fila 1: Etiqueta "Nueva Contraseña" y campo de texto
        lblNuevaContraseña = new JLabel("Nueva Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        add(lblNuevaContraseña, gbc);

        txtNuevaContraseña = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtNuevaContraseña, gbc);

        // Fila 2: Etiqueta "Repite contraseña" y campo de texto
        lblRepiteContraseña = new JLabel("Repite contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        add(lblRepiteContraseña, gbc);

        txtRepiteContraseña = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtRepiteContraseña, gbc);

        // Fila 3: Botón "Confirmar" que abarca dos columnas
        btnConfirmar = new JButton("Confirmar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(btnConfirmar, gbc);

        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String antiguaContraseña = hashPassword(dbm.obtenerContraseñaPorEmail(email));
                String confirmarCodigo = txtCodigo.getText();
                String contraseñaNueva = txtNuevaContraseña.getText();
                String confirmarContraseña = txtRepiteContraseña.getText();
                if (codigo.length() == 6) {
                    if (contraseñaNueva.equals(confirmarContraseña)) {
                        if (ValidacionRegistro.esCodigoVigente(codigo) && confirmarCodigo.equals(codigo)) {
                            if (ValidacionRegistro.validarCodigo(ValidacionRegistro.getEmailPorCodigo(codigo), codigo)) {
                                JOptionPane.showMessageDialog(CambioContraseña.this, "Tu contraseña ha sido modificada!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                String dni = dbm.obtenerDNI(email);
                                String contraseñaHash = hashPassword(contraseñaNueva);
                                if(ValidacionRegistro.validarContrasena(contraseñaNueva)) {
                                    if(antiguaContraseña.equals(contraseñaHash)){
                                        JOptionPane.showMessageDialog(CambioContraseña.this, "No puedes utilizar la misma contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        dbm.cambiaContraseña(contraseñaHash, dni);
                                    }
                                }
                                dispose(); // Cerrar la ventana modal

                            } else {
                                JOptionPane.showMessageDialog(CambioContraseña.this, "Código incorrecto, prueba de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(CambioContraseña.this, "El codigo es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(CambioContraseña.this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(CambioContraseña.this, "Por favor, introduce un código de 6 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el hash de la contraseña", e);
        }
    }
    // Getters para acceder a los componentes desde otras clases, si se requiere
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNuevaContraseña() {
        return txtNuevaContraseña;
    }

    public JTextField getTxtRepiteContraseña() {
        return txtRepiteContraseña;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }
}
