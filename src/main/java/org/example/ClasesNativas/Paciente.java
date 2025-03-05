package org.example.ClasesNativas;
import java.util.Date;

public class Paciente {
    private String nombre;
    private String dni;
    private Date fNacimiento;
    private String email;
    private String sexo;
    private String telefono;
    private String seguro;
    private String domicilio;
    private String paisNacimiento;
    private String fotoPerfil;


    public Paciente(String nombre, String dni, Date fNacimiento, String email, String sexo, String telefono, String seguro, String domicilio, String paisNacimiento) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.fNacimiento = fNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.seguro = seguro;
        this.domicilio = domicilio;
        this.paisNacimiento = paisNacimiento;
        this.fotoPerfil = null;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public Date getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(Date fNacimiento) {
        this.fNacimiento = fNacimiento;
    }


}
