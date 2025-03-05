package org.example.Registro;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EnvioCorreo {
    static final String usuario = CargaConfiguracion.getProperty("email.username");
    static final String contrasena = CargaConfiguracion.getProperty("email.password");

    public static void enviarCorreo(String destinatario, String asunto, String mensaje) {
        // Configuración de las propiedades de correo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP de Gmail
        props.put("mail.smtp.port", "587"); // Puerto TLS
        props.put("mail.smtp.auth", "true"); // Requiere autenticación
        props.put("mail.smtp.starttls.enable", "true"); // Habilitar TLS

        // Credenciales de tu cuenta de correo


        // Crear una sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contrasena);
            }
        });

        try {
            // Crear un mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            // Enviar el mensaje
            Transport.send(message);

//            System.out.println("Correo enviado con éxito a: " + destinatario);

        } catch (MessagingException e) {
            e.printStackTrace();
//            System.out.println("Error al enviar el correo: " + e.getMessage());
        }

    }

}

