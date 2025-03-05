package org.example.Registro;

import org.example.Database.DatabaseManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OlvidaContraseña extends JFrame {
    private JLabel lblInfo;
    private JTextField txtCorreo;
    private JButton btnEnviar;
    DatabaseManagement dbm = new DatabaseManagement();


    public OlvidaContraseña() {

        setLayout(new GridBagLayout());
        setSize(400, 200);
        setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Fila 0: lblInfo centrado
        lblInfo = new JLabel("Introduce tu correo electrónico");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblInfo, gbc);

        // Fila 1: JTextField para el correo
        txtCorreo = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtCorreo, gbc);

        // Fila 2: Botón Enviar centrado
        btnEnviar = new JButton("Enviar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnEnviar, gbc);

        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = txtCorreo.getText();
                if (dbm.existsEmail(correo)) {
                    String codigo = ValidacionRegistro.generarCodigo();
                    ValidacionRegistro.guardarCodigo(correo, codigo);
                    EnvioCorreo.enviarCorreo(correo, "Código para cambio de contraseña", "El código de validación para cambio de contraseña: " + codigo);
                    CambioContraseña cambioContraseña = new CambioContraseña(correo,codigo);
                    cambioContraseña.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "El correo no pertenece a ningun usuario");
                }
            }
        });
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JButton getBtnEnviar() {
        return btnEnviar;
    }
}
