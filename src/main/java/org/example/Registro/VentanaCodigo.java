package org.example.Registro;

import javax.swing.*;
import java.awt.*;

public class VentanaCodigo extends JFrame {
    private static boolean finish = false;
    private String email;

    public static boolean isFinish() {
        return finish;
    }

    public static void setFinish(boolean finish) {
        VentanaCodigo.finish = finish;
    }

    public VentanaCodigo(String email) {
        this.email = email;
        setTitle("Validación");
        setSize(400, 200);
        setLocationRelativeTo(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Introduce el código que hemos enviado a tu email:");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // Campo de texto para el código
        JTextField txtCodigo = new JTextField();
        txtCodigo.setColumns(6);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(txtCodigo, gbc);

        // Botón de confirmar
        JButton btnConfirmar = new JButton("Confirmar");
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(btnConfirmar, gbc);

        // Mensaje para reenviar código
        JLabel lblReenviar = new JLabel("<html><u>¿No has recibido el código? Reenviar nuevo código</u></html>");
        lblReenviar.setForeground(Color.BLUE);
        lblReenviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        lblReenviar.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblReenviar, gbc);

        // Límite de caracteres en el campo de texto
        txtCodigo.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
                if (str != null && (getLength() + str.length() <= 6)) {
                    super.insertString(offset, str, attr);
                }
            }
        });

        // Acción al hacer clic en confirmar
        btnConfirmar.addActionListener(e -> {
            String codigo = txtCodigo.getText();
            if (codigo.length() == 6) {
                if (ValidacionRegistro.esCodigoVigente(codigo) || ValidacionRegistro.getCodigoPorEmail(email) != null) {
                    if (ValidacionRegistro.validarCodigo(ValidacionRegistro.getEmailPorCodigo(codigo), codigo)) {
                        JOptionPane.showMessageDialog(VentanaCodigo.this, "Tu cuenta ha sido registrada!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                        dispose(); // Cerrar la ventana modal
                        setFinish(true);
                    } else {
                        JOptionPane.showMessageDialog(VentanaCodigo.this, "Código incorrecto, prueba de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                        setFinish(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaCodigo.this, "La validez del código ha expirado, intenta a reenviar codigo e introducir el codigo nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    setFinish(false);
                }
            } else {
                JOptionPane.showMessageDialog(VentanaCodigo.this, "Por favor, introduce un código de 6 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                setFinish(false);
            }
        });

        // Acción al hacer clic en "Reenviar nuevo código"
        lblReenviar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String codigoActual = ValidacionRegistro.getCodigoPorEmail(email);

                if (email != null) {
                    // Eliminar el código anterior del mapa
                    ValidacionRegistro.codigos.remove(email);
                    ValidacionRegistro.codigosTemporales.remove(codigoActual);

                    // Generar y guardar un nuevo código
                    String nuevoCodigo = ValidacionRegistro.generarCodigo();
                    ValidacionRegistro.guardarCodigo(email, nuevoCodigo);

                    // Enviar el nuevo código por correo
                    EnvioCorreo.enviarCorreo(email, "Nuevo Código de Validación", "Tu nuevo código de validación es: " + nuevoCodigo);

                    JOptionPane.showMessageDialog(VentanaCodigo.this, "Se ha enviado un nuevo código a tu email.", "Reenviado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("NO HAY EMAIL");
                }
            }
        });

        setVisible(true);
    }

}

