package org.example.Apariencia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class SobreNosotros extends JPanel {
    Color fondoAzul = new Color(213, 238, 251);

    public SobreNosotros() {

        setLayout(new GridLayout(1, 5, 20, 0));
        setBackground(fondoAzul);

        // Oficina
        JPanel officePanel = createContactPanel("Nuestra oficina", "C/ Camí de Son Rapinya, 12 , 07013", "Palma, Islas Baleares", "images/home.png","https://www.google.es/maps/place/CESUR+Mallorca+Formaci%C3%B3n+Profesional/@39.5812812,2.6211983,17z/data=!3m1!4b1!4m6!3m5!1s0x1297937762fdad4d:0xc0fb6bc651e86210!8m2!3d39.5812812!4d2.6237732!16s%2Fg%2F11pd13zrc7?hl=es&entry=ttu&g_ep=EgoyMDI1MDIxMi4wIKXMDSoASAFQAw%3D%3D");

        // Teléfono
        JPanel phonePanel = createContactPanel("Datos de contacto", "Teléfonos: 971 123 456 / 654 321 087", "Fax: 971 589 652", "images/contact.png");

        // Email
        JPanel emailPanel = createContactPanel("e-mail", "", "meimg2025@gmail.com", "images/email.png","mailto:meimg2025@gmail.com?subject=Consulta%20sobre%20servicios");

        // Instagram
        JPanel instagramPanel = createContactPanel("Instagram","MEIMG","Clinica Privada","images/instagram.png","https://www.instagram.com/mareleimgeo/");

        // Facebook
        JPanel facebookPanel = createContactPanel("Facebook","MEIMG","Clinica Privada","images/facebook.png");
        add(officePanel);
        add(phonePanel);
        add(emailPanel);
        add(instagramPanel);
        add(facebookPanel);
    }

    private JPanel createContactPanel(String title, String line1, String line2, String iconPath) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoAzul);

        ImageIcon icon = new ImageIcon(iconPath);
        JLabel iconLabel = new JLabel(Draw.scaleIcon(icon, 30, 30)); // Imagen de icono
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(iconLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.CENTER);

        JLabel textLabel = new JLabel("<html><center>" + line1 + "<br>" + line2 + "</center></html>", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(textLabel, BorderLayout.SOUTH);

        return panel;
    }
    private JPanel createContactPanel(String title, String line1, String line2, String iconPath, String url) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoAzul);

        // Cargar y escalar imagen

        ImageIcon icon = new ImageIcon(iconPath);
        JLabel iconLabel = new JLabel(Draw.scaleIcon(icon, 30, 30)); // Imagen de icono
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(iconLabel, BorderLayout.NORTH);

        // Si tiene URL, lo hacemos clickeable
        if (url != null && !url.startsWith("mailto:")) {
            makeLabelClickable(iconLabel, url);
        } else if (url != null && url.startsWith("mailto:")){
            makeMailClickable(iconLabel, url);
        }

        panel.add(iconLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.CENTER);

        JLabel textLabel = new JLabel("<html><center>" + line1 + "<br>" + line2 + "</center></html>", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(textLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void makeLabelClickable(JLabel label, String url) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a "mano"
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url)); // Abre el enlace en el navegador
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private void makeMailClickable(JLabel label, String url) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a "mano"
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI(url)); // Abre el cliente de correo
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }}
