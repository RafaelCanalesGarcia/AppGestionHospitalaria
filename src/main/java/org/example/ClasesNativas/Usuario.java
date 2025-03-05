package org.example.ClasesNativas;

import org.example.ClasesNativas.Enums.Rol;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Usuario {
    private String dni;
    private Rol rol;
    private String passwordHash;

    public Usuario(String dni, Rol rol, String password) {
        this.dni = dni;
        this.rol = rol;
        this.passwordHash = hashPassword(password);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
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

    public boolean verificarPassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }


}
