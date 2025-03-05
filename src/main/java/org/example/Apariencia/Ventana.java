package org.example.Apariencia;

import org.example.ClasesNativas.Trabajador;
import org.example.Database.DatabaseManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Ventana extends JFrame {
    JPanel centerPanel;
    private SobreNosotros sobreNosotros;
    Color fondoAzul = new Color(213, 238, 251);
    Color letterColor = new Color(12, 68, 198);
    private DatabaseManagement dbm = new DatabaseManagement();

    public Ventana(String dni) {
        setTitle("MEIMG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        centerPanel = new JPanel(new GridBagLayout()); // Cambiado a GridBagLayout
centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        CardLayout cardLayout = new CardLayout();
        centerPanel.setLayout(cardLayout); // Cambiar layout de centerPanel a CardLayout

    // Crear instancia de PedirCita
        PedirCitaPaciente pedirCitaPaciente = new PedirCitaPaciente(dni);
        // Crear instancia de MisCitas
        MisCitas misCitas = new MisCitas(dni);
        centerPanel.add(misCitas, "MisCitas");
    // Añadir la instancia de PedirCita a centerPanel
        PedirCitaTrabajador pedirCitaTrabajador = new PedirCitaTrabajador(dni);
        centerPanel.add(pedirCitaPaciente, "PedirCitaPaciente");
        centerPanel.add(pedirCitaTrabajador, "PedirCitaTrabajador");

        List<Trabajador> trabajadores = dbm.obtenerListaTrabajadores();
        Especialistas especialistas = new Especialistas(trabajadores);
        JScrollPane scrollPane = new JScrollPane(especialistas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);
        centerPanel.add(scrollPane, "Especialistas");

        //TarjetaIdentificacion
        Perfil perfil = new Perfil(dni);
        centerPanel.add(perfil, "Perfil");
        // Configuración principal del layout
        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);

        // Panel izquierdo dividido en 2
        sobreNosotros = new SobreNosotros();
        // Agregar el calendario y otra posible sección al lado izquierdo

        add(sobreNosotros, BorderLayout.SOUTH);

        // Panel izquierdo con botones
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 50));
        leftPanel.setLayout(new GridLayout(8, 1));
        leftPanel.setBackground(fondoAzul);
        ImageIcon icon = new ImageIcon("images/icon.png");
        JLabel iconLabel = new JLabel(Draw.scaleIcon(icon,110,100));
        leftPanel.add(iconLabel);

        JButton calendarButton = new JButton("Pedir cita");
        calendarButton.setIcon(Draw.scaleIcon(Draw.calendar, 50, 50));
        calendarButton.setHorizontalTextPosition(SwingConstants.CENTER);

        calendarButton.setBorder(BorderFactory.createLineBorder(fondoAzul));
        calendarButton.setBackground(fondoAzul);
        calendarButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        leftPanel.add(calendarButton);

        JButton misCitasButton = new JButton("Mis Citas");
        misCitasButton.setIcon(Draw.scaleIcon(Draw.miscitas, 50, 50));
        misCitasButton.setBackground(fondoAzul);
        misCitasButton.setBorder(BorderFactory.createLineBorder(fondoAzul));
        leftPanel.add(misCitasButton);
        misCitasButton.setHorizontalTextPosition(SwingConstants.CENTER);
        misCitasButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton perfilButton = new JButton("Perfil");
        perfilButton.setIcon(Draw.scaleIcon(Draw.patient, 50, 50));
        perfilButton.setBackground(fondoAzul);
        perfilButton.setBorder(BorderFactory.createLineBorder(fondoAzul));
        leftPanel.add(perfilButton);
        perfilButton.setHorizontalTextPosition(SwingConstants.CENTER);
        perfilButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton doctoresButton = new JButton("Especialistas");
        doctoresButton.setIcon(Draw.scaleIcon(Draw.patient, 50, 50));
        doctoresButton.setBackground(fondoAzul);
        doctoresButton.setBorder(BorderFactory.createLineBorder(fondoAzul));
        leftPanel.add(doctoresButton);
        doctoresButton.setHorizontalTextPosition(SwingConstants.CENTER);
        doctoresButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton logoutButton = new JButton("Cerrar sesión");
        logoutButton.setIcon(Draw.scaleIcon(Draw.patient, 50, 50));
        logoutButton.setBackground(fondoAzul);
        logoutButton.setBorder(BorderFactory.createLineBorder(fondoAzul));
        leftPanel.add(logoutButton);
        logoutButton.setHorizontalTextPosition(SwingConstants.CENTER);
        logoutButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        add(leftPanel, BorderLayout.WEST);

        // Acciones de los botones
        doctoresButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cambiarContenido("Especialistas");
            }

        });
        perfilButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cambiarContenido("Perfil");
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true); // Abre la ventana de login
                dispose(); // Cierra la ventana actual (Ventana)
            }
        });

        calendarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(dbm.obtenerTipoUsuario(dni).equalsIgnoreCase("Paciente")) {
                    cambiarContenido("PedirCitaPaciente");
                } else {
                    cambiarContenido("PedirCitaTrabajador");
                }
            }
        });

        misCitasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cambiarContenido("MisCitas");
            }
        });

        setSize(800, 600);
        setVisible(true);
    }

    private void cambiarContenido(String cardName) {
        CardLayout cl = (CardLayout) centerPanel.getLayout();
        cl.show(centerPanel, cardName);
    }

    public static void main(String[] args) {

//       Ventana v = new Ventana("TRAB001");
       Ventana v = new Ventana("PAC001");

    }

}
