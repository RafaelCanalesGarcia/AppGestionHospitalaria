package org.example.Apariencia;

import org.example.Registro.OlvidaContraseña;
import org.example.Registro.Registro;
import org.example.Registro.ValidacionRegistro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JLabel lblRegistrarse, lblOlvidarContrasena;

    public Login() {
        configurarVentana();
        inicializarComponentes();
        agregarListeners();
    }

    private void configurarVentana() {
        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(this);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(213, 238, 251));
    }

    private void inicializarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        ImageIcon logo = Draw.scaleIcon(new ImageIcon("images/icon.png"), 280, 250);
        add(new JLabel(logo), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(new JLabel("Usuario:"), gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        add(txtUsuario, gbc);

        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Contraseña:"), gbc);

        txtContrasena = new JPasswordField(15);
        gbc.gridx = 1;
        add(txtContrasena, gbc);

        // Botón de inicio de sesión
        btnLogin = new JButton("Iniciar Sesión");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnLogin, gbc);

        // Texto para registrarse y olvidar contraseña
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(213, 238, 251));
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        lblRegistrarse = new JLabel("¿No tienes cuenta? Regístrate");
        lblRegistrarse.setForeground(Color.BLUE);
        lblRegistrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelInferior.add(lblRegistrarse);

        lblOlvidarContrasena = new JLabel("¿Olvidaste tu contraseña?");
        lblOlvidarContrasena.setForeground(Color.BLUE);
        lblOlvidarContrasena.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelInferior.add(lblOlvidarContrasena);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(panelInferior, gbc);
    }

    private void agregarListeners() {
        lblRegistrarse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                dispose();// Oculta el JFrame actual
                SwingUtilities.invokeLater(() -> new Registro().setVisible(true)); // Abre la nueva ventana

            }
        });

        lblOlvidarContrasena.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OlvidaContraseña olvidaContraseña = new OlvidaContraseña();
                olvidaContraseña.setVisible(true);
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == 0) { // Si no está maximizada
                    setSize(800, 600); // Tamaño predeterminado
                }
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());
                if (ValidacionRegistro.autenticarUsuario(usuario, contrasena)) {
                    JOptionPane.showMessageDialog(Login.this, "Inicio de sesión exitoso");
                    new Ventana(usuario).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}