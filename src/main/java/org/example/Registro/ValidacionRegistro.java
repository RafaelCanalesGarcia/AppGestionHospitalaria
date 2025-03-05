package org.example.Registro;

import org.example.ClasesNativas.Usuario;
import org.example.Database.DatabaseConnection;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

public class ValidacionRegistro {
    private static final Map<String, String> regexPorPais = new HashMap<>();
    static final Map<String, String> codigos = new HashMap<>();
    private static final SecureRandom random = new SecureRandom();
    static final Map<String, LocalDateTime> codigosTemporales = new HashMap<>();
    private static final int DURACION_MINUTOS = 5;

    public static boolean autenticarUsuario(String usuario, String contrasena) {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.err.println("Error: No se pudo establecer la conexión a la base de datos.");
            return false;
        }

        String query = "SELECT Contraseña FROM usuario WHERE DNI = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedPasswordHash = rs.getString("Contraseña");
                    String inputPasswordHash = Usuario.hashPassword(contrasena);
                    return inputPasswordHash.equals(storedPasswordHash);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la autenticación: " + e.getMessage());
        }
        return false;
    }
    public static String getEmailPorCodigo(String codigo) {
        // Iterar por las entradas del mapa
        for (Map.Entry<String, String> entry : codigos.entrySet()) {
            if (entry.getValue().equals(codigo)) {
                return entry.getKey(); // Retorna el correo correspondiente
            }
        }
        return null; // Retorna null si no encuentra el código
    }

    public static String getCodigoPorEmail(String email) {
        return codigos.get(email);
    }

    public static String generarCodigo() {
        int codigo = 100000 + random.nextInt(900000); // Código de 6 dígitos
        return String.valueOf(codigo);
    }


    public static void guardarCodigo(String destinatario, String codigo) {
        codigos.put(destinatario, codigo);
        codigosTemporales.put(codigo, LocalDateTime.now());
    }

    public static boolean esCodigoVigente(String codigo) {
        LocalDateTime tiempoGenerado = codigosTemporales.get(codigo);
        // si el tiempo actual(isAfter(tiempo generado + tiempo de validacion))
        if (tiempoGenerado.isAfter(tiempoGenerado.plusMinutes(DURACION_MINUTOS))) {
            codigosTemporales.remove(codigo);
            return false; // codigo expirado, lo eliminamos
        }
        return true;
    }

    public static boolean validarCodigo(String destinatario, String codigoIntroducido) {
        String codigoGuardado = codigos.get(destinatario);
        if (codigoIntroducido.equals(codigoGuardado)) {
            codigosTemporales.remove(codigoGuardado);
            codigos.remove(destinatario);
            return true;
        }
        return false;
    }

    static {
        regexPorPais.put("+34", "^[6|7|9][0-9]{8}$"); // España
        regexPorPais.put("+33", "^(0[1-9][0-9]{8})$"); // Francia
        regexPorPais.put("+44", "^(07[0-9]{9}|02[0-9]{9,10})$"); // Reino Unido
        regexPorPais.put("+49", "^0[1-9][0-9]{7,14}$"); // Alemania
        regexPorPais.put("+39", "^(3[0-9]{8,9}|0[1-9][0-9]{6,9})$"); // Italia
        regexPorPais.put("+41", "^0(7[5-9][0-9]{7}|2[0-9]{8})$"); // Suiza
        regexPorPais.put("+43", "^0(6[0-9]{9}|1[0-9]{7,12})$"); // Austria
        regexPorPais.put("+31", "^0(6[0-9]{8}|[1-9][0-9]{6,9})$"); // Países Bajos
        regexPorPais.put("+32", "^0(4[0-9]{8}|[1-9][0-9]{7})$"); // Bélgica
        regexPorPais.put("+47", "^[1-9][0-9]{7}$"); // Noruega
        regexPorPais.put("+46", "^0(7[0-9]{8}|[1-9][0-9]{7,8})$"); // Suecia
        regexPorPais.put("+45", "^[2-9][0-9]{7}$");
    }

    public static boolean validarTelefono(String extension, String telefono) {
        if (regexPorPais.containsKey(extension)) {
            String regex = regexPorPais.get(extension);
            return telefono != null && telefono.matches(regex);
        }
        return false; // Extensión desconocida
    }

    public static boolean validaDNI(String DNI) {
        String DNI_REGEX = "^[0-9]{8}[A-Za-z]$";
        return DNI.matches(DNI_REGEX);
    }

    public static boolean validaNIE(String NIE) {
        String NIE_REGEX = "^[XYZ-xyz][0-9]{7}[A-Za-z]$";
        return NIE.matches(NIE_REGEX);
    }

    public static boolean validarContrasena(String contrasena) {
        // Regex para la contraseña
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!¿?*.,:;<>~`\"\\'\\\\|/{}\\[\\]\\-()_]).{8,}$";
        return contrasena.matches(regex);
    }



}
