
package org.example.Registro;

import org.example.Apariencia.Login;
import org.example.ClasesNativas.Paciente;
import org.example.ClasesNativas.Enums.Rol;
import org.example.ClasesNativas.Seguro;
import org.example.ClasesNativas.Usuario;
import org.example.Database.DatabaseManagement;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

public class Registro extends JFrame {
    DatabaseManagement dbm = new DatabaseManagement();

    // Panel principal
    private JPanel panel;


    // Imagen principal
    private JLabel lblImagen = new JLabel(new ImageIcon("images/icon.png"));

    // Panel donde se añadirán los datos personales
    private JPanel panelInfoPersonal;

    // Nombre
    private JLabel lblNombre;
    private JTextField txtNombre;

    // Panel donde se escogerá el sexo con radioButtons
    private JPanel panelSexo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel lblSexo;
    private JTextField txtSexo;
    private ButtonGroup groupSexo = new ButtonGroup();

    private JRadioButton rbtnHombre;
    private JRadioButton rbtnMujer;
    private JRadioButton rbtnOtro;

    private JPanel panelCompania = new JPanel(new GridLayout(1, 4, 5, 5));


    private JLabel lblCompania;
    private ButtonGroup groupCompania = new ButtonGroup();
    private JRadioButton rbtnAdeslas;
    private JRadioButton rbtnMapfre;
    private JRadioButton rbtnSanitas;
    private JRadioButton rbtnGenerali;

    private JPanel panelCobertura = new JPanel(new GridLayout(1, 4, 5, 5));
    private JLabel lblCobertura;
    private ButtonGroup groupCobertura = new ButtonGroup();
    private JRadioButton rbtnTotal;
    private JRadioButton rbtnVital;
    private JRadioButton rbtnVitalPlus;
    private JRadioButton rbtnLimitada;

    // Fecha nacimiento
    private UtilDateModel model;
    private JLabel lblFechaNacimiento;

    // Direccion postal
    private JLabel lblDireccion;
    private JTextField txtDireccion;

    // DNI / NIE
    private JLabel lblDocumento;
    private JTextField txtDocumento;

    // Contraseña para acceder a su cuenta
    private JLabel lblPasword;
    private JPasswordField txtPassword;

    // Confirmación de contraseña
    private JLabel lblConfirmPasword;
    private JPasswordField txtConfirmPassword;

    // Panel donde se añadirá el contacto
    private JPanel panelContacto;

    // Correo electrónico
    private JLabel lblEmail;
    private JTextField txtEmail;

    // Extensiones para ingresar el tlf
    private String[] extensiones = {
            "+34 (España)", "+33 (Francia)", "+44 (Reino Unido)", "+49 (Alemania)",
            "+39 (Italia)", "+41 (Suiza)", "+43 (Austria)", "+31 (Países Bajos)",
            "+32 (Bélgica)", "+47 (Noruega)", "+46 (Suecia)", "+45 (Dinamarca)"
    };
    // Desplegable para elegir la extensión
    private JComboBox<String> comboExtensiones = new JComboBox<>(extensiones);
    private JLabel extTelefono;

    // Teléfono
    private JLabel lblTelefono;
    private JTextField txtTelefono;

    private JPanel panelSeguro;
    private JLabel lblSeguro;
    private JTextField txtSeguro;

    // Registro
    private JButton btnRegistrar;

    public Registro() {
        configurarVentana();
        inicializarComponentes();
        agregarEventos();
    }

    private void configurarVentana() {
        setTitle("Registro de Usuario");

        setSize(400, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(this);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(213, 238, 251));
    }

    private void inicializarComponentes() {

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(213, 238, 251));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Imagen
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // La imagen ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblImagen, gbc);

        // Restablecer propiedades
        gbc.gridwidth = 1; // Resetear a una columna
        gbc.anchor = GridBagConstraints.WEST;
        panelInfoPersonal = new JPanel(new GridBagLayout());
        // Panel de Información Personal con borde y título
        panelInfoPersonal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE),
                "Información Personal", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        panelInfoPersonal.setBackground(new Color(213, 238, 251));

        // Nombre y apellidos
        lblNombre = new JLabel("Nombre y Apellidos:");
        lblNombre.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInfoPersonal.add(lblNombre, gbc);

        txtNombre = new JTextField(40);
        txtNombre.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelInfoPersonal.add(txtNombre, gbc);

        // Sexo
        lblSexo = new JLabel("Sexo:");
        lblSexo.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInfoPersonal.add(lblSexo, gbc);

        panelSexo.setBackground(new Color(213, 238, 251));

        rbtnHombre = new JRadioButton("Hombre");
        rbtnHombre.setBackground(new Color(213, 238, 251));

        rbtnMujer = new JRadioButton("Mujer");
        rbtnMujer.setBackground(new Color(213, 238, 251));

        rbtnOtro = new JRadioButton("Otro");
        rbtnOtro.setBackground(new Color(213, 238, 251));


        groupSexo.add(rbtnHombre);
        groupSexo.add(rbtnMujer);
        groupSexo.add(rbtnOtro);

        txtSexo = new JTextField();
        txtSexo.setEditable(false);
        txtSexo.setPreferredSize(new Dimension(205, 30));
        panelSexo.add(rbtnHombre);
        panelSexo.add(rbtnMujer);
        panelSexo.add(rbtnOtro);
        panelSexo.add(txtSexo);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelInfoPersonal.add(panelSexo, gbc);
        // Deshabilitar cuando se seleccione "Mujer"


        // Fecha de nacimiento
        lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfoPersonal.add(lblFechaNacimiento, gbc);

        gbc.gridx = 1;
        model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datePicker.getJFormattedTextField().setEditable(true);

        panelInfoPersonal.add(datePicker, gbc);


        // Dirección domicilio
        lblDireccion = new JLabel("Dirección Domicilio:");
        lblDireccion.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInfoPersonal.add(lblDireccion, gbc);

        txtDireccion = new JTextField(40);
        txtDireccion.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        panelInfoPersonal.add(txtDireccion, gbc);

        // Número de documento de identidad
        lblDocumento = new JLabel("DNI/NIE:");
        lblDocumento.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelInfoPersonal.add(lblDocumento, gbc);

        txtDocumento = new JTextField(40);
        txtDocumento.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        panelInfoPersonal.add(txtDocumento, gbc);

        // Contraseña
        lblPasword = new JLabel("Contraseña:");
        lblPasword.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelInfoPersonal.add(lblPasword, gbc);

        txtPassword = new JPasswordField(40);
        gbc.gridx = 1;
        txtPassword.setPreferredSize(new Dimension(100, 30));

        panelInfoPersonal.add(txtPassword, gbc);
        // Contraseña
        lblConfirmPasword = new JLabel("Confirma contraseña:");
        lblConfirmPasword.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelInfoPersonal.add(lblConfirmPasword, gbc);

        txtConfirmPassword = new JPasswordField(40);
        gbc.gridx = 1;
        txtConfirmPassword.setPreferredSize(new Dimension(100, 30));

        panelInfoPersonal.add(txtConfirmPassword, gbc);

        // Añadir el panel de Información Personal al panel principal
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelInfoPersonal, gbc);

        gbc.gridwidth = 1; // Resetear a una columna
        gbc.anchor = GridBagConstraints.WEST;
        // Panel de Contacto con borde y título

        panelContacto = new JPanel(new GridBagLayout());
        panelContacto.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE),
                "Contacto", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        panelContacto.setBackground(new Color(213, 238, 251));

        // E-mail
        lblEmail = new JLabel("E-mail(usuario):");
        lblEmail.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelContacto.add(lblEmail, gbc);

        txtEmail = new JTextField(40);
        txtEmail.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        panelContacto.add(txtEmail, gbc);

        // Número de teléfono
        lblTelefono = new JLabel("Número de Teléfono:");
        lblTelefono.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelContacto.add(lblTelefono, gbc);

        txtTelefono = new JTextField(40);
        txtTelefono.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        panelContacto.add(txtTelefono, gbc);


        // Extensión telefónica
        extTelefono = new JLabel("Extensión Telefónica:");
        extTelefono.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelContacto.add(extTelefono, gbc);


        gbc.gridx = 1;
        panelContacto.add(comboExtensiones, gbc);

        // Añadir el panel de Contacto al panel principal
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelContacto, gbc);


        panelSeguro = new JPanel(new GridBagLayout());
        panelSeguro.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE),
                "Seguro", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        panelSeguro.setBackground(new Color(213, 238, 251));
        gbc.gridwidth = 1;
        lblSeguro = new JLabel("Número de póliza:");
        lblSeguro.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSeguro.add(lblSeguro, gbc);

        txtSeguro = new JTextField(40);
        txtSeguro.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        panelSeguro.add(txtSeguro, gbc);

        // ______________________________________________________
        lblCompania = new JLabel("Compañia:");
        lblCompania.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSeguro.add(lblCompania, gbc);

        panelCompania.setBackground(new Color(213, 238, 251));

        rbtnAdeslas = new JRadioButton("Adeslas");
        rbtnAdeslas.setBackground(new Color(213, 238, 251));

        rbtnMapfre = new JRadioButton("Mapfre");
        rbtnMapfre.setBackground(new Color(213, 238, 251));

        rbtnSanitas = new JRadioButton("Sanitas");
        rbtnSanitas.setBackground(new Color(213, 238, 251));

        rbtnGenerali = new JRadioButton("Generali");
        rbtnGenerali.setBackground(new Color(213, 238, 251));

        groupCompania.add(rbtnAdeslas);
        groupCompania.add(rbtnMapfre);
        groupCompania.add(rbtnSanitas);
        groupCompania.add(rbtnGenerali);

        panelCompania.add(rbtnAdeslas);
        panelCompania.add(rbtnMapfre);
        panelCompania.add(rbtnSanitas);
        panelCompania.add(rbtnGenerali);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permite expandirse horizontalmente
        panelSeguro.add(panelCompania, gbc);
        //______________________________________________________
        lblCobertura = new JLabel("Cobertura:");
        lblCobertura.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelSeguro.add(lblCobertura, gbc);

        rbtnTotal = new JRadioButton("Total");
        rbtnTotal.setBackground(new Color(213, 238, 251));

        rbtnVital = new JRadioButton("Vital");
        rbtnVital.setBackground(new Color(213, 238, 251));

        rbtnVitalPlus = new JRadioButton("VitalPlus");
        rbtnVitalPlus.setBackground(new Color(213, 238, 251));

        rbtnLimitada = new JRadioButton("Limitada");
        rbtnLimitada.setBackground(new Color(213, 238, 251));

        groupCobertura.add(rbtnTotal);
        groupCobertura.add(rbtnVitalPlus);
        groupCobertura.add(rbtnVital);
        groupCobertura.add(rbtnLimitada);
        panelCobertura.setBackground(new Color(213, 238, 251));
        panelCobertura.add(rbtnTotal);
        panelCobertura.add(rbtnVitalPlus);
        panelCobertura.add(rbtnVital);
        panelCobertura.add(rbtnLimitada);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelSeguro.add(panelCobertura, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(panelSeguro, gbc);

        // Botón de registro
        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnRegistrar, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panel, gbc);

        // Envolver el panel en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        // Añadir el JScrollPane al JFrame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

    }

    private void agregarEventos() {
        btnRegistrar.addMouseListener(new MouseAdapter() {
                                          @Override
                                          public void mouseClicked(MouseEvent e) {
                                              String nombre = convertirASustantivo(txtNombre.getText().trim());
                                              String sexo = convertirASustantivo(txtSexo.getText().trim());
                                              Date fechaNacimiento = model.getValue();
                                              String domicilio = txtDireccion.getText();
                                              String DNI = txtDocumento.getText();
                                              String password = txtPassword.getText();
                                              String confirmPassword = txtConfirmPassword.getText();
                                              String extension = comboExtensiones.getSelectedItem().toString().substring(0, 3);
                                              String pais = comboExtensiones.getSelectedItem().toString().substring(5, comboExtensiones.getSelectedItem().toString().length() - 1);
                                              String telefono = txtTelefono.getText();
                                              String email = txtEmail.getText().trim();
                                              String seguro = txtSeguro.getText().trim();
                                              String compania = obtenerTextoDeBotonSeleccionado(groupCompania);
                                              String cobertura = obtenerTextoDeBotonSeleccionado(groupCobertura);

                                              if (dbm.existeUsuario(DNI)) {
                                                  JOptionPane.showMessageDialog(Registro.this, "El usuario con DNI " + DNI + " ya existe.", "Ya ha sido registrado", JOptionPane.ERROR_MESSAGE);
                                              } else if (nombre.equals("Inválido")) {
                                                  JOptionPane.showMessageDialog(Registro.this, "El nombre o apellidos estan sin rellenar o contienen carácteres inadecuados", "Nombre incorrecto", JOptionPane.ERROR_MESSAGE);
                                              } else if (fechaNacimiento == null || fechaNacimiento.after(new Date())) {
                                                  JOptionPane.showMessageDialog(Registro.this, "La fecha de nacimiento no esta especificada o has puesto una fecha futura", "Fecha incorrecta", JOptionPane.ERROR_MESSAGE);
                                              } else if (!ValidacionRegistro.validaDNI(DNI) && !ValidacionRegistro.validaNIE(DNI)) {
                                                  JOptionPane.showMessageDialog(Registro.this, "El DNI o NIE no corresponde al formato adecuado", "Formato de DNI/NIE incorrecto", JOptionPane.ERROR_MESSAGE);
                                              } else if (!ValidacionRegistro.validarContrasena(password)) {
                                                  JOptionPane.showMessageDialog(Registro.this, "La contraseña debe contener mínimo 8 carácteres incluyenndo 1 mayúscula, 1 miníscula, 1 número y 1 símbolo", "Contraseña inválida", JOptionPane.ERROR_MESSAGE);
                                              } else if (!confirmPassword.equals(password)) {
                                                  JOptionPane.showMessageDialog(Registro.this, "Las contraseñas no coinciden entre sí", "Contraseña inválida", JOptionPane.ERROR_MESSAGE);
                                              } else if (!ValidacionRegistro.validarTelefono(extension, telefono)) {
                                                  JOptionPane.showMessageDialog(Registro.this, "El teléfono " + telefono + " no pertenece a la extension " + extension + " de " + pais, "Telefono inválido", JOptionPane.ERROR_MESSAGE);
                                              } else if (sexo.isEmpty() || domicilio.isEmpty() || seguro.isEmpty() || compania.isEmpty() || cobertura.isEmpty()) {
                                                  JOptionPane.showMessageDialog(Registro.this, "No estan todos los campos completos.", "Falta información", JOptionPane.ERROR_MESSAGE);
                                              } else {
                                                  String codigoGenerado = ValidacionRegistro.generarCodigo();
                                                  ValidacionRegistro.guardarCodigo(email, codigoGenerado);
                                                  EnvioCorreo.enviarCorreo(email, "Código de validación MEIMG", "El código de validación de tu registro es: " + codigoGenerado);
                                                  VentanaCodigo ventanaCodigo = new VentanaCodigo(email);
                                                  ventanaCodigo.setVisible(true);

                                                  new Thread(() -> {
                                                      while (!VentanaCodigo.isFinish()) {
                                                          try {
                                                              Thread.sleep(500); // Esperamos medio segundo antes de verificar otra vez
                                                          } catch (InterruptedException ex) {
                                                              ex.printStackTrace();
                                                          }
                                                      }
                                                      Usuario u = new Usuario(DNI, Rol.paciente, password);
                                                      dbm.guardarUsuario(u);
                                                      Paciente p = new Paciente(nombre, DNI, fechaNacimiento, email, sexo, telefono, seguro, domicilio, pais);
                                                      dbm.guardarPaciente(p);
                                                      Seguro s = new Seguro(DNI, seguro, compania, cobertura);
                                                      dbm.guardarSeguro(s);


//                                                   ventanaCodigo.setVisible(false);
                                                      dispose(); // Cierra la ventana de Registro
                                                      SwingUtilities.invokeLater(() -> new Login().setVisible(true));

                                                  }).start();

                                              }
                                          }
                                      }

        );

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dispose(); // Cierra la ventana de Registro
            }
        });

        rbtnOtro.addActionListener(e -> {
            if (rbtnOtro.isSelected()) {
                txtSexo.setEditable(true);
                txtSexo.setText("");// Habilitar el campo de texto cuando "Otro" esté seleccionado
            }
        });
        rbtnHombre.addActionListener(e -> txtSexo.setEditable(false));
        rbtnHombre.addActionListener(e -> txtSexo.setText("Hombre"));// Deshabilitar cuando se seleccione "Hombre"
        rbtnMujer.addActionListener(e -> txtSexo.setEditable(false));
        rbtnMujer.addActionListener(e -> txtSexo.setText("Mujer"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Registro().setVisible(true));
    }

    public static String convertirASustantivo(String nombre) {
        // Verifica si el nombre contiene caracteres no permitidos (no letras ni espacios)
        if (!nombre.matches("[a-zA-Z\\s]+")) {
            return "Inválido";
        }

        // Divide el nombre en palabras y procesa cada palabra
        String[] palabras = nombre.split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            if (palabra.length() > 3) {
                // Convierte la primera letra a mayúscula y el resto a minúscula
                resultado.append(palabra.substring(0, 1).toUpperCase())
                        .append(palabra.substring(1).toLowerCase());
            } else {
                // Convierte toda la palabra a minúscula si es menor o igual a 3 letras
                resultado.append(palabra.toLowerCase());
            }
            resultado.append(" "); // Agrega un espacio entre palabras
        }
        // Retorna el resultado, eliminando el espacio extra al final
        return resultado.toString().trim();
    }

    private String obtenerTextoDeBotonSeleccionado(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText(); // Devuelve el texto del botón seleccionado
            }
        }
        return null;
    }
}